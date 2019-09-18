package org.activiti.manage.factory;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;

public abstract class FlowProduct {

	public FlowProduct() {

	}

	public abstract ProcessInstance start(String userId,String processDefinitionKey, String variableData,List imageDataList,ProcessEngineConfiguration processEngineFactory)throws Exception;
	
	public abstract String route(String taskId,ProcessEngineConfiguration processEngineFactory,HistoryService historyService);
	
	public abstract void personalTask(String taskId,Integer input,String variableData,List imageDataList,ProcessEngineConfiguration processEngineFactory,HistoryService historyService)throws Exception;

	
}