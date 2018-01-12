/**
 * 
 */
package org.cloud.usercenter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.cloud.usercenter.common.ConmonConstant;
import org.cloud.usercenter.entity.BusinessType;
import org.cloud.usercenter.mapper.BusinessTypeMapper;
import org.cloud.usercenter.util.RedisUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月12日 上午10:20:50
 */
@Service
public class BusinessTypeService {
	@Resource
	BusinessTypeMapper businessTypeMapper;
	
	@PostConstruct
	public void init() {
		List<BusinessType> businessTypes = businessTypeMapper.queryAll();
		Map<String,String> btHash = new HashMap<String,String>();
		for (BusinessType bt : businessTypes) {
			btHash.put(String.valueOf(bt.getAppId()), JSON.toJSONString(bt));
		}
		RedisUtil.hmset(ConmonConstant.BUSINESS_TYPE, btHash);
	}
}
