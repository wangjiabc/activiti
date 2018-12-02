package org.activiti.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.manage.context.Connect;
import org.activiti.manage.dao.UserDAO;
import org.activiti.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.voucher.manage.model.Users;


@RequestMapping(value = "/user")
@Controller
public class UserController {

	ApplicationContext applicationContext=new Connect().get();
	
	UserDAO userDAO= (UserDAO) applicationContext.getBean("dao");
	
	UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/get")
	public @ResponseBody List get(){
		List list=new ArrayList<>();
		
		List list2=new ArrayList<>();

		list=userService.getAllFullUser(1, 1000, 0, null, null, "");
		
        Iterator iterator=list.iterator();
        
        while (iterator.hasNext()) {
			Users users=(Users) iterator.next();
			Map a=new HashMap<>();
			a.put("id", users.getOpenId());
			a.put("name", users.getNickname());
			a.put("description", users.getPlace());
			list2.add(a);
		}
		
		return list2;
		
	}
	
}
