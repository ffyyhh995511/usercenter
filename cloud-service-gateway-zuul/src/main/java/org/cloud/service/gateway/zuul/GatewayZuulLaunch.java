/**
 * 
 */
package org.cloud.service.gateway.zuul;

import org.cloud.service.gateway.zuul.filter.PreTypeZuulFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;


/**
 * @Description:服务网关,所有的请求均到达这个服务进行过滤、认证、授权
 * 访问服务的端口为网关的端口
 * @author:fangyunhe
 * @time:2018年1月18日 下午4:41:56
 */
@EnableZuulProxy
@SpringBootApplication
public class GatewayZuulLaunch {
	
	public static void main(String[] args) {
		SpringApplication.run(GatewayZuulLaunch.class, args);
	}
	
	@Bean
    public PreTypeZuulFilter preTypeZuulFilter() {
        return new PreTypeZuulFilter();
    }
}
