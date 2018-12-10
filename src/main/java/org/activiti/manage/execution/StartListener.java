package org.activiti.manage.execution;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.manage.context.ConnectSession;
import org.activiti.manage.tools.MyTestUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.rmi.server.entity.Neaten;
import com.rmi.server.entity.RoomInfoFlowIdEntity;


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
		String processInstanceId=execution.getProcessInstanceId();

		Map exeMap=execution.getVariables();
		Neaten neaten=(Neaten) exeMap.get("neaten");
		
		String userId =(String) exeMap.get("userId");
		
		Session session=new ConnectSession().get();
		
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=new RoomInfoFlowIdEntity();
		
		roomInfoFlowIdEntity.setProcessInstanceId(processInstanceId);
		roomInfoFlowIdEntity.setOpenId(userId);
		roomInfoFlowIdEntity.setGuid(neaten.getGUID());
		roomInfoFlowIdEntity.setApplicationUser(neaten.getApplicationUser());
		roomInfoFlowIdEntity.setAddress(neaten.getAddress());
		roomInfoFlowIdEntity.setType(neaten.getType());
		roomInfoFlowIdEntity.setResult(0);
		roomInfoFlowIdEntity.setDate(new Date());
		roomInfoFlowIdEntity.setUpdate_time(new Date());
		roomInfoFlowIdEntity.setState(1);

		session.beginTransaction();
		
		Serializable result =session.save(roomInfoFlowIdEntity);
		
		int i=(int) result;
                
        if(i<1){
        	session.getTransaction().rollback();
        	throw new Exception();
        }
        
        session.getTransaction().commit();
	}



}
