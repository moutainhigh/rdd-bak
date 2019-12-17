package com.cqut.czb.bn.entity.dto.PayConfig;

import com.cqut.czb.bn.util.md5.MD5Util;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeChatUtils {

    // 得到32位随机数
    public static String getRandomStr() {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        String str = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        for (int i = 0; i < 32; i++) {
            sb.append(str.charAt(r.nextInt(str.length())));
        }
        return sb.toString();
    }

    // 得到sign
    @SuppressWarnings("unchecked")  
    public static String createSign(String characterEncoding,SortedMap<String,Object> parameters){  
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + WeChatPayConfig.key);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;  
    }

    @SuppressWarnings("unchecked")
    public static String createRddSign(String characterEncoding,SortedMap<String,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WeChatPayConfig.skey);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
    
    // 得到日期
    public static String getDataStr(String type) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    // 转换为xml
    public static String map2xml(SortedMap<String, Object> map) {
        String info = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        info += "<xml>";
        Set<String> keySet = map.keySet();
        for (String string : keySet) {
            info += "<" + string + ">" + map.get(string) + "</" + string + ">";
        }
        info += "</xml>";
        return info;
    }

    //xml转换为map
    public static Map<String, Object> xml2Map(String xmlStr) {
        Map<String, Object> map = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            String FEATURE = null;
            FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
            factory.setFeature(FEATURE, true);
        	
        	// If you can't completely disable DTDs, then at least do the following:
        	// Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-general-entities
        	// Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-general-entities
        	// JDK7+ - http://xml.org/sax/features/external-general-entities 
        	FEATURE = "http://xml.org/sax/features/external-general-entities";
        	factory.setFeature(FEATURE, false);
        	
        	// Xerces 1 - http://xerces.apache.org/xerces-j/features.html#external-parameter-entities
        	// Xerces 2 - http://xerces.apache.org/xerces2-j/features.html#external-parameter-entities
        	// JDK7+ - http://xml.org/sax/features/external-parameter-entities 
        	FEATURE = "http://xml.org/sax/features/external-parameter-entities";
        	factory.setFeature(FEATURE, false);
        	
        	// Disable external DTDs as well
        	FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
        	factory.setFeature(FEATURE, false);
        	
        	// and these as well, per Timothy Morgan's 2014 paper: "XML Schema, DTD, and Entity Attacks"
        	factory.setXIncludeAware(false);
        	factory.setExpandEntityReferences(false);
            // 将字符串转换成流
            ByteArrayInputStream bis = new ByteArrayInputStream(xmlStr
                    .getBytes());
            Document doc = builder.parse(bis);
            Node root = doc.getFirstChild();// 根节点
            NodeList nodeList = root.getChildNodes();
            map = new HashMap<String, Object>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node child = nodeList.item(i);
                if (child instanceof Element) {
                    Element e = (Element) child;
                    map.put(e.getNodeName(), e.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    //分转为元（单位转换）
    public static double centToYuan(double cent){
    	double yuan = 100.0;
    	return BigDecimal.valueOf(cent).divide(new BigDecimal(yuan)).doubleValue();
    }
    
}