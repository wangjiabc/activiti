package com.rmi.server;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

public interface Server {

	public Map startProcessInstance(@RequestParam String processDefinitionKey,
			@RequestParam String userId,@RequestParam String variableData,@RequestParam String className);
	
	public List findMyPersonalTask(@RequestParam String assignee);
	
	
	public String toRoute(@RequestParam String taskId,@RequestParam String userId,@RequestParam String className);
	
	public Object findMyPersonalTaskById(@RequestParam String id);
	
	public Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className);
	
	public List findHistoryById(@RequestParam String id,HttpServletResponse response);
	
	public JSONObject selectAttachMent(@RequestParam Integer limit,@RequestParam Integer offset);
	
	public byte[] readResource(@RequestParam String executionId)throws Exception;
	
}
