/**
 * 
 */
package org.zengsource.mvc;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zengsource.mvc.action.AbstractAction;
import org.zengsource.mvc.action.GenericAction;
import org.zengsource.mvc.action.editor.ActionEditor;
import org.zengsource.mvc.plugin.PluginFactory;
import org.zengsource.mvc.spring.WebAppContextHelper;
import org.zengsource.util.StringUtil;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * @author zeng.xiaoning
 * 
 */
public class MvcContext implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String GET = "get";
	public static final String POST = "post";

	private static ThreadLocal<MvcContext> instance;

	public static MvcContext getInstance() {
		if (instance == null) {
			instance = new ThreadLocal<MvcContext>();
		}
		if (instance.get() == null) {
			instance.set(new MvcContext());
		}
		return instance.get();
	}

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String method;
	private ServletContext servletContext;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ApplicationContext applicationContext;
	private String actionEditorName;
	private Map<String, String> urlSuffixMap;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private MvcContext() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void init(WebAppContextHelper springContextHelper,
			String actionEditorName) {
		if (this.applicationContext == null) {
			this.applicationContext = springContextHelper
					.getApplicationContext();
		}
		if (this.actionEditorName == null) {
			this.actionEditorName = actionEditorName;
		}
	}

	public boolean isPost() {
		return POST.equals(this.method);
	}

	public boolean isGet() {
		return GET.equals(this.method);
	}

	public void setAttribute(String name, Object data) {
		if (this.request != null) {
			this.request.setAttribute(name, data);
		}
	}

	/** No '/' in the end. */
	public String getFullContextPath() {
		String context = this.request.getContextPath();
		String reqUrl = this.request.getRequestURL().toString();
		if (StringUtil.isBlank(context)) { // ROOT.war
			String tmp = reqUrl.replace("http://", "").replace("https://", "");
			int contextStart = tmp.indexOf("/");
			if (contextStart > -1) {
				return tmp.substring(contextStart);
			} else {
				return reqUrl.substring(0, reqUrl.length() - tmp.length()
						+ contextStart);
			}
		} else { // Not ROOT.war
			return reqUrl.substring(0,
					reqUrl.indexOf(context) + context.length());
		}
	}

	public String getActionHierachy() {
		String context = this.request.getContextPath();
		String reqUrl = this.request.getRequestURL().toString();
		if (StringUtil.isBlank(context)) { // ROOT.war
			String tmp = reqUrl.replace("http://", "").replace("https://", "");
			int contextStart = tmp.indexOf("/");
			if (contextStart > -1) {
				return tmp.substring(contextStart + 1);
			} else {
				throw new RuntimeException("Cannot parse action hierachy from "
						+ reqUrl);
			}
		} else { // Not ROOT.war
			return reqUrl.substring(reqUrl.indexOf(context) + context.length()
					+ 1);
		}
	}

	public AbstractAction getAction() {
		String actionHierachy = getActionHierachy();
		String actionName = getActionEditor().getActionName(actionHierachy);
		try {
			return (AbstractAction) this.applicationContext.getBean(actionName);
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	public ActionEditor getActionEditor() {
		return (ActionEditor) this.applicationContext.getBean( //
				this.actionEditorName + "-action-editor");
	}

	public AbstractAction getDefaultAction() {
		GenericAction defaultAction = new GenericAction();
		defaultAction.setName("DEFAULT_ACTION");
		return defaultAction;
	}

	public String getDefaultDispatchPage() {
		String actionHierachy = getActionHierachy();
		int lastDot = actionHierachy.lastIndexOf(".");
		return "/" + actionHierachy.substring(0, lastDot) + ".jsp";
	}

	public String getActionSuffix() {
		String reqUrl = this.request.getRequestURL().toString();
		int lastDot = reqUrl.lastIndexOf(".");
		return lastDot == -1 ? "" : reqUrl.substring(lastDot);
	}

	public PluginFactory getPluginFactory() {
		try {
			return (PluginFactory) getApplicationContext().getBean(
					"pluginFactory");
		} catch (NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	public void addURLSuffix(String name, String suffix) {
		if (this.urlSuffixMap == null) {
			this.urlSuffixMap = new HashMap<String, String>();
		}
		this.urlSuffixMap.put(name, suffix);
	}

	public String getURLSuffix(String name) {
		if (this.urlSuffixMap == null) {
			return null;
		}
		return this.urlSuffixMap.get(name);
	}
	
	public String getRootPath() {
		return getServletContext().getRealPath("/");
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public String getActionEditorName() {
		return actionEditorName;
	}

	public void setActionEditorName(String actionEditorName) {
		this.actionEditorName = actionEditorName;
	}

	public Map<String, String> getUrlSuffixMap() {
		return urlSuffixMap;
	}

	public void setUrlSuffixMap(Map<String, String> urlSuffixMap) {
		this.urlSuffixMap = urlSuffixMap;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
