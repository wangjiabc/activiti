package org.activiti.manage.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.manage.context.ConnectSession;
import org.activiti.manage.factory.FlowProduct;
import org.activiti.manage.tools.MyTestUtil;
import org.hibernate.Session;
import org.json.JSONObject;

import com.rmi.server.entity.Deliveran;
import com.rmi.server.entity.FlowData;
import com.rmi.server.entity.Neaten;
import com.rmi.server.entity.RoomInfoFlowIdEntity;

public class test extends FlowProduct{
	
	Session session=new ConnectSession().get();
	
	@Override
	public ProcessInstance start(String userId,String processDefinitionKey, String variableData,
			ProcessEngineConfiguration processEngineFactory) throws Exception{
		// TODO Auto-generated method stub
		
		String address="";

		JSONObject jsonObject;
		
		Map<String, Object> variables=new HashMap<>();
		
		jsonObject = new JSONObject(variableData);

		Neaten neaten = new Neaten();

		FlowData flowData=new FlowData();
		
		List<Deliveran> list=new ArrayList<>();

		Date date=new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(new Date());

		address = jsonObject.getString("address");
		neaten.setGUID(jsonObject.getString("guid"));
		neaten.setNeaten_item(jsonObject.getString("neatenitem"));
		neaten.setAddress(jsonObject.getString("address"));
		neaten.setHappen_time(sdf.parse(jsonObject.getString("happenTime")));
		neaten.setPrincipal(jsonObject.getString("principal"));
		neaten.setRemark(jsonObject.getString("remark"));
		neaten.setNeaten_instance(jsonObject.getString("neaten_instance"));
		neaten.setAddComp(jsonObject.getString("addComp"));
		neaten.setLng(jsonObject.getDouble("lng"));
		neaten.setLat(jsonObject.getDouble("lat"));
		neaten.setType(jsonObject.getString("type"));
		neaten.setArea(jsonObject.getFloat("area"));
		neaten.setAmount(jsonObject.getFloat("amount"));
		neaten.setWorkUnit(jsonObject.getString("workUnit"));
		neaten.setCheckItemDate(jsonObject.getString("checkItemDate"));
		neaten.setDate(date);
		neaten.setApplicationUser(jsonObject.getString("username"));

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time2 = sdf2.format(date);

		flowData.setTemplate_Id("9iK_AqQpwEZPew9-TgMAy1zyuBGfB1pbuZ3DBGuEXz0");
		flowData.setSend_Type("维修审批");
		flowData.setFirst_data(address + "维修申请");
		flowData.setKeyword1_data(jsonObject.getString("username"));
		flowData.setKeyword2_data(jsonObject.getString("neatenitem") + "整改维修");
		flowData.setKeyword3_data(time2);
		flowData.setKeyword4_data("正在申请");
		flowData.setRemark_data("金额:" + jsonObject.getFloat("amount"));
		flowData.setUrl("http://lzgfgs.com/voucher/mobile/flow/myTask.html");

		Deliveran deliveran = new Deliveran();

		deliveran.setContent("提交申请");
		deliveran.setUserName(jsonObject.getString("username"));
		deliveran.setDate(date);

		list.add(deliveran);

		flowData.setDeliverans(list);

		
		variables.put("neaten", neaten);

		variables.put("flowData", flowData);
		
		System.out.println("variables="+variables);
		
		MyTestUtil.print(variables);
		
		ProcessInstance pi = null;

		pi = processEngineFactory.getRuntimeService() // 与正在执行的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKey, variables); // 使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		// 流程添加标题
		processEngineFactory.getRuntimeService().setProcessInstanceName(pi.getId(), address + "维修申请");

		return pi;
		
	}

	
	@Override
	public String route(String taskId,ProcessEngineConfiguration processEngineFactory,HistoryService historyService) {
		// TODO Auto-generated method stub
		String currentUserId=historyService.getHistoricIdentityLinksForTask(taskId).get(0).getUserId();
		
		Map taskMap=processEngineFactory.getTaskService().getVariables(taskId);
		
		String userId=(String) taskMap.get("userId");
		
		String path;
		
		String processInstanceId=processEngineFactory.getTaskService().getTaskEvents(taskId).get(0).getProcessInstanceId();

		List<RoomInfoFlowIdEntity> list=session.createQuery("from RoomInfoFlowIdEntity where processInstanceId=? ")
				.setString(0, processInstanceId).list();
		
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=list.get(0);
		
		int state=roomInfoFlowIdEntity.getState();
		
		if (state > 1) {
			if (userId.equals(currentUserId)) {
				path = "/testFlow/reTask";
			} else {
				path = "/testFlow/task";
			}
		} else {
			if (userId.equals(currentUserId)) {
				path = "/testFlow/acceptPut";
			} else {
				path = "/testFlow/acceptTask";
			}
		}
		
		return path;
	}
	
