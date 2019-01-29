package com.kiiik.pub.endpoint;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月27日
 *一般情况下，我们不会直接实现 HealthIndicator 接口，而是继承 AbstractHealthIndicator 抽象类。
 *因为，我们只需要重写 doHealthCheck 方法，并在这个方法中我们关注于具体的健康检测的业务逻辑服务。
 */
public class HeathEndPoint  implements HealthIndicator{
	Log log = LogFactory.getLog(HeathEndPoint.class);

	@Override
	public Health health() {
		/*// 健康检查的逻辑
		 
        // 检查通过（方式一新建对象）
        return new Health.Builder().withDetail("usercount", 10) //自定义监控内容
                .withDetail("userstatus", "up").up().build();
 
        // 检查失败（方式二静态方法）
        return Health.down().withDetail("status", 9)
                .withDetail("message", "服务故障").build();*/
		return null;
        
	}
}
