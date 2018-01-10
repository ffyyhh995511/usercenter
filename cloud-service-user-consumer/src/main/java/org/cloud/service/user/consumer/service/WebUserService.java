package org.cloud.service.user.consumer.service;

import org.cloud.service.user.consumer.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fangyunhe
 * @date 2017年9月7日 下午1:43:47
 * 
 */
@FeignClient(name = "CLOUD-SERVICE-USER", fallback = WebUserServiceFallback.class)
public interface WebUserService {
    
    @RequestMapping(value="/web/user/up/register",method=RequestMethod.POST)
	Object upRegister(User user);

	@RequestMapping(value="/web/user/up/login",method=RequestMethod.POST)
	Object upLogin(@RequestParam("username") String username, @RequestParam("password") String password);
//	Object upLogin(@RequestBody User user);
	
}
