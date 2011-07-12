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
import org.zengsource.mvc.view.XmlErrorView;
import org.zengsource.mvc.view.XmlView;
import org.zengsource.util.StringUtil;

/**
 * Use this class for code convention recommandation.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class SignInAction extends GenericAction {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String username;
	private String password;

	private UserService userService;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public SignInAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MvcException {
		logger.info("Somebody sign in ... ");
		if (StringUtil.notBlank(getUsername())) {
			return doSignIn();
		}
		return super.doService();
	}

	private AbstractView doSignIn() throws MvcException {
		if ("demo".equals(getUsername()) //
				&& "demo".equals(getPassword())) {
			User demo = this.userService.getById("demo");
			if (demo == null) {
				demo = new User();
				demo.setId("demo");
				demo.setUsername("demo");
				demo.setPassword("demo");
				this.userService.create(demo);
			}
			getSession(true).setAttribute("_USER_", "demo");
			return XmlView.SUCCESS;
		} else {
			User user = this.userService.getByUsername(getUsername());
			if (user != null && user.getPassword().equals(getPassword())) {
				getSession(true).setAttribute("_USER_", user.getId());
				return XmlView.SUCCESS;
			}
		}
		return new XmlErrorView("username", "User not found!");
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
