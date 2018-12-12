package com.kiiik.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年5月14日
 */
@Component
@ConfigurationProperties(prefix = "system.switch")
public class SwitchProperties {
	private boolean showControllerTime;

	public boolean isShowControllerTime() {
		return showControllerTime;
	}

	public void setShowControllerTime(boolean showControllerTime) {
		this.showControllerTime = showControllerTime;
	}
	
}
