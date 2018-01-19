/**
 * 
 */
package org.cloud.service.zipkin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月19日 下午3:29:16
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerLaunch {
	public static void main(String[] args) {
        SpringApplication.run(ZipkinServerLaunch.class, args);
    }
}
