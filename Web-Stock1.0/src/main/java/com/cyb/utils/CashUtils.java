package com.cyb.utils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.cyb.config.EmailConfigSettings;
import com.cyb.date.DateUtil;
import com.cyb.url.UrlUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 作者 : iechenyb<br>
 * 类描述: 说点啥<br>
 * 创建时间: 2018年3月30日
 */
@Component
public class CashUtils {
	Log log = LogFactory.getLog(CashUtils.class);

	public String getDataFromUrl(String url) {
		try {
			String res = UrlUtils.downLoadFromUrl(url, null, null);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean checkQutoesQeue(String data) {

		try {
			JSONArray json = JSONArray.fromObject(data);
			JSONObject price = (JSONObject) json.get(0);
			log.info("队列剩余数据:" + price.getLong("队列剩余数据"));
			long contractQutoesNums = price.getLong("队列剩余数据");
			// 行情数据为0且累计5万则定义为异常！
			if (contractQutoesNums == 0 || contractQutoesNums > 50000) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean checkFunds(String data) {
		boolean normal = true;
		try {
			if ("".equals(data)) {
				return false;
			}
			JSONArray json = JSONArray.fromObject(data);
			JSONObject price = (JSONObject) json.get(0);
			log.info("prices:" + price.getJSONArray("price"));
			JSONArray ps = price.getJSONArray("price");
			int num = 0;
			// 判断前20分钟的行情数据，空值超过15个则定义为异常。
			for (int i = 0; i < 20; i++) {
				if ("-".equals(ps.getString(i))) {
					num++;
				}
				if (num >= 15) {
					log.info("行情出现异常！前20分钟出现空价格的数量" + num);
					normal = false;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			normal = false;
		}
		return normal;
	}

	@Autowired
	EmailConfigSettings setting;

	public void sendEmail(String data, String subject) {
		String content = DateUtil.timeToMilis() + " 检测接口数据异常! \n 数据是：\n" + data;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(setting.getFrom());
		message.setTo(setting.getTo());
		message.setSubject(subject);
		message.setText(content);
		sender.send(message);
	}

	@Autowired
	private JavaMailSender sender;

	public void checkCashInterface() {
		// 默认测试
		String fundData = getDataFromUrl("http://180.169.108.228:8080/VASDataCenter/qutoes/totalFunds.html");
		try {
			if (!checkFunds(fundData)) {
				sendEmail(fundData, "增值服务中心-全国资金总量曲线提醒");
			}
			String queueData = getDataFromUrl("http://180.169.108.228:8080/VASDataCenter/qutoes/infor.html");
		    log.info("状态信息："+queueData);
			if (!checkQutoesQeue(queueData)) {
				sendEmail(queueData, "增值服务中心-行情队列状态提醒");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
