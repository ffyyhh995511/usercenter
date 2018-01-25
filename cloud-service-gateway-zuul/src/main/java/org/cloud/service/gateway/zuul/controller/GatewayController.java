/**
 * 
 */
package org.cloud.service.gateway.zuul.controller;

import java.util.Map;

import org.cloud.service.gateway.zuul.event.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月25日 下午4:34:53
 */
@RestController
@Slf4j
public class GatewayController {
	@Autowired
    RefreshRouteService refreshRouteService;

    @RequestMapping("/refreshRoute")
    public String refreshRoute(){
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("/watchNowRoute")
    public String watchNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return "watchNowRoute";
    }
}
