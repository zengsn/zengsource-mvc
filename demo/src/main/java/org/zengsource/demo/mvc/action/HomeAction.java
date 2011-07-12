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

package org.zengsource.demo.mvc.action;

import org.zengsource.demo.mvc.model.User;
import org.zengsource.demo.mvc.service.UserService;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.GenericAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.util.StringUtil;

/**
 * Handling more than one actions in a single action bean.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class HomeAction extends GenericAction {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String user;

	private UserService userService;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HomeAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	/**
	 * If no action defined, we can still use this method the handle request,
	 * such as user.jxp?id=demo.
	 */
	protected AbstractView doService() throws MvcException {
		String uid = getId();
		if (StringUtil.isBlank(uid)) {
			uid = (String) getRequest().getSession(true).getAttribute("_USER_");
		}
		User user = this.userService.getById(uid);
		getRequest().setAttribute("user", user);
		return super.doService();
	}

	/** Handling "view" action, such as index.jxp?id=demo&action=view. */
	public AbstractView doView() throws MvcException {
		return super.doService();
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
