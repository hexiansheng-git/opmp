package com.hhwy.flowable.service.fanwei;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hhwy.common.core.utils.DateUtils;
import com.hhwy.common.security.util.SecurityUtils;
import com.hhwy.flowable.core.domain.ActBusiness;
import com.hhwy.flowable.core.domain.ActFormRoute;
import com.hhwy.flowable.core.mapper.ActBusinessMapper;
import com.hhwy.flowable.core.mapper.ActFormRouteMapper;
import com.hhwy.flowable.core.mapper.BpmnMapper;
import com.hhwy.flowable.core.processor.TaskProcessor;
import org.apache.commons.collections4.CollectionUtils;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import javax.websocket.RemoteEndpoint;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送待办
 * 海外综合门户统一待办中心
 */
@Component
@RefreshScope
public class PushTaskImpl  implements TaskProcessor {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ActFormRouteMapper actFormRouteMapper;
    @Autowired
    private ActBusinessMapper actBusinessMapper;

    @Autowired
    private BpmnMapper bpmnMapper;
/*    #创建待办
    createUrl: http://10.11.238.56/rest/ofs/ReceiveTodoRequestByJson
    #创建已办
    doneUrl: http://10.11.238.56/rest/ofs/ProcessDoneRequestByJson
    #办结
    overUrl: http://10.11.238.56/rest/ofs/ProcessOverRequestByJson
    #删除流程
    deleteUrl: http://10.11.238.56/rest/ofs/deleteRequestInfoByJson
*/
    //向总部推送角色接口地址
    @Value("${pushTask.createUrl}")
    private String createUrl;

    @Value("${pushTask.doneUrl}")
    private String doneUrl;

    @Value("${pushTask.overUrl}")
    private String overUrl;

    @Value("${pushTask.deleteUrl}")
    private String deleteUrl;

    @Value("${pushTask.pmUrl}")
    private String pmUrl;

    @Value("${pushTask.apikey}")
    private String apikey;

    private static final Logger log = LoggerFactory.getLogger(PushTaskImpl.class);


//    @Override
//    public void create(String taskId) {
//        //获取流程实例信息
//        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).includeProcessVariables().singleResult();
//        String id = task.getId(); //任务id
//        String taskTame = task.getName(); //任务名称（节点名称）
//        String receiver = task.getAssignee(); //任务接收人
//        Date createTime = task.getCreateTime(); //任务创建时间
//        String tenantKey = task.getTenantId();  //租户key
//        String processInstanceId = task.getProcessInstanceId(); //流程实例id
//        String processDefinitionId = task.getProcessDefinitionId(); //流程定义id
//        String taskDefinitionKey = task.getTaskDefinitionKey(); //任务对应的节点key
//
//        Map<String, Object> processVariables = task.getProcessVariables();
//        String routerId = processVariables.get("routerId").toString();
//        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
//        String processName = processInstance.getName(); //流程实例名称
//
//        ActFormRoute actFormRoute = actFormRouteMapper.selectActFormRouteByProcessDefinitionIdAndActivityIdAndRouterId(processDefinitionId, taskDefinitionKey, routerId, tenantKey);
//        if (actFormRoute != null) {
//            String pageRoute = actFormRoute.getPageRoute(); //任务审批界面地址
//        }
//
//        List<String> parentTaskIdList = bpmnMapper.selectParentTaskIdsByTaskId(taskId);
//        String sender;
//        Date sendTime;
//        if (CollectionUtils.isEmpty(parentTaskIdList)) {//第一个节点会为空
//            sender = processInstance.getStartUserId(); //创建人
//            sendTime = processInstance.getStartTime();
//        }else {
//            HistoricTaskInstance parentTask = historyService.createHistoricTaskInstanceQuery().taskIds(parentTaskIdList).orderByHistoricTaskInstanceEndTime().desc().list().get(0);
//            sender = parentTask.getAssignee();
//            sendTime = parentTask.getEndTime();
//        }
        //    泛微测试地址
        //    http://10.11.238.56/login/OALogin.jsp
        //    这是无身份认证的登录地址，账号：2018021028，密码：1
//    }

