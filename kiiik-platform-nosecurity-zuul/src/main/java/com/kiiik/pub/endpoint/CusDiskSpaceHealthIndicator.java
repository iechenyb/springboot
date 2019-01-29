package com.kiiik.pub.endpoint;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 *作者 : iechenyb<br>
 *类描述: 说点啥<br>
 *创建时间: 2018年12月27日
 */
@Component
public class CusDiskSpaceHealthIndicator extends AbstractHealthIndicator {
	Log log = LogFactory.getLog(CusDiskSpaceHealthIndicator.class);
	private final FileStore fileStore;
    private final long thresholdBytes;
 
    @Autowired
    public CusDiskSpaceHealthIndicator(
        @Value("${health.filestore.path:/}") String path,
        @Value("${health.filestore.threshold.bytes:10485760}") long thresholdBytes)
        throws IOException {
        fileStore = Files.getFileStore(Paths.get(path));
        this.thresholdBytes = thresholdBytes;
    }
    // 检查逻辑
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        long diskFreeInBytes = fileStore.getUnallocatedSpace();
        if (diskFreeInBytes >= thresholdBytes) {
            builder.up();
        } else {
            builder.down();
        }
 
        long totalSpaceInBytes = fileStore.getTotalSpace();
        builder.withDetail("disk.free", diskFreeInBytes);
        builder.withDetail("disk.total", totalSpaceInBytes);
    }
    
    /**
     * {
			description: "Composite Discovery Client",
			status: "UP",
			discoveryComposite: {
			description: "Composite Discovery Client",
			status: "UP",
			discoveryClient: {
			description: "Composite Discovery Client",
			status: "UP",
			services: [
			"service-admin",
			"kiiik-zuul"
			]
			},
			eureka: {
			description: "Remote status from Eureka server",
			status: "UP",
			applications: {
			SERVICE-ADMIN: 1,
			KIIIK-ZUUL: 1
			}
			}
			},
			diskSpace: {
			status: "UP",
			total: 892826808320,
			free: 801618845696,
			threshold: 10485760
			},
			redis: {
			status: "UP",
			version: "3.2.0"
			},
			db: {
			status: "UP",
			database: "MySQL",
			hello: 1
			},
			hystrix: {
			status: "UP"
			}
			}
     */
	
}
