/**
 * 
 */
package org.zengsource.mvc.spring;

import javax.servlet.ServletContext;

import org.zengsource.util.StringUtil;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author zeng.xiaoning
 * 
 */
public class WebAppContextHelper {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private ServletContext servletContext;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public WebAppContextHelper(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ApplicationContext getApplicationContext() {
		return getApplicationContext(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}

	public ApplicationContext getApplicationContext(String attrName) {
		if (StringUtil.isBlank(attrName)) {
			attrName = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
		}
		ApplicationContext appContext = WebApplicationContextUtils
				.getWebApplicationContext(this.servletContext, attrName);
		// TODO logging ...
		return appContext;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