    /**
     *         创建流程发送的参数
     *         "syscode": "PM", //项管平台表示
     *         "flowid": "11919191283123",  //流程id
     *         "requestname": "[大管理测试]流程",  //标题
     *         "workflowname": "前期策划", //流程类型
     *         "nodename": "部门领导审批",   //审批节点
     *         "pcurl": "https://pmxm.cfhec.net/login",  //pc端url
     *         "appurl": "",
     *         "creator": "2018021028",  //创建人  4a编码
     *         "createdatetime": "2023-12-19 15:00:00",  //创建时间
     *         "receiver": "2018021028", //接收人
     *         "receivedatetime": "2023-12-19 15:00:00"  //接收时间
     * @param taskId
     */
    @Override
    public void create(String taskId) {
        //创建待办
        Map<String, Object> param = this.getCreateInfo(taskId);
        log.info("一公局门户!!!!!!!!!!!!!!!!创建待办:"+taskId+":"+JSON.toJSONString(param));
        if(param.get("pcurl")!=null){
            //一公局门户
            String res= HttpRequest.post(createUrl)
                    .header("apikey",apikey)
                    .body(JSON.toJSONString(param)).execute().body();
            log.info("一公局门户****************返回数据:"+taskId+":"+res);
            //中交门户


        }else{
            log.info("##########未获取到审批路径不进行待办推送,检查流程路由配置");
        }
    }

    @Override
    public void complete(String taskId) {
        //创建已办
        Map<String, Object> paramDB = this.getCreateInfo(taskId);
        log.info("一公局门户!!!!!!!!!!!!!!!!!待办变已办:"+taskId+":"+JSON.toJSONString(paramDB));
        if(paramDB.get("pcurl")!=null){
            //一公局门户
            String resDB= HttpRequest.post(doneUrl)
                    .header("apikey",apikey)
                    .body(JSON.toJSONString(paramDB)).execute().body();
            log.info("一公局门户*****************返回数据:"+taskId+":"+resDB);
            //中交门户


        }else{
            log.info("##########未获取到审批路径不进行待办推送,检查流程路由配置");
        }

    }

    @Override
    public void remove(String taskId) {

    }

    @Override
    public void deleteProcessInstance(String taskId) {

    }



    public  Map<String,Object> getCreateInfo(String taskId){
        //获取流程实例信息
        HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(taskId).includeProcessVariables().singleResult();
        return  this.getTaskInfo(task);
    }




    Map<String,Object> getTaskInfo(HistoricTaskInstance task){
        Map<String, Object> map = new HashMap<>();

        String id = task.getId(); //任务id
        String taskTame = task.getName(); //任务名称（节点名称）
        String receiver = task.getAssignee(); //任务接收人
        Date createTime = task.getCreateTime(); //任务创建时间
        String processInstanceId = task.getProcessInstanceId(); //流程实例id
        String processDefinitionId = task.getProcessDefinitionId(); //流程定义id
        String taskDefinitionKey = task.getTaskDefinitionKey(); //任务对应的节点key
        String tenantKey = task.getTenantId();  //租户key

        //流程实例名称
        Map<String, Object> processVariables = task.getProcessVariables();
        String routerId = processVariables.get("routerId").toString();
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processName = processInstance.getName();

        //任务审批界面地址
        ActFormRoute actFormRoute = actFormRouteMapper.selectActFormRouteByProcessDefinitionIdAndActivityIdAndRouterId(processDefinitionId, taskDefinitionKey, routerId, tenantKey);
        String pageRoute=null;
        if (actFormRoute != null) {
            pageRoute = actFormRoute.getPageRoute();
        }
        //业务id
        ActBusiness business = new ActBusiness();
        business.setProcessInstanceId(processInstanceId);
        ActBusiness actBusiness = actBusinessMapper.getActBusiness(business);
        String businessId = actBusiness.getBusinessId();

        map.put("syscode","PM");
        map.put("flowid",processInstanceId);
        map.put("requestname","[海外项管]"+processName);

        if(taskDefinitionKey=="userTask_96903e32d5e04d6dac21461c4e08b107"){
            String tenantName = SecurityUtils.getSysUser().getTenant().getTenantName();
            map.put("requestname","[海外项管]"+"项目WBS管理："+tenantName+"项目编号"+tenantKey+"没有绑定p6，请领导绑定");
        }

        map.put("workflowname","工作流程");
        map.put("nodename",taskTame);
        map.put("nodeId",taskDefinitionKey);
        map.put("dateobj",createTime);
        map.put("pcurl",pmUrl+pageRoute+"?id="+businessId+"&tenantKey="+tenantKey+"&receiver="+receiver+"&pageType=fw");
        map.put("appurl","");
        map.put("creator", SecurityUtils.getUserName());
        map.put("createdatetime", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,createTime));
        map.put("receiver",receiver);
        map.put("receivedatetime",DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,new Date()));
        return map;
    }


