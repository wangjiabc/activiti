package org.activiti.manage.execution;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;


public class StartListener implements ExecutionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Autowired
	ProcessEngineConfiguration processEngineFactory;
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("execution="+execution);
		String executionId=execution.getId();
		
		TaskEntity taskEntity=(TaskEntity) execution.getEngineServices().getTaskService().createTaskQuery().executionId(executionId).singleResult();
		
	}



}
