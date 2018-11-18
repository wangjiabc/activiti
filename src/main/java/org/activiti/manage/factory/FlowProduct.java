package org.activiti.manage.factory;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.json.JSONObject;

public abstract class FlowProduct {
	
	protected JSONObject jsonObject=new JSONObject();
	
	protected Map<String, Object> variables=new HashMap<>();
	
	public FlowProduct() {

	}

	public abstract ProcessInstance start(String processDefinitionKey, String variableData,ProcessEngineConfiguration processEngineFactory);
	
}
