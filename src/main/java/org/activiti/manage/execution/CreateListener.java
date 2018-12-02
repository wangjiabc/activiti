package org.activiti.manage.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.http.message.BasicNameValuePair;

import com.rmi.server.entity.Hidden_Neaten;

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
		
		Hidden_Neaten hidden_Neaten=(Hidden_Neaten) map.get("hidden_Neaten");
		
		List<BasicNameValuePair> reqParam = new ArrayList<BasicNameValuePair>();
		reqParam.add(new BasicNameValuePair("openId", assignee));
		reqParam.add(new BasicNameValuePair("Template_Id", "1vQfPSl4pSvi5UnmmDhVtueutq2R1w7XYRMts294URg"));
		reqParam.add(new BasicNameValuePair("Send_Type", "整改审批"));
		reqParam.add(new BasicNameValuePair("url", ""));
		reqParam.add(new BasicNameValuePair("first_data", hidden_Neaten.getRoomGUID()));
		reqParam.add(new BasicNameValuePair("keyword1_data", hidden_Neaten.getNeaten_name()));
		reqParam.add(new BasicNameValuePair("keyword2_data", hidden_Neaten.getNeaten_instance()));
		reqParam.add(new BasicNameValuePair("keyword3_data", hidden_Neaten.getType()));
		reqParam.add(new BasicNameValuePair("keyword4_data", String.valueOf(hidden_Neaten.getAmountTotal())));
		reqParam.add(new BasicNameValuePair("keyword5_data", assignee));
		reqParam.add(new BasicNameValuePair("remark_data", assignee));
		
		httpClient.doGet(requestUrl, reqParam);
		
	}

}
