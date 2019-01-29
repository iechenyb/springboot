package com.kiiik.web.websocket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.kiiik.config.websoket.SocketMessage;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2019年1月4日
 */
@Controller
public class WSController {
	Log log = LogFactory.getLog(WSController.class);
	
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	/*@GetMapping("/")
	public String index() {
		return "index";
	}*/

	@MessageMapping("/send")
	@SendTo("/topic/send")
	public SocketMessage send(SocketMessage message) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.date = df.format(new Date());
		return message;
	}

	@Scheduled(fixedRate = 1000)
	@SendTo("/topic/callback")
	public Object callback() throws Exception {
		// 发现消息
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messagingTemplate.convertAndSend("/topic/callback", df.format(new Date()));
		return "callback";
	}
}