	@Override
	public void personalTask(String taskId,Integer input, String variableData, ProcessEngineConfiguration processEngineFactory,HistoryService historyService) {
		// TODO Auto-generated method stub
		Neaten neaten= (Neaten) processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.getVariable(taskId,"neaten");
		
		FlowData flowData=(FlowData) processEngineFactory.getTaskService().getVariable(taskId, "flowData");
		
		String currentUserId=historyService.getHistoricIdentityLinksForTask(taskId).get(0).getUserId();
		
		Map taskMap=processEngineFactory.getTaskService().getVariables(taskId);
		
		JSONObject jsonObject;
		
		Map<String, Object> variables=new HashMap<>();
		
		Date date=new Date();
		

		SimpleDateFormat sdf = new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ");
		String time = sdf.format(new Date());

		jsonObject = new JSONObject(variableData);

		System.out.println("currentUserId=" + currentUserId);
		System.out.println("userId=" + taskMap.get("userId"));

		String processInstanceId=processEngineFactory.getTaskService().getTaskEvents(taskId).get(0).getProcessInstanceId();
		
		List<RoomInfoFlowIdEntity> RoomInfoFlowIdList=session.createQuery("from RoomInfoFlowIdEntity where processInstanceId=? ")
				.setString(0, processInstanceId).list();
		
		RoomInfoFlowIdEntity roomInfoFlowIdEntity=RoomInfoFlowIdList.get(0);
		
		int state=roomInfoFlowIdEntity.getState();
		
		if (state > 1) {
			
			if (!currentUserId.equals(taskMap.get("userId"))) {

				flowData.setTemplate_Id("9iK_AqQpwEZPew9-TgMAy1zyuBGfB1pbuZ3DBGuEXz0");
				flowData.setKeyword2_data(neaten.getNeaten_item() + "整改维修");
				flowData.setKeyword3_data(sdf.format(neaten.getDate()));
				flowData.setKeyword4_data("正在申请");
				flowData.setRemark_data("金额:" + jsonObject.getFloat("amount"));
				flowData.setUrl("http://lzgfgs.com/voucher/mobile/1/flow/myTask.html");

				List<Deliveran> list = flowData.getDeliverans();

				Deliveran deliveran = new Deliveran();

				System.out.println("content=" + jsonObject.getString("content"));
				System.out.println("username=" + jsonObject.getString("username"));

				deliveran.setContent(jsonObject.getString("content"));
				deliveran.setUserName(jsonObject.getString("username"));
				deliveran.setDate(date);
				MyTestUtil.print(deliveran);
				list.add(deliveran);

				flowData.setDeliverans(list);

				variables.put("input", input);

				variables.put("flowData", flowData);
				MyTestUtil.print(flowData);
				MyTestUtil.print(variables);
				System.out.println("variablesuserid=" + taskMap.get("userId"));

			} else {

				neaten.setType(jsonObject.getString("type"));
				neaten.setNeaten_instance(jsonObject.getString("neaten_instance"));
				neaten.setPrincipal(jsonObject.getString("principal"));
				neaten.setRemark(jsonObject.getString("remark"));
				neaten.setArea(jsonObject.getFloat("area"));
				neaten.setAmount(jsonObject.getFloat("amount"));
				neaten.setWorkUnit(jsonObject.getString("workUnit"));

				flowData.setTemplate_Id("9iK_AqQpwEZPew9-TgMAy1zyuBGfB1pbuZ3DBGuEXz0");
				flowData.setKeyword2_data(neaten.getNeaten_item() + "整改维修");
				flowData.setKeyword3_data(sdf.format(neaten.getDate()));
				flowData.setKeyword4_data("正在申请");
				flowData.setRemark_data("金额:" + jsonObject.getFloat("amount"));
				flowData.setUrl("http://lzgfgs.com/voucher/mobile/flow/myTask.html");

				variables.put("input", input);

				variables.put("neaten", neaten);

				variables.put("flowData", flowData);

			}
			
		}else{
			
			if (!currentUserId.equals(taskMap.get("userId"))) {

				flowData.setTemplate_Id("9iK_AqQpwEZPew9-TgMAy1zyuBGfB1pbuZ3DBGuEXz0");
				flowData.setKeyword2_data(neaten.getNeaten_item() + "维修验收申请");
				flowData.setKeyword3_data(sdf.format(neaten.getDate()));
				flowData.setKeyword4_data("正在申请");
				flowData.setRemark_data("金额:" + jsonObject.getFloat("amountTotal"));
				flowData.setUrl("http://lzgfgs.com/voucher/mobile/1/flow/myTask.html");

				List<Deliveran> list = flowData.getDeliverans();

				Deliveran deliveran = new Deliveran();

				System.out.println("content=" + jsonObject.getString("content"));
				System.out.println("username=" + jsonObject.getString("username"));

				deliveran.setContent(jsonObject.getString("content"));
				deliveran.setUserName(jsonObject.getString("username"));
				deliveran.setDate(date);
				MyTestUtil.print(deliveran);
				list.add(deliveran);

				flowData.setDeliverans(list);

				variables.put("input", input);

				variables.put("flowData", flowData);
				MyTestUtil.print(flowData);
				MyTestUtil.print(variables);
				System.out.println("variablesuserid=" + taskMap.get("userId"));

			} else {

				neaten.setType(jsonObject.getString("type"));
				neaten.setNeaten_instance(jsonObject.getString("neaten_instance"));
				neaten.setPrincipal(jsonObject.getString("principal"));
				neaten.setRemark(jsonObject.getString("remark"));
				neaten.setArea(jsonObject.getFloat("area"));
				neaten.setAmount(jsonObject.getFloat("amount"));
				neaten.setAmountTotal(jsonObject.getFloat("amountTotal"));
				neaten.setAuditingAmount(jsonObject.getFloat("auditingAmount"));
				neaten.setAvailabeLength(jsonObject.getString("availabeLength"));
				neaten.setWorkUnit(jsonObject.getString("workUnit"));

				flowData.setTemplate_Id("9iK_AqQpwEZPew9-TgMAy1zyuBGfB1pbuZ3DBGuEXz0");
				flowData.setKeyword2_data(neaten.getNeaten_item() + "维修验收申请");
				flowData.setKeyword3_data(sdf.format(neaten.getDate()));
				flowData.setKeyword4_data("正在申请");
				flowData.setRemark_data("金额:" + jsonObject.getFloat("amountTotal"));
				flowData.setUrl("http://lzgfgs.com/voucher/mobile/flow/myTask.html");

				variables.put("input", input);

				variables.put("neaten", neaten);

				variables.put("flowData", flowData);

			}
			
		}
		
		if(input==2){
			
			flowData.setTemplate_Id("LWfojRihMettsLzgs72r4oP86UIBRsEvrUKeMZbRpM4");
			flowData.setKeyword2_data(sdf.format(neaten.getDate()));
			flowData.setKeyword3_data(sdf.format(new Date()));
			flowData.setRemark_data("请修改后重新提交审批");
			flowData.setUrl("http://lzgfgs.com/voucher/mobile/1/flow/myTask.html");
			
			variables.put("flowData", flowData);
			
		}
		
		System.out.println("variables=");
		
		MyTestUtil.print(variables);
	
		processEngineFactory.getTaskService().setVariables(taskId, variables);
		processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.complete(taskId, variables);
			
	}



}
