/**
 * 
 */
package org.zengsource.mvc.action;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.zengsource.mvc.MvcContext;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.view.AbstractView;


/**
 * @author zeng.xiaoning
 * 
 */
public abstract class AbstractAction implements Serializable {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	protected Logger logger = Logger.getLogger(getClass());

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractAction() {
		logger.info("New :: " + getClass().getName());
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	protected void doInit() throws MvcException {
		if (getRequest() != null) {
			try {
				getRequest().setCharacterEncoding("utf-8");
				getResponse().setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				throw new MvcException(e);
			}
			Enumeration<?> paramNames = getRequest().getParameterNames();
			while (paramNames.hasMoreElements()) {
				String name = (String) paramNames.nextElement();
				String value = getRequest().getParameter(name);
				logger.info("Normal save: " + value);
				// try {
				// value = new String(value.getBytes("ISO-8859-1"));
				// logger.info("8859_1 save: " + value);
				// value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
				// logger.info("UTF-8 保存: " + value);
				// value = new String(value.getBytes("8859_1"), "GBK");
				// logger.info("GBK save: " + value);
				// } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				setFieldValue(name, value);
			}
		}
	}

	protected void setFieldValue(String name, String value) throws MvcException {
		logger.info("Save parameter: [" + name + "] = [" + value + "]");
		try {
			BeanUtils.setProperty(this, name, value);
		} catch (IllegalAccessException e) {
			throw new MvcException(e);
		} catch (InvocationTargetException e) {
			throw new MvcException(e);
		}
	}

	protected abstract AbstractView doService() throws MvcException;

	public AbstractView doRun() throws MvcException {

		doInit();

		AbstractView view = doService();

		doDestroy();

		return view;
	}

	protected void doDestroy() throws MvcException {

	}

	protected MvcContext getContext() {
		return MvcContext.getInstance();
	}

	protected HttpServletRequest getRequest() {
		return getContext().getRequest();
	}

	protected HttpServletResponse getResponse() {
		return getContext().getResponse();
	}
	
	protected HttpSession getSession(boolean create) {
		return getRequest().getSession(create);
	}

	public void setAttribute(String name, Object data) {
		getContext().setAttribute(name, data);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public abstract String getActionName();

}
