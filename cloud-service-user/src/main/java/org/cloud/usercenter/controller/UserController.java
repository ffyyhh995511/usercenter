/**
 * 
 */
package org.cloud.usercenter.controller;


import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.cloud.usercenter.dto.AuthorizationDto;
import org.cloud.usercenter.entity.User;
import org.cloud.usercenter.response.Response;
import org.cloud.usercenter.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户统一登录、校验
 * @author:fangyunhe
 * @time:2018年1月4日 下午5:08:41
 */

@Slf4j
@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	/**
	 * 账号密码方式注册username、password
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public Object register(User user){
		if(user == null || !StringUtils.isNoneBlank(user.getUsername(),user.getPassword())) {
			return Response.paramsCheckFailResponse("账号密码不能为空");
		}
		try {
			Response<User> result = userService.saveUsernameAndPassword(user);
			return result;
		} catch (Exception e) {
			log.error("注册失败",e);
		}
		return Response.failedResponse("注册失败");
	}
	
	/**
	 * 账号密码方式登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public Object login(User user){
		if(!StringUtils.isNoneBlank(user.getUsername(),user.getPassword())) {
			return Response.paramsCheckFailResponse("账号密码不能为空");
		}
		try {
			Response<AuthorizationDto> result = userService.loginUsernameAndPassword(user);
			return result;
		} catch (Exception e) {
			log.error("登录失败",e);
		}
		return Response.failedResponse("登录失败");
	}
	
	/**
	 * 退出
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	public Object logout(){
		try {
		} catch (Exception e) {
			log.error("退出失败",e);
		}
		return Response.failedResponse("退出失败");
	}
	
}
