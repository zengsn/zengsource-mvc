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
import org.zengsource.mvc.action.MultipleAction;
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
public class SignOnAction extends MultipleAction {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	private String email;
	private String username;
	private String password1;
	private String password2;
	private String acceptTerms;

	private UserService userService;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public SignOnAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public AbstractView doService() throws MvcException {
		if (StringUtil.notBlank(getUsername())) {
			User user = new User();
			user.setUsername(getUsername());
			user.setPassword(getPassword1());
			user.setEmail(getEmail());
			if (this.userService.create(user) == UserService.CREATED) {
				User demo = this.userService.getById("demo"); // follow the demo user
				if (demo != null) {
					user.follows(demo);
				}
				return XmlView.SUCCESS;
			} else {
				return new XmlErrorView("username", "User already existed!");
			}
		}
		return super.doService();
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getAcceptTerms() {
		return acceptTerms;
	}

	public void setAcceptTerms(String acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
