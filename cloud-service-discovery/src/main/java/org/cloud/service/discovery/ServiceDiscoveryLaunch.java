package org.cloud.service.discovery;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class ServiceDiscoveryLaunch implements ApplicationListener<EmbeddedServletContainerInitializedEvent>{
	private static EmbeddedServletContainerInitializedEvent event;

	public static void main( String[] args )
    {
    	new SpringApplicationBuilder(ServiceDiscoveryLaunch.class).web(true).run(args);
    	int port = event.getEmbeddedServletContainer().getPort();
    	log.info("注册中心启动.端口 {}",port);
    } 

	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
		ServiceDiscoveryLaunch.event = event;
	}
}