package org.activiti.manage.execution;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.manage.context.ConnectSession;
import org.activiti.manage.context.DBUtils;
import org.activiti.manage.tools.MyTestUtil;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.rmi.server.entity.FlowData;
import com.rmi.server.entity.Neaten;
import com.rmi.server.entity.RoomInfoFlowIdEntity;
import com.sun.org.glassfish.external.statistics.annotations.Reset;

import common.HttpClient;

public class EndListener implements ExecutionListener{
	
	private static final long serialVersionUID = 1L;

	private static final String requestUrl = "http://127.0.0.1:8080/voucher/mobile/WechatSendMessage/send.do";
	
	private static HttpClient httpClient = new HttpClient();
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("execution="+execution);
		String executionId=execution.getId();

		TaskEntity taskEntity=(TaskEntity)execution.getEngineServices().getTaskService().createTaskQuery().executionId(executionId).singleResult();
				
		System.out.println("end map=");
		
		MyTestUtil.print(taskEntity);
		
		Map taskMap=taskEntity.getActivityInstanceVariables();

		MyTestUtil.print(taskMap);
		/*
		ExecutionEntity executionEntity=taskEntity.getExecution();
		
		List<IdentityLinkEntity> identityLinkEntity=executionEntity.getIdentityLinks();
		
		Iterator iterator=identityLinkEntity.iterator();
		
		int i=0;
		
		while (iterator.hasNext()) {
			System.out.println("identityLinkEntity="+i);
			MyTestUtil.print(iterator.next());
			i++;
		}
		
		String userId = identityLinkEntity.get(0).getUserId();
		
		*/
		int input=(int) taskMap.get("input");
		String userId =(String) taskMap.get("userId");
		System.out.println("userId="+userId);
		Neaten neaten=(Neaten) taskMap.get("neaten");

		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		
		/*
		String sql="update roominfo_flowid  set Result_='"+input+"', Update_time_='"+time+"'"
				+ ",State_ = 0  where GUID_='"+neaten.getGUID()+"' "
				+ " and ProcessInstanceId_='"+taskEntity.getProcessInstanceId()+"'";
		
		System.out.println("sql="+sql);
		
		Connection connection=DBUtils.getConnection();
		
		PreparedStatement prep = connection.prepareStatement(sql);  
        int i= prep.executeUpdate(sql);
   
        if(i<1){
        	throw new Exception();
        }
        */
		
		FlowData flowData=(FlowData) taskMap.get("flowData");
						
		String result="";

		Session session=new ConnectSession().get();
		
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=new RoomInfoFlowIdEntity();
		
		roomInfoFlowIdEntity.setCurrentOpenId(taskEntity.getAssignee());
		roomInfoFlowIdEntity.setResult(input);		
		roomInfoFlowIdEntity.setUpdate_time(new Date());
		roomInfoFlowIdEntity.setState(0);
		
		session.beginTransaction();
		
		int i=session.createQuery("update RoomInfoFlowIdEntity set currentOpenId=? , update_time=?,"
				+ "state=? , result=? where processInstanceId=?")
				.setString(0, taskEntity.getAssignee()).setDate(1, new Date())
				.setInteger(2, 0).setInteger(3, input)
				.setString(4, taskEntity.getProcessInstanceId()).executeUpdate();

		if (i < 1) {
			session.getTransaction().rollback();
			throw new Exception();
		}
		
		if(input==1){
			result="已通过";
		}else{
			result="已拒绝";
			
			i=session.createQuery("delete RoomInfoFlowIdEntity where processInstanceId=?").setString(0, taskEntity.getProcessInstanceId()).executeUpdate();
		       
	        if(i<1){
	        	session.getTransaction().rollback();
	        	throw new Exception();
	        }
			
		}

		session.getTransaction().commit();

		List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		reqParam.add(new BasicNameValuePair("openId", userId));
		reqParam.add(new BasicNameValuePair("Template_Id", "M_TnyO6o3U6bImli9xsfXhL-rCskh9YYaSzCLWMdbJM"));
		reqParam.add(new BasicNameValuePair("Send_Type", "整改审批"));
		reqParam.add(new BasicNameValuePair("url", "http://nwx.wtsms.net/voucher/mobile/1/flow/myTask.html"));
		reqParam.add(new BasicNameValuePair("first_data", "审核时间:"+time));
		reqParam.add(new BasicNameValuePair("keyword1_data", neaten.getNeaten_item() + "整改维修"));
		reqParam.add(new BasicNameValuePair("keyword2_data", result));
		reqParam.add(new BasicNameValuePair("keyword3_data", ""));
		reqParam.add(new BasicNameValuePair("keyword4_data", ""));
		reqParam.add(new BasicNameValuePair("keyword5_data", ""));
		reqParam.add(new BasicNameValuePair("remark_data", flowData.getRemark_data()));
		
		httpClient.doGet(requestUrl, reqParam);
		
	}

}
