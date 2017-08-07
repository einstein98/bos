package com.heima.web.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author 作者 Eins98
 * @version 创建时间：2017年8月7日 下午2:20:24
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	private static final long serialVersionUID = -2085264128153327912L;
	private T t;

	@Override
	public T getModel() {
		return t;
	}

	@SuppressWarnings("unchecked")
	public BaseAction() {
		Type genericClassType = this.getClass().getGenericSuperclass();
		Type genericType = ((ParameterizedType) genericClassType).getActualTypeArguments()[0];
		try {
			this.t = ((Class<T>) genericType).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void push(Object obj) {
		ActionContext.getContext().getValueStack().push(obj);
	}

	public void put(String key, Object value) {
		ActionContext.getContext().put(key, value);
	}

	public void setSessionAttr(String key, Object value) {
		ActionContext.getContext().getSession().put(key, value);
	}

	public Object getSessionAttr(String key) {
		return ActionContext.getContext().getSession().get(key);
	}

	public void removeSessionAttr(String key) {
		ActionContext.getContext().getSession().remove(key);
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	private int page;
	private int rows;
	private Page pageData;

	public PageRequest getPageRequest(Sort sort) {
		return new PageRequest(page, rows, sort);
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setPageData(Page pageData) {
		this.pageData = pageData;
	}

	public Map<String, Object> getPageMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		return map;
	}

	public void returnJson(Object obj) throws IOException {
		String jsonString = JSON.toJSONString(obj);
		getResponse().getWriter().write(jsonString);
	}

}
