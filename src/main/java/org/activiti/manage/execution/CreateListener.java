package org.activiti.manage.execution;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.manage.context.ConnectSession;
import org.activiti.manage.context.DBUtils;
import org.activiti.manage.h.daoImpl.ProcessDaoImpl;
import org.activiti.manage.tools.MyTestUtil;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmi.server.entity.FlowData;
import com.rmi.server.entity.Neaten;
import com.rmi.server.entity.RoomInfoFlowIdEntity;

import common.HttpClient;

public class CreateListener implements TaskListener{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	private static final String requestUrl = "http://127.0.0.1:8080/voucher/mobile/WechatSendMessage/send.do";
	
	private static HttpClient httpClient = new HttpClient();
	
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		
		String taskId=delegateTask.getId();
		
		String assignee=delegateTask.getAssignee();
		
		Map map=delegateTask.getExecution().getEngineServices().getTaskService().getVariables(taskId);
		
		System.out.println("task assignee="+assignee);
		
		MyTestUtil.print(map);
		
		Neaten neaten=(Neaten) map.get("neaten");
		
		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());
		/*
		String sql="update roominfo_flowid set  Update_time_='"+time+"'  where GUID_='"+neaten.getGUID()+"'";
		
		System.out.println("sql="+sql);
		
		Connection connection;
		
		try {
			connection = DBUtils.getConnection();
			PreparedStatement prep = connection.prepareStatement(sql);  
	        prep.executeUpdate(sql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 */
		
		Session session=new ConnectSession().get();
		
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=new RoomInfoFlowIdEntity();
		
		roomInfoFlowIdEntity.setCurrentOpenId(assignee);
		roomInfoFlowIdEntity.setUpdate_time(new Date());

		session.beginTransaction();
		
		session.createQuery("update RoomInfoFlowIdEntity set currentOpenId=? , Update_time_=? where processInstanceId=?")
				.setString(0, assignee).setDate(1, new Date()).setString(2, delegateTask.getProcessInstanceId()).executeUpdate();

        session.getTransaction().commit();
		
		FlowData flowData=(FlowData) map.get("flowData");
		
		List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		reqParam.add(new BasicNameValuePair("openId", assignee));
		reqParam.add(new BasicNameValuePair("Template_Id", flowData.getTemplate_Id()));
		reqParam.add(new BasicNameValuePair("Send_Type", flowData.getSend_Type()));
		reqParam.add(new BasicNameValuePair("url", flowData.getUrl()));
		reqParam.add(new BasicNameValuePair("first_data", flowData.getFirst_data()));
		reqParam.add(new BasicNameValuePair("keyword1_data", flowData.getKeyword1_data()));
		reqParam.add(new BasicNameValuePair("keyword2_data", flowData.getKeyword2_data()));
		reqParam.add(new BasicNameValuePair("keyword3_data", flowData.getKeyword3_data()));
		reqParam.add(new BasicNameValuePair("keyword4_data", flowData.getKeyword4_data()));
		reqParam.add(new BasicNameValuePair("keyword5_data", flowData.getKeyword5_data()));
		reqParam.add(new BasicNameValuePair("remark_data", flowData.getRemark_data()));
		
		httpClient.doGet(requestUrl, reqParam);
		
	}

}
