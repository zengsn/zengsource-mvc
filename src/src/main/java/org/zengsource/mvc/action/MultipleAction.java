/**
 * 
 */
package org.zengsource.mvc.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class MultipleAction extends GenericAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String action;
	private Map<String, String> actionServiceMap;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public MultipleAction() {
		actionServiceMap = new HashMap<String, String>();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MvcException {
		if (StringUtil.isBlank(this.action)) {
			return super.doService();
		}
		String service = getServiceName();
		String serviceMethodName = "do" + capitalFirst(service);
		try {
			logger.info("doService()-> Run service: " + service);
			Method serviceMethod = this.getClass().getMethod(serviceMethodName);
			return (AbstractView) serviceMethod.invoke(this);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return super.doService();
	}

	private String getServiceName() {
		String action = getAction();
		String service = this.actionServiceMap.get(action);
		if (service == null) {
			return action;
		} else {
			return service;
		}
	}

	private String capitalFirst(String str) {
		if (str.length() == 1) {
			return str.toUpperCase();
		}
		char first = Character.toUpperCase(str.charAt(0));
		return first + str.substring(1);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	/** Override this method if need different param name of action. */
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, String> getActionServiceMap() {
		return actionServiceMap;
	}

	public void setActionServiceMap(Map<String, String> actionServiceMap) {
		this.actionServiceMap = actionServiceMap;
	}

}
