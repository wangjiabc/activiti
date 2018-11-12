package org.activiti.manage.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;


/**
 * 鐎圭偠顢戠�佃甯撮崣锝堢箻鐞涘瞼顒烽崥锟�
 * 
 * @author xiaowei
 * @copyght 閸娾�茬埃缁夋垶濡ч張澶愭閸忣剙寰�
 */
public class SignFilter implements Filter {

//	Logger logger = Logger.getLogger(SignFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		@SuppressWarnings("unchecked")
/*		HashMap<String, String[]> map = (HashMap<String, String[]>) request
				.getParameterMap();*/
//		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> resultMap=getParameterMap(request);
		JSON.toJSONString(resultMap);


	    chain.doFilter(request, response);
	}

	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map getParameterMap(ServletRequest request) {
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map returnMap = new HashMap();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    return returnMap;
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
