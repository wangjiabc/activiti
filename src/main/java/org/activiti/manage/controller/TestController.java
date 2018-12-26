package org.activiti.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.manage.context.Connect;
import org.activiti.manage.dao.UserDAO;
import org.activiti.manage.daoModel.Users;
import org.activiti.manage.h.daoImpl.ProcessDaoImpl;
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

import com.rmi.server.entity.RoomInfoFlowIdEntity;

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
	
	private AffairService affairService;
	
	@Autowired
	private org.activiti.manage.h.daoImpl.testDaoImpl testDaoImpl;
	
	@Autowired
	ProcessDaoImpl processDaoImpl;
	
	@Transactional(rollbackFor = { Exception.class })
	@Autowired
	public void setaffairService(AffairService affairService) {
		this.affairService=affairService;
	}
	
	
	@RequestMapping("affair1")
	public @ResponseBody
	Integer affair1() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 1);
		paramMap.put("val", "dddddd");
		
		int i=0;
		/*
		i=affairService.insertAll(paramMap);	 
       */
		
		System.out.println(affairService);
		
		i=affairService.insert1(paramMap);
		
		
		return i;
	}

	
	@RequestMapping("affair2")
	public @ResponseBody
	Integer affair2() throws Exception{
	
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", 2);
		paramMap.put("val", "fffffffff");
		
		int i=0;
		
	    i=affairService.insert2(paramMap);
    
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
		
					
			testDaoImpl.save2(1);
			
		}
	
	// 插入数据 persist 相当于hibernate save方法
	@RequestMapping(value = "/save")
	public void save() throws Exception {
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=new RoomInfoFlowIdEntity();
		
		roomInfoFlowIdEntity.setCurrentDate(new Date());
		processDaoImpl.save(roomInfoFlowIdEntity);

	}

	// 插入数据 persist 相当于hibernate save方法
	@RequestMapping(value = "/del")
	public void del(@RequestParam String id) throws Exception {

		processDaoImpl.del(id);

	}
	
	// 插入数据 persist 相当于hibernate save方法
		@RequestMapping(value = "/select")
		public @ResponseBody Map select(@RequestParam Integer r,@RequestParam int limit,@RequestParam int offset) throws Exception {

			return processDaoImpl.selectAll(r, limit, offset);

		}

	// 插入数据 persist 相当于hibernate save方法
	@RequestMapping(value = "/selectById")
	public @ResponseBody Map selectById(@RequestParam String openId,@RequestParam Integer r, @RequestParam int limit, @RequestParam int offset)
			throws Exception {

		return processDaoImpl.selectByOpenId(openId, r, limit, offset);

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