//    public static void main(String[] args) {
//        Map<Object, Object> map = new HashMap<>();
//        Commission commission = new Commission();
//
//        commission.setCreatedTime(1699343818705L);			//只是待办、待阅2中状态的创建时间业务系统创建待办的时间，更新办理状态时，此时间不变。
//        commission.setCorpName("中交一公局");			//单位名称
//
//        commission.setContentType("1");			//消息类型区分 消息类型区分。0：待阅；1：待办；2：预警；3：日程；4：订单；5：消息。6：报销
//        commission.setDoType("2");				//0：待阅  1：待办；2：已办；3：已阅  4：挂起  5：签收  6：恢复  7：废弃/删除  8：订单推送  9：结束  10:报销推送
//        commission.setDataId(0);				//传dataID 或 dataNo		二选一***
//        commission.setFlowId("fca1a133-6e64-11ee-9020-baa7a8ca0ede");			//流程ID					***
//        commission.setFlowInstid("fca1a133-6e64-11ee-9020-baa7a8ca0ede");		//流程实例ID				***
//        commission.setTitle("【海外项管】前期策划工作计划审批流程");		//待办标题				***
//        commission.setSysType("FHEB_HWXG");			// ####### 此处请填写自助接入平台返回的系统类别字段 #######
//        commission.setVerifykey("2f86be09796e44d58c1cc340eaca4bf1");	//####### 此处请填写自助接入平台返回的接口调用授权码类别字段，本字段由门户自助接入平台自动生成，测试系统与正式系统码不通用 #######
//        commission.setDataNo("3a2814e7-7d43-11ee-88e3-1ae5d8f6f871");		//与dataId二选一
//
//        commission.setDoUserId("L20101371");	//当前待办处理人
//        commission.setFlowName("前期策划工作计划审批流程");		//流程名称
//        commission.setNodeID("userTask_d89445484f5f4483b1bdb1c452b7fcec");		//节点ID
//        commission.setNodeName("海外事业部施工管理处经办人");			//focusFlag点名称
//        commission.setSysModel("前期策划工作计划审批流程");			//业务系统特定模块
//        commission.setUpdateTime(1699343818705L);			//更新时间
//        commission.setOtherActUrl("?method=view&fdId=16e43b64c499806703");	//待办打开的链接拼接地址参数，与PcActUrl传一致即可
//        commission.setPcActUrl("?method=view&fdId=16e43b64c499806703");		//待办打开的链接拼接地址参数，用户在门户上打开待办时，系统会自动拼接上 系统单点地址+本参数
//        commission.setUrgentLevel("0");			//紧急程度	正常：0   紧急：1            重要：2  	加急：3
//
//        Date data = new Date();
//        Long writeTime = data.getTime();
//        commission.setWriteTime(writeTime);		//写入时间
//
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(commission);
//
//        //数据最外层 key
//        JSONObject jsonObj_msg = new JSONObject();
//        jsonObj_msg.put("todos", jsonArray);
//
//
//
//
//        String url = "http://211.159.153.219:8081/smsServerFlow/sms/todo/service/add/todo";
//        String resDB= HttpRequest.post(url)
//                .header("Content-type","application/json; charset=utf-8")
//                .header("Accept", "application/json")
//                .body(JSON.toJSONString(jsonObj_msg)).execute().body();
//
//        System.out.println(resDB);
//
//
//    }



}
