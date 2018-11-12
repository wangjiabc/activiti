package org.activiti.manage.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.manage.tools.MyTestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/first")
public class FirstController {
	
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    HistoryService historyService;
    @Autowired
    TaskService taskService;
    @Autowired
    ProcessEngineConfiguration processEngineFactory;
    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/test")
    public void test(HttpServletResponse response) throws Exception {
        // 部署流程，只要是符合BPMN2规范的XML文件，理论上都可以被ACTIVITI部署
        repositoryService.createDeployment().addClasspathResource("b.bpmn20.xml").deploy();
        // 开启流程，myprocess是流程的ID
        System.out.println("流程【启动】，环节推动到【一次审批】环节");
        runtimeService.startProcessInstanceByKey("RunMode");
        // 查询历史表中的Task
        List<Task> task = taskService.createTaskQuery().list();
        Task task1 = task.get(task.size()-1);
        //解开注释就推动到下一环节，对应的在流程图上看到
        taskService.complete(task1.getId());
        System.out.println("执行【一次审批】环节，流程推动到【二次审批】环节");
        task1 = taskService.createTaskQuery().executionId(task1.getExecutionId()).singleResult();

        //解开注释就推动到下一环节，对应的在流程图上看到
        taskService.complete(task1.getId());
        System.out.println("执行【二次审批】环节，流程推动到【结束】环节");

        //processInstanceId
        String processInstanceId = task1.getProcessInstanceId();
        //获取历史流程实例
        HistoricProcessInstance processInstance =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图
        System.out.println("processInstanceId="+processInstance.getProcessDefinitionId());
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        MyTestUtil.print(bpmnModel);
        processEngineFactory = processEngineFactory.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl)processEngineFactory);

        ProcessDiagramGenerator diagramGenerator = processEngineFactory.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());

        List<HistoricActivityInstance> highLightedActivitList =  historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);

        for(HistoricActivityInstance tempActivity : highLightedActivitList){
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
       // List<String> activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
       // System.out.println("activeActivityIds="+activeActivityIds);
        //中文显示的是口口口，设置字体就好了
      InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis,highLightedFlows,"宋体","宋体","宋体",null, 1.0);
        
        System.out.println("key="+processInstance.getProcessDefinitionKey());
        
        repositoryService.createDeployment()
        .name("expense‐process.bar")
        .addClasspathResource("FinancialReportProcess.bpmn20.xml")
        .deploy();
        
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
        		.processDefinitionKey(processInstance.getProcessDefinitionKey())
        		.list().get(0);
        
        String diagramResourceName = processDefinition.getDiagramResourceName();
        System.out.println("diagramResourceName="+diagramResourceName);
       // InputStream imageStream = repositoryService.getResourceAsStream(
       //		processInstance.getDeploymentId(), diagramResourceName);
        
      //单独返回流程图，不高亮显示
      //  InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);
        
      
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