package com.rmi.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.manage.factory.FlowFactory;
import org.activiti.manage.h.daoImpl.ProcessDaoImpl;
import org.activiti.manage.mapper.ReDeploymentMapper;
import org.activiti.manage.tools.FileConvect;
import org.activiti.manage.tools.MyTestUtil;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

//继承该类， UnicastRemoteObject，暴露远程服务  
public class ServerImpl implements Server {  

	@Autowired  
    private RepositoryService repositoryService;  
    
    @Autowired
    private RuntimeService runtimeService;
	
	@Autowired
	HistoryService historyService;

	@Autowired
	ProcessEngineConfiguration processEngineFactory;

	@Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;
	
	@Autowired
	ReDeploymentMapper reDeploymentMapper;
	   
	@Autowired
	ProcessDaoImpl processDaoImpl;
	
	/** 启动流程实例 **/
	public Map startProcessInstance(@RequestParam String processDefinitionKey,
			@RequestParam String userId,@RequestParam String variableData,@RequestParam String className) {
		// 流程定义的key
		
		Map map = new HashMap<>();
		
		try {
			// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
			processEngineFactory.getIdentityService().setAuthenticatedUserId(userId);
			
			ProcessInstance pi = new FlowFactory(className).getProduct().start(userId,processDefinitionKey, variableData,
					processEngineFactory);
			map.put("state", "succeed");
			
			System.out.println("pi="+pi);
			
			if (pi != null) {
				map.put("流程实例ID:", pi.getId());// 流程实例ID 101
				map.put("流程定义ID:", pi.getProcessDefinitionId());// 流程定义ID
			} else {
				map.put("state", "failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("state", "failed");
		}

		return map;

	}

	/** 查询当前人的个人任务 */
	public List findMyPersonalTask(@RequestParam String assignee) {
		List<Task> list = processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
				.createTaskQuery()// 创建任务查询
				.taskAssigneeLike(assignee)// 指定个人任查询，指定办理人
				.orderByTaskCreateTime().desc().list();
		List list2 = new ArrayList<>();
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {

			Map map = new HashMap<>();
			Task task = (Task) iterator.next();
			map.put("任务ID:", task.getId());
			map.put("任务名称:", task.getName());
			map.put("任务的创建时间:", task.getCreateTime());
			map.put("任务的办理人:", task.getAssignee());
			map.put("流程实例ID:", task.getProcessInstanceId());
			map.put("执行对象ID:", task.getExecutionId());
			map.put("流程定义ID:", task.getProcessDefinitionId());

			list2.add(map);
		}

		return list2;
	}

	
	/** 跳转到任务页面 */
	public String toRoute(@RequestParam String taskId,@RequestParam String userId,@RequestParam String className) {

		String path=new FlowFactory(className).getProduct().route(taskId, userId, historyService);
		
		return path;

	}
	
	
	/** 查询当前任务 */
	public Object findMyPersonalTaskById(@RequestParam String id) {

		Object object = null;

		try {
			 //object=processEngineFactory.getRuntimeService().getVariables(id);
			object = processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
					.getVariables(id);
			 
			System.out.println("object=");
			MyTestUtil.print(object);
		} catch (org.activiti.engine.ActivitiObjectNotFoundException e) {
			// TODO: handle exception
			return "ERROR：ID:" + id;
		}
		//Vacation vacation=(Vacation) object;
		return object;

	}

	/** 完成我的任务 */
	public Map completeMyPersonalTask(@RequestParam String taskId,@RequestParam Integer input,@RequestParam String variableData,
			@RequestParam String className) {

		Map map = new HashMap<>();

		try {
			
			new FlowFactory(className).getProduct().personalTask(taskId,input,variableData,processEngineFactory,historyService);
			map.put("完成任务：任务ID:", taskId);

		} catch (org.activiti.engine.ActivitiObjectNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("state", "failed");
			map.put("ERROR：任务ID:", taskId);
		}

		return map;

	}
	
	
	 /**获取全部历史流程实例*/
    public List findHistoryById(@RequestParam String id,HttpServletResponse response){
    	
    	List list=historyService.createHistoricDetailQuery()
    	.variableUpdates()
    	.taskId(id)
    	.orderByVariableName().asc()
    	.list();
    	
    	return list;
    	
    }

    
    public JSONObject selectAttachMent(@RequestParam Integer limit,@RequestParam Integer offset){
   	 List<Task> resultList = processEngineFactory.getTaskService()//与正在执行的任务管理相关的Service
					.createTaskQuery().orderByTaskCreateTime().desc().listPage(offset, limit);
   	 long total=processEngineFactory.getTaskService()//与正在执行的任务管理相关的Service
					.createTaskQuery().count();
        MyTestUtil.print(resultList);
        Iterator iterator=resultList.iterator();
        List list=new ArrayList<>();
        while(iterator.hasNext()){
       	 Task task=(Task) iterator.next();
       	 Map map=new HashMap<>();
       	 map.put("id",task.getId());
       	 map.put("owner",task.getOwner());
       	 map.put("name",task.getName());
       	 map.put("assignee",task.getAssignee());
       	 map.put("description",task.getDescription());
       	 map.put("executionId",task.getExecutionId());
       	 map.put("processInstanceId",task.getProcessInstanceId());
       	 map.put("processDefinitionId",task.getProcessDefinitionId());
       	 map.put("createTime",task.getCreateTime());
       	 list.add(map);
       	 System.out.println(map);
        }
        JSONObject resultJson = new JSONObject();
        resultJson.put("rows", list);
        resultJson.put("total", total);
        return resultJson;
    }
    
    
    /**
     * 读取带跟踪的图片
     */
    public byte[] readResource(@RequestParam String executionId)throws Exception {
        
    	String pathRoot = System.getProperty("user.home");
		
		String filePath=pathRoot+"\\Desktop\\pasoft\\photo\\flow";
    	
		HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(executionId).singleResult();
        
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        
        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(executionId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);

        for(HistoricActivityInstance tempActivity : highLightedActivitList){
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

        //中文显示的是口口口，设置字体就好了
        InputStream inStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体","宋体",null, 1.0);
          
        File filepath=new File(filePath);
        
        if(!filepath.exists()){
            filepath.mkdirs();//创建目录
            System.out.println("目录不存在");
        }
        
        File file=new File(filePath+"\\"+executionId+".png");
        
        int bytesum = 0; 
        int byteread = 0; 
        FileOutputStream fs = new FileOutputStream(filePath+"\\"+executionId+".png");
        byte[] buffer = new byte[1444]; 
		int length; 
		while ( (byteread = inStream.read(buffer)) != -1) { 
			bytesum += byteread; //字节数 文件大小 
			System.out.println(bytesum); 
			fs.write(buffer, 0, byteread); 
		} 
		inStream.close(); 
		fs.close();

		byte[] fileByte=FileConvect.fileToByte(file);
        
		System.out.println("fileByte="+filePath);
		
        return fileByte;
        
    }
    
    
    /**
     * 获取需要高亮的线
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(
            ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
    
}  