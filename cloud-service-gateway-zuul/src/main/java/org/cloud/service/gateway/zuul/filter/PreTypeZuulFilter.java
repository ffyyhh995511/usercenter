/**
 * 
 */
package org.cloud.service.gateway.zuul.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import com.netflix.zuul.ZuulFilter;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月19日 上午11:35:53
 */
public class PreTypeZuulFilter extends ZuulFilter {
	protected Logger logger = LoggerFactory.getLogger(PreTypeZuulFilter.class);

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		this.logger.info("This is pre-type zuul filter.");
		return null;
	}

}
