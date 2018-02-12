/**
 * 
 */
package org.cloud.service.zipkin.server;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import zipkin.server.EnableZipkinServer;
import zipkin.storage.mysql.MySQLStorage;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月19日 下午3:29:16
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerLaunch {
	public static void main(String[] args) {
        SpringApplication.run(ZipkinServerLaunch.class, args);
    }
	
	@Bean
    public MySQLStorage mySQLStorage(DataSource datasource) {
        return MySQLStorage.builder().datasource(datasource).executor(Runnable::run).build();
    }
}
