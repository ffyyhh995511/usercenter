package org.cloud.usercenter.inteceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.cloud.usercenter.common.ConmonConstant;
import org.cloud.usercenter.config.BusinessConfig;
import org.cloud.usercenter.entity.BusinessType;
import org.cloud.usercenter.response.Response;
import org.cloud.usercenter.util.RedisUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * appId、appSecret校验
 * 
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月12日 上午10:44:08
 */
@Slf4j
public class AppIdRequestInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		if (checkRequest(request)) {
			return true;
		} else {
			sendErrorResponse(response);
			return false;
		}
	}

	private boolean checkRequest(HttpServletRequest request) {
		String appId = request.getHeader("appId");
		String appSecret = request.getHeader("appSecret");
		if (!StringUtils.isNoneBlank(appId, appSecret)) {
			log.warn("appId:{} appSecret:{} 为空", appId, appSecret);
			return false;
		}
		String value = RedisUtil.hget(ConmonConstant.BUSINESS_TYPE, appId);
		BusinessType bt = JSON.parseObject(value, BusinessType.class);
		if (bt != null && bt.getAppSecret().equals(appSecret)) {
			return true;
		}
		log.warn("appId:{} appSecret:{} 校验不通过", appId, appSecret);
		return false;
	}

	/**
	 * @param res
	 */
	private void sendErrorResponse(HttpServletResponse res) {
		Response<String> authResponse = Response.authResponse("appId和appSecret校验不通过");
		res.setCharacterEncoding("UTF-8");
		res.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = res.getWriter();
			out.append(JSON.toJSONString(authResponse));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
