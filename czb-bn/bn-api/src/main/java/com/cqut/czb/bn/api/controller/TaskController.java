package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.dao.mapper.RoleApiMapperExtra;
import com.cqut.czb.bn.entity.global.JSONResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Api(value = "Sample", description = "范例相关接口",produces = MediaType.ALL_VALUE)
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    RoleApiMapperExtra roleApiMapperExtra;


    @GetMapping
    public JSONResult listTasks(){
        System.out.println("任务列表");
        return new JSONResult("任务列表");
    }


    @PostMapping
    public String newTasks(){
        return "创建了一个新的任务";
    }

    @PutMapping("/{taskId}")
    public String updateTasks(@PathVariable("taskId")Integer id){
        return "更新了一下id为:"+id+"的任务";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTasks(@PathVariable("taskId")Integer id){
        return "删除了id为:"+id+"的任务";
    }

    @RequestMapping(value = "/testRoleApi",method = RequestMethod.GET)
    public JSONResult testRoleApi(){


        return new JSONResult(0);
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public List<RequestToMethodItem> index(HttpServletRequest request)
    {
        ServletContext servletContext = request.getSession().getServletContext();
        if (servletContext == null)
        {
            return null;
        }
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        //请求url和处理方法的映射
        List<RequestToMethodItem> requestToMethodItemList = new ArrayList<RequestToMethodItem>();
        //获取所有的RequestMapping
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
                HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : allRequestMappings.values())
        {
            //本项目只需要RequestMappingHandlerMapping中的URL映射
            if (handlerMapping instanceof RequestMappingHandlerMapping)
            {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet())
                {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();

                    RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
                    String requestType = methodCondition.getMethods().stream().map(vo -> String.valueOf(vo)).collect(Collectors.joining(","));


                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    //  String requestUrl = SetUtils.first(patternsCondition.getPatterns());
                    String requestUrl = patternsCondition.getPatterns().stream().map(vo -> String.valueOf(vo)).collect(Collectors.joining(","));;
                    String controllerName = mappingInfoValue.getBeanType().toString();
                    String requestMethodName = mappingInfoValue.getMethod().getName();
                    Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
                    RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName, requestMethodName,
                            methodParamTypes);

                    requestToMethodItemList.add(item);
                }
            }
        }
        return requestToMethodItemList;
    }

    class RequestToMethodItem {
        String requestUrl;
        String requestType;
        String controllerName;
        String requestMethodName;
        Class<?>[] methodParamTypes;

        public RequestToMethodItem(String requestUrl, String requestType, String controllerName, String requestMethodName, Class<?>[] methodParamTypes) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
            this.controllerName = controllerName;
            this.requestMethodName = requestMethodName;
            this.methodParamTypes = methodParamTypes;
        }

        public String getRequestUrl() {
            return requestUrl;
        }

        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }

        public String getRequestType() {
            return requestType;
        }

        public void setRequestType(String requestType) {
            this.requestType = requestType;
        }

        public String getControllerName() {
            return controllerName;
        }

        public void setControllerName(String controllerName) {
            this.controllerName = controllerName;
        }

        public String getRequestMethodName() {
            return requestMethodName;
        }

        public void setRequestMethodName(String requestMethodName) {
            this.requestMethodName = requestMethodName;
        }

        public Class<?>[] getMethodParamTypes() {
            return methodParamTypes;
        }

        public void setMethodParamTypes(Class<?>[] methodParamTypes) {
            this.methodParamTypes = methodParamTypes;
        }
    }
}

