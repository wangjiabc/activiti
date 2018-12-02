package org.activiti.manage.product;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.manage.factory.FlowProduct;
import org.json.JSONObject;

import com.rmi.server.entity.Vacation;

public class test1 extends FlowProduct{

	@Override
	public ProcessInstance start(String userId,String processDefinitionKey, String variableData,ProcessEngineConfiguration processEngineFactory) {
		// TODO Auto-generated method stub
		
		try {
			
			Vacation vacation=new Vacation();
			
			jsonObject= new JSONObject(variableData);
			vacation.setUname(jsonObject.getString("username"));
			vacation.setAge(jsonObject.getInt("age"));
			variables.put("vacation", vacation);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("variableData="+variables);
		
		ProcessInstance pi = processEngineFactory.getRuntimeService()// 与正在执行
				// 的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKey, variables); // 使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		return pi;
	}

	@Override
	public void personalTask(String taskId,Integer input,String variableData,
			ProcessEngineConfiguration processEngineFactory,HistoryService historyService) {
		// TODO Auto-generated method stub
		
		Vacation vacation = (Vacation) processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.getVariable(taskId,"vacation");
		
		try {

			jsonObject = new JSONObject(variableData);
			vacation.setUname(jsonObject.getString("username"));
			vacation.setAge(jsonObject.getInt("age"));
			variables.put("vacation", vacation);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		processEngineFactory.getTaskService().setVariables(taskId, variables);
		processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.complete(taskId, variables);
		
	}

	@Override
	public String route(String taskId,String userId,HistoryService historyService) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
