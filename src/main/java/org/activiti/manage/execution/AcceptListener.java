package org.activiti.manage.execution;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.manage.context.ConnectSession;
import org.activiti.manage.tools.MyTestUtil;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.Session;

import com.rmi.server.entity.FlowData;
import com.rmi.server.entity.Neaten;
import com.rmi.server.entity.RoomInfoFlowIdEntity;

import common.HttpClient;

public class AcceptListener implements ExecutionListener{
	
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

		int input=(int) taskMap.get("input");
		String userId =(String) taskMap.get("userId");
		System.out.println("userId="+userId);
		Neaten neaten=(Neaten) taskMap.get("neaten");

		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		
		FlowData flowData=(FlowData) taskMap.get("flowData");

		Session session=new ConnectSession().get();
		
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=new RoomInfoFlowIdEntity();
		
		roomInfoFlowIdEntity.setCurrentOpenId(taskEntity.getAssignee());
		roomInfoFlowIdEntity.setResult(input);		
		roomInfoFlowIdEntity.setUpdate_time(new Date());
		roomInfoFlowIdEntity.setState(1);
		
		session.beginTransaction();
		
		int i=session.createQuery("update RoomInfoFlowIdEntity set currentOpenId=? , update_time=?,"
				+ "state=? , result=? where processInstanceId=?")
				.setString(0, taskEntity.getAssignee()).setDate(1, new Date())
				.setInteger(2, 1).setInteger(3, 2)
				.setString(4, taskEntity.getProcessInstanceId()).executeUpdate();

		if (i < 1) {
			session.getTransaction().rollback();
			throw new Exception();
		}
		
		session.getTransaction().commit();

		List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		reqParam.add(new BasicNameValuePair("openId", userId));
		reqParam.add(new BasicNameValuePair("Template_Id", "M_TnyO6o3U6bImli9xsfXhL-rCskh9YYaSzCLWMdbJM"));
		reqParam.add(new BasicNameValuePair("Send_Type", "整改审批"));
		reqParam.add(new BasicNameValuePair("url", "http://lzgfgs.com/voucher/mobile/flow/myTask.html"));
		reqParam.add(new BasicNameValuePair("first_data", "审核时间:"+time));
		reqParam.add(new BasicNameValuePair("keyword1_data", neaten.getNeaten_item() + "整改维修申请"));
		reqParam.add(new BasicNameValuePair("keyword2_data", "维修申请方案已通过,请提交验收申请"));
		reqParam.add(new BasicNameValuePair("keyword3_data", ""));
		reqParam.add(new BasicNameValuePair("keyword4_data", ""));
		reqParam.add(new BasicNameValuePair("keyword5_data", ""));
		reqParam.add(new BasicNameValuePair("remark_data", flowData.getRemark_data()));
		
		httpClient.doGet(requestUrl, reqParam);
		
	}

}
