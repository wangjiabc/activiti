package org.activiti.manage.factory;

import java.util.Map;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;

public class FlowFactory {
	
	private String processDefinitionKey;
	
	private Map<String, Object> variables;
	
	private Class c;
	
	private String className;
	
	public FlowFactory(String className) {
		// TODO Auto-generated constructor stub
		this.className=className;
	}

	public FlowProduct getProduct(){
		
		FlowProduct flowProduct = null;
		
		try {
			c = Class.forName("org.activiti.manage.product."+className);
			flowProduct=(FlowProduct) c.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flowProduct;
	}
	
}
