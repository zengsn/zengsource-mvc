/*
 * Copyright 2008-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.zengsource.mvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.zengsource.mvc.action.AbstractAction;
import org.zengsource.mvc.plugin.PluginException;
import org.zengsource.mvc.plugin.PluginFactory;
import org.zengsource.mvc.spring.WebAppContextHelper;
import org.zengsource.mvc.view.AbstractView;

/**
 * Front controller of ZengSource MVC Framework.
 * 
 * @author Zeng Shaoning (http://zsn.cc
 * @version 1.0
 * @since 6.0
 */
public class InletServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	static final long serialVersionUID = 1L;
	static final String ACTION_EDITOR = "actionEditor";
	public static final String PARAM_URL_PAGE = "pageURL";
	public static final String PARAM_URL_XML = "xmlURL";
	public static final String PARAM_URL_JSON = "jsonURL";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	Logger logger = Logger.getLogger(getClass());

	WebAppContextHelper springContextHelper;
	String actionEditorName;
	String pageURLSuffix;
	String xmlURLSuffix;
	String jsonURLSuffix;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public InletServlet() {
		super();
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void init() throws ServletException {
		super.init();

		logger.info("Init Spring ApplicationContext ... ");
		this.springContextHelper = new WebAppContextHelper(getServletContext());
		this.actionEditorName = getMvcConfig(ACTION_EDITOR, "default");
		this.pageURLSuffix = getMvcConfig(PARAM_URL_PAGE, "jxp");
		this.xmlURLSuffix = getMvcConfig(PARAM_URL_XML, "xmd");
		this.jsonURLSuffix = getMvcConfig(PARAM_URL_JSON, "jsd");
//		Enumeration<?> enumer = getServletContext().getAttributeNames();
//		while (enumer.hasMoreElements()) {
//			String attrName = enumer.nextElement().toString();
//			logger.info(attrName);
//			logger.info(getServletContext().getAttribute(attrName));
//		}
	}

	/**
	 * Get the user setting for the MVC framework.
	 * 
	 * @param pararm
	 *            Name of the &lt;init-param&gt;.
	 * @param defValue
	 *            Use the default value if not defined.
	 * @return The parameter valu.
	 */
	private String getMvcConfig(String paramName, String defValue) {
		String value = getInitParameter(paramName);
		return value == null || value.length() == 0 ? defValue : value;
	}

	public void destroy() {
		super.destroy();
		this.springContextHelper = null;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(MvcContext.GET, request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAction(MvcContext.POST, request, response);
	}

	private void doAction(String method, HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");

		MvcContext mvcContext = MvcContext.getInstance();
		// Init Context
		mvcContext.init(this.springContextHelper, this.actionEditorName);
		// Save request and data
		mvcContext.setMethod(method);
		mvcContext.setRequest(request);
		mvcContext.setResponse(response);
		mvcContext.setServletContext(getServletContext());
		mvcContext.addURLSuffix(PARAM_URL_PAGE, pageURLSuffix);
		mvcContext.addURLSuffix(PARAM_URL_XML, xmlURLSuffix);
		mvcContext.addURLSuffix(PARAM_URL_JSON, jsonURLSuffix);
		request.setAttribute("_PAGE_URL_SUFFIX_", pageURLSuffix);
		request.setAttribute("_XML_URL_SUFFIX_", xmlURLSuffix);
		request.setAttribute("_JSON_URL_SUFFIX_", jsonURLSuffix);

		logger.info("Init Site Context ... ");
		try {
			PluginFactory pf = mvcContext.getPluginFactory();
			if (pf != null && !pf.enableAll()) {
				AbstractView view = (AbstractView) request.getAttribute("_VIEW_");
				if (view != null) { // Plugin blocks the request
					view.forward();
					return; // stop this request
				}
			}
		} catch (PluginException e) {
			logger.warn("An error found when enable plugins!");
			e.printStackTrace();
			// TODO: redirect to error page
		}

		// Parse action
		AbstractAction action = mvcContext.getAction();
		if (action == null) {
			logger.warn("Null action, use default action!");
			action = mvcContext.getDefaultAction();
		}
		// Check if view already built
		AbstractView view = (AbstractView) request.getAttribute("_VIEW_");
		if (view != null) {
			request.removeAttribute("_VIEW_");
		} else {
			// try {
			view = action.doRun();
			// } catch (RuntimeException e) {
			// logger.error(e.getLocalizedMessage());
			// view = DispatchView.error(e.getClass().getName(),
			// ExceptionUtil.print(e));
			// }
			if (view == null) { // redirected by action
				view = (AbstractView) request.getAttribute("_VIEW_");
			}
		}

		// View
		if (view != null) {
			view.forward();
		} else {
			logger.error("Null View!!!!!!!!!!!!!!!!!!!!");
		}
	}
}