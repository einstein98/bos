package com.heima.web.result;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午8:11:54
 */
public class FastJsonResult implements Result {

	private static final long serialVersionUID = -1132436264022678935L;
	private String root;
	/**
	 * 设置时期格式： 如：yyyy-MM-dd HH:mm:ss.SSS 默认：yyyy-MM-dd HH:mm:ss
	 */
	private String dateFormat;
	private final String DEFAULT_DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	private Set<String> includeProperties;
	private Set<String> excludeProperties;

	public Set<String> getExcludePropertiesList() {
		return this.excludeProperties;
	}

	public void setExcludeProperties(String properties) {
		this.excludeProperties = TextParseUtil.commaDelimitedStringToSet(properties);
	}

	public void setIncludeProperties(String includeMethods) {
		this.includeProperties = TextParseUtil.commaDelimitedStringToSet(includeMethods);
	}

	public Set<String> getIncludeProperties() {
		return includeProperties;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) actionContext
				.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		response.setContentType("text/json;charset=utf-8");
		Object rootObject = this.findRootObject(invocation);
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		if (rootObject != null) {
			if (includeProperties != null && includeProperties.size() > 0) {
				filter.getIncludes().addAll(includeProperties);
			}
			if (excludeProperties != null && excludeProperties.size() > 0) {
				filter.getExcludes().addAll(excludeProperties);
			}

			if (StringUtils.isBlank(dateFormat)) {
				dateFormat = DEFAULT_DATA_FORMAT;
			}
			JSON.DEFFAULT_DATE_FORMAT = dateFormat;
			String jsonString = JSON.toJSONString(rootObject, filter, SerializerFeature.WriteDateUseDateFormat);
			response.getWriter().write(jsonString);
		}
	}

	protected Object findRootObject(ActionInvocation invocation) {
		Object rootObject;
		if (this.root != null) {
			ValueStack stack = invocation.getStack();
			rootObject = stack.findValue(this.root);
		} else {
			rootObject = invocation.getStack().peek();
		}
		return rootObject;
	}
}
