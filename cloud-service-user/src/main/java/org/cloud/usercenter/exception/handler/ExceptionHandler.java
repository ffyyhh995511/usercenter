/**
 * 
 */
package org.cloud.usercenter.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.cloud.usercenter.exception.GException;
import org.cloud.usercenter.response.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;



import lombok.extern.slf4j.Slf4j;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年3月21日 下午4:57:48
 * @version 1.0	
 */
@Slf4j
@ControllerAdvice
@Component
public class ExceptionHandler {
	
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(GException.class)
    Response<Exception> handGException(HttpServletRequest request, GException e) {
    	log.error(e.getMessage(), e);
        return Response.interiorErrorResponse(e.getErrorCode(), e.getMsg());
	}

	@ResponseBody
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	Response<Exception> handException(HttpServletRequest request, Exception e) {
		log.error(e.getMessage(), e);
		return Response.interiorErrorResponse();
	}
}
