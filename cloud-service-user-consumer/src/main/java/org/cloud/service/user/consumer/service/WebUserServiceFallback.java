package org.cloud.service.user.consumer.service;

import org.cloud.service.user.consumer.entity.User;
import org.cloud.service.user.consumer.response.Response;
import org.springframework.stereotype.Component;

@Component
public class WebUserServiceFallback implements WebUserService{

	public Response<String> upRegister(User user) {
		return Response.interiorErrorResponse();
	}

	@Override
	public Object upLogin(String username, String password) {
		return Response.interiorErrorResponse();
	}

}
