package org.activiti.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.manage.context.Connect;
import org.activiti.manage.dao.UserDAO;
import org.activiti.manage.daoModel.Users;
import org.activiti.manage.entity.Users1;
import org.activiti.manage.entity.Vacation;
import org.activiti.manage.mapper.ReDeploymentMapper;
import org.activiti.manage.service.AffairService;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;




@RequestMapping("/test")
@Controller 
public class TestController {

	ApplicationContext applicationContext=new Connect().get();
	
	UserDAO userDAO= (UserDAO) applicationContext.getBean("dao");
	
	@Autowired  
    private RepositoryService repositoryService;  
    
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;
    
    @Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;
    
    @Autowired
    ProcessEngineConfiguration processEngineFactory;
    
    
   @Autowired
   ReDeploymentMapper reDeploymentMapper;
	
	private AffairService testService;
	
	@Transactional(rollbackFor = { Exception.class })
	@Autowired
	public void setTestService(AffairService testService) {
		this.testService=testService;
	}
	
	
	@RequestMapping("affair1")
	public @ResponseBody
	Integer affair1() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 1);
		paramMap.put("val", "dddddd");
		
		int i=0;
		/*
		i=testService.insertAll(paramMap);	 
       */
		
		System.out.println(testService);
		
		i=testService.insert1(paramMap);
		
		
		return i;
	}

	
	@RequestMapping("affair2")
	public @ResponseBody
	Integer affair2() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 2);
		paramMap.put("val", "fffffffff");
		
		int i=0;
		
	    i=testService.insert2(paramMap);
    
		return i;
	}
	
	@RequestMapping(value = "/addUser")
	public @ResponseBody int addUser(@RequestParam int id,
			@RequestParam String username,@RequestParam String password) {
		
		  Users users=new Users();
		  
		  users.setId(id);
		  users.setUsername(username);
		  users.setPassword(password);

		  return userDAO.addAll(users);
	}
	
	@RequestMapping(value = "/selectUser")
	public @ResponseBody List selectUser() {

		  
		  return userDAO.findAll();
		
	}
	
	
	// 插入数据 persist 相当于hibernate save方法
	@RequestMapping(value = "/insert")
	 public void testInsert() throws Exception {
		
			Users1 users = new Users1("1", "小明", 18);
			
			Vacation vacation=new Vacation();
			
			vacation.setDays(2);
			
		}
	 
	
	@RequestMapping(value = "/def")
	 public void def() throws Exception {
		
		String[] defs = { "org/activiti/manage/jpa/JPASpringTest.bpmn20.xml" };
	    for (String pd : defs)
	      repositoryService.createDeployment().addClasspathResource(pd).deploy();
		
	}
	
	
	@RequestMapping(value = "/start")
	 public void start() throws Exception {
		
		Map<String, Object> variables = new HashMap<String, Object>();
	    variables.put("customerName", "Jane Doe");
	    variables.put("amount", 50000);

	    ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("LoanRequestProcess", variables);
		
	}
	
}
