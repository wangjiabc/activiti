package org.activiti.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.manage.factory.FlowFactory;
import org.activiti.manage.h.daoImpl.ProcessDaoImpl;
import org.activiti.manage.mapper.ReDeploymentMapper;
import org.activiti.manage.tools.MyTestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmi.server.entity.ImageData;
import com.voucher.manage.model.Users;

@RequestMapping("/flow")
@Controller  
public class FlowController {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	HistoryService historyService;

	@Autowired
	ProcessEngineConfiguration processEngineFactory;

	@Autowired
	ReDeploymentMapper reDeploymentMapper;
	   
	@Autowired
	ProcessDaoImpl processDaoImpl;
	
	/** 启动流程实例 **/
	@RequestMapping(value = "/start")
	public @ResponseBody Map startProcessInstance(@RequestParam String processDefinitionKey,
			@RequestParam String userId,@RequestParam String variableData,@RequestParam String className,String img) {
		// 流程定义的key
		
		Map map = new HashMap<>();
		
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			processEngineFactory.getIdentityService().setAuthenticatedUserId(userId);
			
			List list=new ArrayList<>();
			
			if(img!=null){
				ImageData imageData=new ImageData();
				imageData.setName("aaa");
				imageData.setDate(new Date());
				imageData.setURI(img);
				list.add(imageData);
			}
			
			ProcessInstance pi = new FlowFactory(className).getProduct().start(userId,processDefinitionKey, variableData,
					list,processEngineFactory);
			map.put("state", "succeed");
			
			System.out.println("pi="+pi);
			
			if (pi != null) {
				map.put("流程实例ID:", pi.getId());// 流程实例ID 101
				map.put("流程定义ID:", pi.getProcessDefinitionId());// 流程定义ID
			} else {
				map.put("state", "failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("state", "failed");
		}

		return map;

	}

	/** 查询当前人的个人任务 */
	@RequestMapping(value = "/findMyTask")
	public @ResponseBody List findMyPersonalTask(@RequestParam String assignee) {
		List<Task> list = processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.createTaskQuery()// 创建任务查询
				.taskAssigneeLike(assignee)// 指定个人任查询，指定办理人
				.orderByTaskCreateTime().desc().list();
		List list2 = new ArrayList<>();
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {

			Map map = new HashMap<>();
			Task task = (Task) iterator.next();
			map.put("任务ID:", task.getId());
			map.put("任务名称:", task.getName());
			map.put("任务的创建时间:", task.getCreateTime());
			map.put("任务的办理人:", task.getAssignee());
			map.put("流程实例ID:", task.getProcessInstanceId());
			map.put("执行对象ID:", task.getExecutionId());
			map.put("流程定义ID:", task.getProcessDefinitionId());

			list2.add(map);
		}

		return list2;
	}

	
	/** 跳转到任务页面 */
	@RequestMapping(value = "/toRoute")
	public String toRoute(@RequestParam String taskId,@RequestParam String userId,@RequestParam String className) {

		String path=new FlowFactory(className).getProduct().route(taskId,processEngineFactory,historyService);
		
		return path;

	}
		
	/** 查询当前任务 */
	@RequestMapping(value = "/findMyTaskById")
	public @ResponseBody Object findMyPersonalTaskById(@RequestParam String id) {

		Object object = null;

		try {
			 //object=processEngineFactory.getRuntimeService().getVariables(id);
			object = processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
					.getVariables(id);
			 
			MyTestUtil.print(object);
		} catch (org.activiti.engine.ActivitiObjectNotFoundException e) {
			// TODO: handle exception
			return "ERROR：ID:" + id;
		}
		//Vacation vacation=(Vacation) object;
		return object;

	}

	/** 完成我的任务 */
	@RequestMapping(value = "/personalTask")
	public @ResponseBody Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className,String img) {

		Map map = new HashMap<>();

		List list=new ArrayList<>();
		
		if(img!=null){
			ImageData imageData=new ImageData();
			imageData.setName("aaa");
			imageData.setDate(new Date());
			imageData.setURI(img);
			list.add(imageData);
		}
		
		try {
			
			new FlowFactory(className).getProduct().personalTask(taskId,input,variableData,list,processEngineFactory,historyService);
			map.put("完成任务：任务ID:", taskId);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("state", "failed");
			map.put("ERROR：任务ID:", taskId);
		}

		return map;

	}
		
	 /**获取全部历史流程实例*/
    @RequestMapping(value = "/findHistoryById")
    public @ResponseBody List findHistoryById(@RequestParam String id,HttpServletResponse response){
    	
    	List list=historyService.createHistoricDetailQuery()
    	.variableUpdates()
    	.taskId(id)
    	.orderByVariableName().asc()
    	.list();
    	
    	return list;
    	
    }
	
    
}
