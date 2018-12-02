package org.activiti.manage.product;

import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.manage.factory.FlowProduct;
import org.json.JSONObject;

import com.rmi.server.entity.Hidden_Neaten;

public class neaten extends FlowProduct{

	@Override
	public ProcessInstance start(String userId,String processDefinitionKey, String variableData,
			ProcessEngineConfiguration processEngineFactory) {
		// TODO Auto-generated method stub
		try {
			
			jsonObject= new JSONObject(variableData);
			
			Hidden_Neaten hidden_Neaten=new Hidden_Neaten();
			
			hidden_Neaten.setGUID(jsonObject.getString("guid"));
			hidden_Neaten.setNeaten_id(jsonObject.getString("neaten_id"));
			hidden_Neaten.setNeaten_name(jsonObject.getString("neaten_name"));			
			hidden_Neaten.setNeaten_instance(jsonObject.getString("neaten_instance"));
			hidden_Neaten.setArea(jsonObject.getFloat("area"));
			hidden_Neaten.setType(jsonObject.getString("type"));
			hidden_Neaten.setAmount(jsonObject.getFloat("amount"));
			hidden_Neaten.setWorkUnit(jsonObject.getString("workUnit"));
			
			variables.put("hidden_Neaten", hidden_Neaten);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("variableData="+variables);
		
		ProcessInstance pi = null;
		
		try {
			pi = processEngineFactory.getRuntimeService()// 与正在执行
					// 的流程实例和执行对象相关的Service
					.startProcessInstanceByKey(processDefinitionKey, variables); // 使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return pi;
		
	}

	
	@Override
	public String route(String taskId,String userId,HistoryService historyService) {
		// TODO Auto-generated method stub
		String currentUserId=historyService.getHistoricIdentityLinksForTask(taskId).get(0).getUserId();
		
		String path;
		
		System.out.println("currentUserId="+currentUserId);
		System.out.println("userId="+userId);
		
		if(userId.equals(currentUserId)){
			path="testFlow/reTask";
		}else{
			path="testFlow/task";
		}
		
		return path;
	}
	
	@Override
	public void personalTask(String taskId,Integer input, String variableData, ProcessEngineConfiguration processEngineFactory,HistoryService historyService) {
		// TODO Auto-generated method stub
		Hidden_Neaten hidden_Neaten= (Hidden_Neaten) processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.getVariable(taskId,"hidden_Neaten");
		
		String currentUserId=historyService.getHistoricIdentityLinksForTask(taskId).get(0).getUserId();
		
		Map taskMap=processEngineFactory.getTaskService().getVariables(taskId);
		
		try {

			jsonObject= new JSONObject(variableData);
			
			hidden_Neaten.setGUID(jsonObject.getString("guid"));
			hidden_Neaten.setNeaten_id(jsonObject.getString("neaten_id"));
			hidden_Neaten.setNeaten_name(jsonObject.getString("neaten_name"));			
			hidden_Neaten.setNeaten_instance(jsonObject.getString("neaten_instance"));
			hidden_Neaten.setArea(jsonObject.getFloat("area"));
			hidden_Neaten.setType(jsonObject.getString("type"));
			hidden_Neaten.setAmount(jsonObject.getFloat("amount"));
			hidden_Neaten.setWorkUnit(jsonObject.getString("workUnit"));
						
			variables.put("input", input);

			System.out.println("variablesuserid="+taskMap.get("userId"));
			
			if(currentUserId.equals(taskMap.get("userId"))){
				
				variables.put("hidden_Neaten", hidden_Neaten);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		String aaa=historyService.getHistoricIdentityLinksForTask(taskId).get(0).getUserId();
		
		System.out.println("aaa="+aaa);
		
		processEngineFactory.getTaskService().setVariables(taskId, variables);
		processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.complete(taskId, variables);
	}



}
