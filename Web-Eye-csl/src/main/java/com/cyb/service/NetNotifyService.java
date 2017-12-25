package com.cyb.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cyb.dao.NetMonitorRepository;
import com.cyb.dao.NetNotifyDaoImpl;
import com.cyb.dao.NetNotifyRepository;
import com.cyb.dao.NetResultDaoImpl;
import com.cyb.date.DateUtil;
import com.cyb.po.NetMonitor;
import com.cyb.po.NetNotify;
import com.cyb.po.NetResult;
import com.cyb.utils.CSVFileUtil;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2017年12月18日
 */
@Service
public class NetNotifyService {
	Log log = LogFactory.getLog(NetNotifyService.class);

	@Autowired
	private JavaMailSender sender;

	@Value(value = "${spring.boot.admin.notify.mail.to}")
	private String to = "1048417686@qq.com";

	@Value(value = "${spring.boot.admin.notify.mail.from}")
	private String from = "383065059@qq.com";

	@Autowired
	NetMonitorRepository netMonitory;

	@Autowired
	NetNotifyRepository notifyRep;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	NetResultDaoImpl netResultDao;

	@Autowired
	NetNotifyDaoImpl netNotifyDao;

	@SuppressWarnings("resource")
	public void writeException() throws IOException {
		String filePath = "d:/data/netresult.csv";
		File rsFile = new File(filePath);
		if (rsFile.exists()) {
			rsFile.delete();
			try {
				rsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String[] header = new String[] { "服务名称", "IP地址", "端口号", "请求地址", "异常时间", "异常信息", "系统类型" };
		BufferedWriter csvFileOutputStream = null;
		csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "GB2312"),
				1024);
		csvFileOutputStream.write(CSVFileUtil.toCSVLine(header));
		csvFileOutputStream.newLine();

		List<NetResult> ls = netResultDao.getListByHql("from NetResult where zt=0 order by time desc");
		String[] row = null;
		for (NetResult rs : ls) {
			row = new String[] { rs.getName(), rs.getIp(), rs.getPort(), rs.getUrl(), rs.getTime(), rs.getInfor(),rs.getSysType() };
			csvFileOutputStream.write(CSVFileUtil.toCSVLine(row));
			csvFileOutputStream.newLine();
		}
		csvFileOutputStream.flush();
	}

	public void sendMail() {
		try {
			writeException();
		} catch (IOException e1) {
			e1.printStackTrace();

		} // 先重新生成异常信息文件
		String filePath = "d:/data/netresult.csv";
		List<NetMonitor> persons = netMonitory.findAll();
		Long today = DateUtil.date2long8();
		// 1查找需要通知的人员
		for (NetMonitor p : persons) {
			try {
				// 2查找当前人员已经通知的次数
				if (notifyRep.getSendCountBySomeOne(p.getAccount(), p.getType(),today).size() <= 3) {
					// 查询所有已经通知多的记录，通知超过3次则不通知
					sendAttachmentsMail("公司系统监控提醒", "公司系统监控提醒", filePath, p.getAccount());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendAttachmentsMail(String subject, String content, String filePath, String to) {
		MimeMessage message = sender.createMimeMessage();
		try {
			// true表示需要创建一个multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);

			FileSystemResource file = new FileSystemResource(new File(filePath));
			String fileName = "default.csv"; // filePath.substring(filePath.lastIndexOf(File.separator));
			helper.addAttachment(fileName, file);

			sender.send(message);
			NetNotify notify = new NetNotify();
			notify.setAccount(to);
			notify.setMsg(subject);
			notify.setType("email");
			notify.setTime(DateUtil.date2long8());
			// entityManager.save(notify);
			// entityManager.persist(notify);
			netNotifyDao.save(notify);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
