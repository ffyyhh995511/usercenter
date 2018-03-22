package org.cloud.service.user.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fangyunhe
 * @date 2017年9月7日 下午1:43:47
 * 
 */
@FeignClient(name = "CLOUD-SERVICE-USER", fallback = WebUserServiceFallbackImpl.class)
public interface WebUserService {
    
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	Object upLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestHeader("appId") String appId, @RequestHeader("appSecret") String appSecret);

	@RequestMapping(value = "/check/ticket", method = RequestMethod.GET)
	Object ticketCheck(@RequestParam("ticket") String ticket, @RequestHeader("appId") String appId,
			@RequestHeader("appSecret") String appSecret);
	
}
