package org.cloud.service.user.consumer.service;

import org.cloud.service.user.consumer.response.Response;
import org.springframework.stereotype.Component;

@Component
public class WebUserServiceFallbackImpl implements WebUserService{

	@Override
	public Object upLogin(String username, String password, String appId, String appSecret) {
		return Response.interiorErrorResponse();
	}

	@Override
	public Object ticketCheck(String ticket, String appId, String appSecret) {
		return Response.interiorErrorResponse();
	}

}
