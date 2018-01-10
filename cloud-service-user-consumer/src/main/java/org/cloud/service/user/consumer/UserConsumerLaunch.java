package org.cloud.service.user.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * spring-cloud 消费者demo
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月10日 下午3:13:25
 */

@EnableCircuitBreaker //断路器支持、容错
@EnableFeignClients(basePackages = "org.**")
@EnableDiscoveryClient
@SpringBootApplication
public class UserConsumerLaunch {

    public static void main(String[] args) {
        SpringApplication.run(UserConsumerLaunch.class, args);
    }

}
