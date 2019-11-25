package com.cqut.czb.bn.api;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(scanBasePackages = {"com.cqut.czb.bn","com.cqut.czb.auth"},exclude = {
})

public class StackApplication {

    public static void main(String[] args) {
        SpringApplication.run(StackApplication.class, args);
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("10240KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("15360KB");
        return factory.createMultipartConfig();
    }

    /**
     * it's for set http url auto change to https
     */
//    @Bean
//    public EmbeddedServletContainerFactory servletContainer(){
//        TomcatEmbeddedServletContainerFactory tomcat=new TomcatEmbeddedServletContainerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                //不需要强制使用https
////                SecurityConstraint securityConstraint=new SecurityConstraint();
////                securityConstraint.setUserConstraint("CONFIDENTIAL");//confidential
////                SecurityCollection collection=new SecurityCollection();
////                collection.addPattern("/*");
////                securityConstraint.addCollection(collection);
////                context.addConstraint(securityConstraint);
//            }
//        };
////        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
//        return tomcat;
//    }
//
//    // 配置http
//    private Connector createStandardConnector() {
//        // 默认协议为org.apache.coyote.http11.Http11NioProtocol
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setSecure(false);
//        connector.setScheme("http");
//        connector.setPort(port);
//        connector.setRedirectPort(httpsPort); // 当http重定向到https时的https端口号
//        return connector;
//    }
//
//    @Value("${server.http.port}")
//    private Integer port;
//
//    @Value("${server.port}")
//    private Integer httpsPort;


//    @Bean
//    public Connector httpConnector(){
//        Connector connector=new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //Connector监听的http的端口号
//        connector.setPort(8899);
//        connector.setSecure(true);
//        //监听到http的端口号后转向到的https的端口号
//        connector.setRedirectPort(888);
//        return connector;
//    }
}
