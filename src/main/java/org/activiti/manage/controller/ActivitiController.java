package org.activiti.manage.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;  
  
  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.DeploymentEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.manage.jpa.LoanRequest;
import org.activiti.manage.mapper.ReDeploymentMapper;
import org.activiti.manage.model.ProcdefEntity;
import org.activiti.manage.tools.MyTestUtil;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.formula.functions.EDate;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;  
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;  
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.voucher.manage.model.Users;

import org.activiti.explorer.navigation.NavigatorManager;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;


@Controller  
public class ActivitiController {  
	
    @Autowired  
    private RepositoryService repositoryService;  
    
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;
    
    @Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;
    
    @Autowired
    ProcessEngineConfiguration processEngineFactory;
    
    
   @Autowired
   ReDeploymentMapper reDeploymentMapper;
    
   
    /** 
     * 查询生日列表 
     *  
     * @param req 
     * @return 
     */  
    @RequestMapping(value = "/activiti", method = RequestMethod.GET, produces = "application/json;charset=utf-8")  
    @ResponseBody  
    public Object brithdayList(HttpServletRequest req) {  
        Map<String, Object> map = new HashMap<String, Object>();  
        map.put("name", "中文 乱码");  
        MyTestUtil.print(map);
        return map;  
    }  
  
  
    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json;charset=utf-8")  
    public void create(  
            @RequestParam("name") String name,  
            @RequestParam("key") String key,  
            @RequestParam(value = "description", required = false) String description,  
            HttpServletRequest request, HttpServletResponse response) {  
        try {  
            ObjectMapper objectMapper = new ObjectMapper();  
            ObjectNode modelObjectNode = objectMapper.createObjectNode();  
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);  
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);  
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,  
                    org.apache.commons.lang3.StringUtils  
                            .defaultString(description));  
            Model newModel = repositoryService.newModel();  
            newModel.setMetaInfo(modelObjectNode.toString());  
            newModel.setName(name);  
            newModel.setKey(key);  
            repositoryService.saveModel(newModel);  
            ObjectNode editorNode = objectMapper.createObjectNode();  
            editorNode.put("id", "canvas");  
            editorNode.put("resourceId", "canvas");  
            ObjectNode stencilSetNode = objectMapper.createObjectNode();  
            stencilSetNode.put("namespace",  
                    "http://b3mn.org/stencilset/bpmn2.0#");  
            editorNode.put("stencilset", stencilSetNode);  
            repositoryService.addModelEditorSource(newModel.getId(), editorNode  
                    .toString().getBytes("utf-8"));  
            response.sendRedirect(request.getContextPath()  
                    + "/modeler.html?modelId=" + newModel.getId());  
        } catch (Exception e) {  
            e.getStackTrace();  
        }  
    } 
    
    
    /**
     * 查询 客户端分页
     * @return
     */
     @RequestMapping(value="/selectAllModel")
     @ResponseBody
     public String selectAllModel(){
         List<Model> resultList =  repositoryService.createModelQuery().orderByCreateTime().desc().list();
         long total=repositoryService.createModelQuery().count();
         JSONObject resultJson = new JSONObject();
         resultJson.put("rows", resultList);
         resultJson.put("total", total);
         return resultJson.toString();
     }
    
     
     @RequestMapping(value="/deleteModel")
     @ResponseBody
     public Map deleteModel(@RequestParam String modelIds){

 			Map<String, Object> map = new HashMap<String, Object>();
 			
 			String[] modelIdStrings = modelIds.split(",");
 			System.out.println("modelIdStrings="+modelIdStrings);
 			// 娑擄拷濞嗏�冲灩闂勩倕顦挎稉顏堟祩妞嬶拷
 			for (String modelIdString : modelIdStrings) {
 				try{
 					repositoryService.deleteModel(modelIdString);
 				}catch (Exception e) {
					// TODO: handle exception
 					e.printStackTrace();
 					
 					map.put("status", "flase");
 					map.put("message", "删除失败");
 					
 					return map;
				}
 			}

 			map.put("status", "success");
			map.put("message", "删除成功");

 		return map;
     }
    
     @RequestMapping(value="/selectHistory")
     @ResponseBody
     public String selectHistory(){
         List<HistoricProcessInstance> resultList =  historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceEndTime().desc().list();
         long rows=historyService.createHistoricProcessInstanceQuery().count();
         JSONObject resultJson = new JSONObject();
         resultJson.put("data", resultList);
         resultJson.put("rows", rows);
         return resultJson.toString();
     }
    
     @RequestMapping(value="/selectDeploy")
     @ResponseBody
     public String selectDeploy(){
    	 /*
         List<Deployment> resultList =  repositoryService.createDeploymentQuery().list();
         Iterator iterator=resultList.iterator();
         List list=new ArrayList<>();
         while(iterator.hasNext()){
        	 Deployment deployment=(Deployment) iterator.next();
        	 System.out.println("deployment="+deployment);
        	 Map map=new HashMap<>();
        	 map.put("id", deployment.getId());
        	 map.put("name", deployment.getName());
        	 map.put("category", deployment.getCategory());
        	 map.put("tenant_id",deployment.getTenantId());
        	 map.put("time", deployment.getDeploymentTime());
        	 list.add(map);
         }
         MyTestUtil.print(list);
         System.out.println("resultList="+resultList);
         */
    	 
    	 List list=new ArrayList<>();
    	 list=reDeploymentMapper.selectAll();
         long rows=repositoryService.createDeploymentQuery().count();
         JSONObject resultJson = new JSONObject();
         resultJson.put("data", list);
         resultJson.put("rows", rows);
         return resultJson.toString();
     }
     
     
     @RequestMapping(value="/selectProcdef")
     @ResponseBody
     public String selectProcdef(@RequestParam Integer limit,@RequestParam Integer offset){
         List<ProcessDefinition> resultList = repositoryService.createProcessDefinitionQuery()
        		 .orderByDeploymentId().orderByProcessDefinitionVersion()
        		 .desc().listPage(offset, limit);
         List<Deployment> deploymentList=repositoryService.createDeploymentQuery()
        		 .orderByDeploymentId().orderByDeploymenTime()
        		 .desc().listPage(offset, limit);
         long total=repositoryService.createProcessDefinitionQuery().count();
         MyTestUtil.print(resultList);
         List list=new ArrayList<>();
         Iterator iterator=resultList.iterator();
         Iterator iterator2=deploymentList.iterator();
         while(iterator.hasNext()){
        	 ProcessDefinition processDefinition=(ProcessDefinition) iterator.next();
        	 ProcdefEntity procdefEntity=new ProcdefEntity();
        	 procdefEntity.setId(processDefinition.getId());
        	 procdefEntity.setKey(processDefinition.getKey());
        	 procdefEntity.setName(processDefinition.getName());
        	 procdefEntity.setDeploymentId(processDefinition.getDeploymentId());
        	 procdefEntity.setResourceName(processDefinition.getResourceName());
        	 procdefEntity.setDescription(processDefinition.getDescription());
        	 procdefEntity.setDiagramResourceName(processDefinition.getDiagramResourceName());
        	 procdefEntity.setCategory(processDefinition.getCategory());
        	 Deployment deployment=(Deployment) iterator2.next();
        	 if(deployment.getId().equals(processDefinition.getDeploymentId())){
        		 procdefEntity.setDeploymentTime(deployment.getDeploymentTime());
        	 }
        	 list.add(procdefEntity);
         }
         JSONObject resultJson = new JSONObject();
         resultJson.put("rows", list);
         resultJson.put("total", total);
         return resultJson.toString();
     }
     
     
     @RequestMapping(value="/deleteProcdef")
     @ResponseBody
     public Map deleteProcdef(@RequestParam String deploymentIds){

			Map<String, Object> map = new HashMap<String, Object>();
			
			String[] deploymentIdStrings = deploymentIds.split(",");
			System.out.println("deploymentIdStrings="+deploymentIdStrings);
			// 娑擄拷濞嗏�冲灩闂勩倕顦挎稉顏堟祩妞嬶拷
			for (String deploymentIdString : deploymentIdStrings) {
				try{
					repositoryService.deleteDeployment(deploymentIdString);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					
					map.put("status", "flase");
					map.put("message", "删除失败");
					
					return map;
				}
			}

			map.put("status", "success");
			map.put("message", "删除成功");

		return map;    	 
     }
     
     @RequestMapping(value="/selectAttachMent")
     @ResponseBody
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
     
     
     @RequestMapping(value="/deleteAttachMent")
     @ResponseBody
     public Map deleteAttachMent(@RequestParam String processInstanceIds){

    	 Map<String, Object> map = new HashMap<String, Object>();
			
			String[] processInstanceIdStrings = processInstanceIds.split(",");
			System.out.println("processInstanceIdStrings="+processInstanceIdStrings);
			// 娑擄拷濞嗏�冲灩闂勩倕顦挎稉顏堟祩妞嬶拷
			for (String processInstanceId : processInstanceIdStrings) {
				try{
					//runtimeService.suspendProcessInstanceById(processInstanceId); //冻结
					runtimeService.deleteProcessInstance(processInstanceId, "");
					processEngineFactory.getTaskService().deleteTask(processInstanceId);
				}catch (org.activiti.engine.ActivitiException e) {
					// TODO: handle exception
					e.printStackTrace();
					
					map.put("status", "flase");
					map.put("message", "删除失败");
					
					return map;
				}
			}

			map.put("status", "success");
			map.put("message", "删除成功");

		return map;
     }
     
     /**
      * 导出model的xml文件
      */
     @RequestMapping(value = "/export1")
     public @ResponseBody String export1(@RequestParam String modelId, HttpServletResponse response) {
         try {
             Model modelData = repositoryService.getModel(modelId);
             BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
             //获取节点信息
             byte[] arg0 = repositoryService.getModelEditorSource(modelData.getId());
             JsonNode editorNode = new ObjectMapper().readTree(arg0);
             //将节点信息转换为xml
             BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

             BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
             byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

             ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
             IOUtils.copy(in, response.getOutputStream());
             
             in.close();
             
             DataInputStream dis = new DataInputStream(in); 
             String newName = dis.readUTF(); 

             return newName;
             
         }catch (java.io.EOFException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return null;
     }
    
     
     @RequestMapping(value = "/export")
     public @ResponseBody ModelAndView export(@RequestParam String modelId, HttpServletResponse response) {
    	 
    	 ModelAndView mav = new ModelAndView();

    	 mav.setViewName("modelEditor");

    	 return mav;
    	 
     }
     
     
     @RequestMapping(value="/saveModel")
     public @ResponseBody Integer saveModel(@RequestParam String id,@RequestParam String xml,@RequestParam String name,String description) {
         try{
         String unescapeXml = StringEscapeUtils.unescapeXml(xml);//因过滤处理XSS时会对<,>等字符转码，此处需将字符串还原
         InputStream   in_nocode   =   new   ByteArrayInputStream(unescapeXml.getBytes("UTF-8"));
         XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();  
         XMLStreamReader reader = xmlFactory.createXMLStreamReader(in_nocode);

         BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
         BpmnModel bpmnModel = xmlConverter.convertToBpmnModel(reader);
         MyTestUtil.print(bpmnModel);
         BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
         JsonNode j =jsonConverter.convertToJson(bpmnModel);

         byte[] modelEditorSource = new ObjectMapper().writeValueAsBytes(j);

         MultiValueMap<String,String> values = new LinkedMultiValueMap<String,String>();
         values.add("json_xml", new String(modelEditorSource,"UTF-8"));
         values.add("svg_xml", "");
         values.add("name", name);
         values.add("description", description);

         MyTestUtil.print(values);
         
         Model model = repositoryService.getModel(id);

         org.codehaus.jackson.JsonNode modelJson =new org.codehaus.jackson.map.ObjectMapper().readTree(model.getMetaInfo());
        
         System.out.println(modelJson.toString());
         
         com.alibaba.fastjson.JSONObject objectNode=(JSONObject) JSONObject.parse(modelJson.toString());
         
         objectNode.put("name", values.getFirst("name"));
         objectNode.put("description", values.getFirst("description"));
         
         System.out.println("objectNode="+objectNode.toJSONString());
         
         model.setMetaInfo(objectNode.toString());
         model.setName(values.getFirst("name"));

         repositoryService.saveModel(model);

         System.out.println("values="+values.getFirst("json_xml").getBytes("utf-8"));
         
         repositoryService.addModelEditorSource(model.getId(), values.getFirst("json_xml").getBytes("utf-8"));

         }catch(Exception e){
             e.printStackTrace();
             return 0;
         }
         return 1;
     } 

     
     /**
      * 部署
      */
     @RequestMapping(value = "deploy",method=RequestMethod.GET)
     @ResponseBody
     public Integer deploy(@RequestParam("modelId") String modelId, HttpServletRequest request) {
    	 int i=0;
         try {
             Model modelData = repositoryService.getModel(modelId);
             ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
             byte[] bpmnBytes = null;
             BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
             bpmnBytes = new BpmnXMLConverter().convertToXML(model);
             String processName = modelData.getName() + ".bpmn20.xml";
             Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes,"utf-8")).deploy();
             i=1;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return i;
     }
     
     /**
      * 导出model的xml文件
      */
     @RequestMapping(value = "/exportDeploy")
     public @ResponseBody Object exportDeploy(@RequestParam String deploymentId, HttpServletResponse response) {
         try {
        	 BpmnModel bpmnModel = repositoryService.getBpmnModel(deploymentId);

             BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
             byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

             ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
             IOUtils.copy(in, response.getOutputStream());
            /* String filename = modelData.getName() + ".bpmn20.xml";
             response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
             response.flushBuffer();
             */
             return in;
             
         } catch (Exception e){
             PrintWriter out = null;
             try {
                 out = response.getWriter();
             } catch (IOException e1) {
                 e1.printStackTrace();
             }
             out.write("未找到对应数据");
             e.printStackTrace();
         }
         return "error";
     }

  	/**启动流程实例**/
     @RequestMapping(value = "/start")
     public @ResponseBody Map startProcessInstance(@RequestParam String processDefinitionKey,Integer days){
 		//流程定义的key
 
    	Map<String, Object> variables = new HashMap<String, Object>();
 	    variables.put("customerName", "xxxxxx");
 	    variables.put("amount", 243232);
 	    
    	ProcessInstance pi = processEngineFactory.getRuntimeService()//与正在执行	的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(processDefinitionKey,variables);  //使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
    	
     	Map map=new HashMap<>();
		
 		map.put("流程实例ID:",pi.getId());//流程实例ID   101
 		map.put("流程定义ID:",pi.getProcessDefinitionId());//流程定义ID
 		
 		return map;
 	}
     
  	/**查询当前人的个人任务*/
     @RequestMapping(value = "/findMyTask")
     public @ResponseBody List findMyPersonalTask(@RequestParam String assignee){
 		 List<Task> list = processEngineFactory.getTaskService()//与正在执行的任务管理相关的Service
 						.createTaskQuery()//创建任务查询
 						.taskAssigneeLike("%"+assignee+"%")//指定个人任查询，指定办理人
 						.orderByTaskCreateTime().desc()
 						.list(); 
 		List list2=new ArrayList<>();
 		Iterator iterator=list.iterator();
 		while (iterator.hasNext()) {
 			
 			Map map=new HashMap<>();
 			Task task=(Task) iterator.next();
 			map.put("任务ID:",task.getId());
 			map.put("任务名称:",task.getName());
 			map.put("任务的创建时间:",task.getCreateTime());
 			map.put("任务的办理人:",task.getAssignee());
 			map.put("流程实例ID:",task.getProcessInstanceId());
 			map.put("执行对象ID:",task.getExecutionId());
 			map.put("流程定义ID:",task.getProcessDefinitionId());
 			
 			list2.add(map);
 		}
 		
 		return list2;
 	}
     
     
     /**查询当前任务*/
     @RequestMapping(value = "/findMyTaskById")
     public @ResponseBody Object findMyPersonalTaskById(@RequestParam String id){
    	
    	 Object object = null; 
    	 
		try {
			object=processEngineFactory.getRuntimeService().getVariables(id);
			// object= processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
			//		.getVariable(id,"users");
			MyTestUtil.print(object);
		} catch (org.activiti.engine.ActivitiObjectNotFoundException e) {
			// TODO: handle exception
			return "ERROR：ID:"+id;
		}
    	 
    	 return object;
    	
 	}
     
     /**完成我的任务*/
     @RequestMapping(value = "/personalTask")
 	public @ResponseBody Map completeMyPersonalTask(@RequestParam String taskId,Integer days){

    	 Map<String, Object> var = new HashMap<String, Object>();
  	    var.put("hhh", "55");
  	    var.put("opop", 5555555);
  	    
  	    Map map = new HashMap<>();
  	    
		try {
			processEngineFactory.getTaskService().setVariables(taskId, var);
			processEngineFactory.getTaskService()// 与正在执行的任务管理相关的Service
					.complete(taskId, var);			
			map.put("完成任务：任务ID:", taskId);
			
		} catch (org.activiti.engine.ActivitiObjectNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			map.put("ERROR：任务ID:", taskId);
		}
 		
 		
 		return map;

     }
    
     /**获取全部历史流程实例*/
     @RequestMapping(value = "/findAllHistory")
     public @ResponseBody Map findAllHistory(@RequestParam Integer limit,@RequestParam Integer offset, HttpServletResponse response){
         
           List<HistoricProcessInstance> historicProcess =  historyService.createHistoricProcessInstanceQuery().orderByProcessInstanceEndTime().desc().listPage(offset, limit);
           
           MyTestUtil.print(historicProcess);
           
           long total=historyService.createHistoricProcessInstanceQuery().count();
           
           Iterator iterator=historicProcess.iterator();
           
           List list=new ArrayList<>();
           
           while (iterator.hasNext()) {
			
        	   HistoricProcessInstance historicProcessInstance=(HistoricProcessInstance) iterator.next();
        	   
        	   Map hMap=new HashMap<>();
        	   
        	   hMap.put("id",historicProcessInstance.getId());
        	   hMap.put("name",historicProcessInstance.getName());
        	   hMap.put("processDefinitionId", historicProcessInstance.getProcessDefinitionId());
        	   hMap.put("processDefinitionKey", historicProcessInstance.getProcessDefinitionKey());
        	   hMap.put("businessKey",historicProcessInstance.getBusinessKey());        	   
        	   hMap.put("description",historicProcessInstance.getDescription());
        	   hMap.put("deploymentId",historicProcessInstance.getDeploymentId());
        	   hMap.put("endTime",historicProcessInstance.getEndTime());
           
        	   list.add(hMap);
           }
           
           Map map=new HashMap<>();
           
           map.put("rows", list);
           map.put("total", total);
           
           return map;
     }
    
     
     
     /**获取流程实例*/
     @RequestMapping(value = "/findProcdef")
     public void findProcdef(@RequestParam String deploymentId , HttpServletResponse response){
    	 response.setHeader("Cache-Control", "no-store");    //禁止浏览器缓存 
         response.setHeader("Pragrma", "no-cache");    //禁止浏览器缓存 
         response.setDateHeader("Expires", 0);    //禁止浏览器缓存 
         response.setCharacterEncoding("UTF-8"); 
         
           //DeploymentEntity deploymentEntity =  (DeploymentEntity) repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
           
           //MyTestUtil.print(deploymentEntity);
           
           BpmnModel bpmnModel = repositoryService.getBpmnModel(deploymentId);

           MyTestUtil.print(bpmnModel);
           
           Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

           ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

           //中文显示的是口口口，设置字体就好了
           InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png","宋体","宋体","宋体",null, 1.0);
             
           // 输出资源内容到相应对象
           byte[] b = new byte[1024];
           int len;
           
           try {
			while ((len = imageStream.read(b, 0, 1024)) != -1) {
			       response.getOutputStream().write(b, 0, len);
			   }
           } catch (IOException e) {
        	   // TODO Auto-generated catch block
        	   e.printStackTrace();
           }
           
     }
     
   /**获取流程实例*/
     @RequestMapping(value = "/findExecution")
     public void findExecution(@RequestParam String executionId , HttpServletResponse response){
    	 response.setHeader("Cache-Control", "no-store");    //禁止浏览器缓存 
         response.setHeader("Pragrma", "no-cache");    //禁止浏览器缓存 
         response.setDateHeader("Expires", 0);    //禁止浏览器缓存 
         response.setCharacterEncoding("UTF-8"); 
         
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
           InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体","宋体",null, 1.0);
             
           // 输出资源内容到相应对象
           byte[] b = new byte[1024];
           int len;
           
           try {
			while ((len = imageStream.read(b, 0, 1024)) != -1) {
			       response.getOutputStream().write(b, 0, len);
			   }
           } catch (IOException e) {
        	   // TODO Auto-generated catch block
        	   e.printStackTrace();
           }
           
     }
     
     
     /**
      * 读取带跟踪的图片
      */
     @RequestMapping(value = "/process/trace/auto")
     public void readResource(@RequestParam String executionId, HttpServletResponse response)
             throws Exception {
         response.setHeader("Cache-Control", "no-store");    //禁止浏览器缓存 
         response.setHeader("Pragrma", "no-cache");    //禁止浏览器缓存 
         response.setDateHeader("Expires", 0);    //禁止浏览器缓存 
         response.setCharacterEncoding("UTF-8"); 
         
         ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
         BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
         List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);

         Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

         ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
//         InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
         InputStream imageStream = new DefaultProcessDiagramGenerator().generateDiagram(
                 bpmnModel, "png",
                 activeActivityIds, Collections.<String>emptyList(),  1.0);
         // 输出资源内容到相应对象
         byte[] b = new byte[1024];
         int len;
         while ((len = imageStream.read(b, 0, 1024)) != -1) {
             response.getOutputStream().write(b, 0, len);
         }
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

