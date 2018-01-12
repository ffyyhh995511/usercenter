package org.cloud.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户统一注册、登录、校验
 * 一个账号登录多个网站(同个一级域名下)、app
 * 
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月4日 下午5:43:40
 */

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("org.cloud.usercenter.mapper")
public class ServiceUserLaunch implements CommandLineRunner{
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceUserLaunch.class, args);
	}
    
    @Override
	public void run(String... arg0) throws Exception {
		log.info("用户中心撸起来");
	}
}
