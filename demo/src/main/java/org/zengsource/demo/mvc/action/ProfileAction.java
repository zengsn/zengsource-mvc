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

import java.io.File;

import org.apache.commons.fileupload.FileItem;
import org.zengsource.demo.mvc.model.User;
import org.zengsource.demo.mvc.service.UserService;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipartAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.XmlView;

/**
 * Use this class for code convention recommandation.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class ProfileAction extends MultipartAction {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String email;

	private UserService userService;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public ProfileAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MvcException {
		String rootPath = getContext().getRootPath();
		logger.info("save file to tmpdir: " + rootPath);
		String uid = getSession(true).getAttribute("_USER_").toString();
		User user = this.userService.getById(uid);
		if (user != null) {
			File userDir = new File(rootPath + "/user/" + uid);
			if (!userDir.exists() || userDir.isFile()) {
				userDir.mkdirs();
			}

			FileItem avatarItem = getFileItem("avatar");
			if (avatarItem != null) {
				File diskFile = saveFile("avatar", userDir + "/" + avatarItem.getName());
				if (diskFile != null) {
					user.setAvatar("user/" + uid + "/" + avatarItem.getName());
				}
			}
		}
		return XmlView.SUCCESS;
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
