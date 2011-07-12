/**
 * 
 */
package org.zengsource.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zengsource.mvc.MvcContext;
import org.zengsource.mvc.MvcException;


/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public abstract class AbstractView {
	
	protected Logger logger = Logger.getLogger(getClass());

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public abstract void forward() throws MvcException;

	protected MvcContext getContext() {
		return MvcContext.getInstance();
	}

	protected HttpServletRequest getRequest() {
		return getContext().getRequest();
	}

	protected HttpServletResponse getResponse() {
		return getContext().getResponse();
	}
}
