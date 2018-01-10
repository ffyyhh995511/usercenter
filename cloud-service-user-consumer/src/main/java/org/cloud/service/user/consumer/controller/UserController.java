package org.cloud.service.user.consumer.controller;

import org.cloud.service.user.consumer.entity.User;
import org.cloud.service.user.consumer.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Product Controller
 *
 * @author CD826(CD826Dong@gmail.com)
 * @since 1.0.0
 */
@RestController
@RequestMapping("/test/user")
public class UserController {
    @Autowired
    private WebUserService webUserService;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object login(User user) {
    	return webUserService.upLogin(user.getUsername(),user.getPassword());
    }
}

