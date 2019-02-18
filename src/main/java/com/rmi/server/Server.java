package com.rmi.server;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;


import com.alibaba.fastjson.JSONObject;

public interface Server {

	public Map startProcessInstance(@RequestParam String processDefinitionKey,
			@RequestParam String userId,@RequestParam String variableData,
			@RequestParam List imageDataList,@RequestParam String className) throws Exception;
	
	public Map findMyPersonalTask(@RequestParam String assignee,@RequestParam Integer limit,@RequestParam Integer offset);
	
	public Long findMyPersonalTaskCount(@RequestParam String assignee,@RequestParam Date afterDate);
	
	public Long selectCountAfter(String openId,Date afterDate);
	
	public String toRoute(@RequestParam String taskId,@RequestParam String className);
	
	public Object findMyPersonalTaskById(@RequestParam String id);
	
	public Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className,@RequestParam List imageDataList) throws Exception;
	
	public Map findHistoryById(@RequestParam String id);
	
	public JSONObject selectAttachMent(@RequestParam Integer limit,@RequestParam Integer offset);
	
	public Map findMyAllHistory(@RequestParam String assignee,@RequestParam Integer limit,@RequestParam Integer offset);
	
	public Map findAllHistory(@RequestParam Integer limit,@RequestParam Integer offset);
	
	public Map selectAll(@RequestParam Integer result,@RequestParam int limit,@RequestParam int offset);
	
	public Map selectAllByState(@RequestParam Integer state,@RequestParam int limit,@RequestParam int offset);
	
	public Map selectById(String guid,int result,int limit,int offset);
	
	public Map selectByGuid(String guid);
	
	public Map selectByOpenId(@RequestParam String openId, @RequestParam Integer r, @RequestParam int limit,
			@RequestParam int offset);
	
	public Integer del(String guid)throws Exception;
	
	public byte[] readResource(@RequestParam String executionId)throws Exception;
	
}
