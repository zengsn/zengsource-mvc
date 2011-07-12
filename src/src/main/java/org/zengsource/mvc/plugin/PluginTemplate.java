/**
 * 
 */
package org.zengsource.mvc.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zengsource.mvc.MvcContext;


/**
 * @author snzeng
 * 
 */
public abstract class PluginTemplate implements Plugable {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	protected Logger logger = Logger.getLogger(getClass());

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public PluginTemplate() {
	}
	
	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	protected MvcContext getContext() {
		return MvcContext.getInstance();
	}
	
	protected HttpServletRequest getRequest() {
		return getContext().getRequest();
	}
	
	protected HttpServletResponse getResponse() {
		return getContext().getResponse();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
