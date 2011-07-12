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
package org.zengsource.demo.mvc.service;

import org.zengsource.demo.mvc.dao.UserDao;
import org.zengsource.demo.mvc.model.User;

/**
 * Use this class for code convention recommandation.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class UserServiceImpl implements UserService {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private UserDao userDao;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public UserServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public User getById(String id) {
		return this.userDao.queryById(id);
	}
	
	public User getByUsername(String username) {
		return this.userDao.queryByUsername(username);
	}
	
	public Integer create(User user) {
		if (this.getById(user.getId()) != null) {
			return EXISTED;
		}
		this.userDao.add(user);
		return CREATED;
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public UserDao getUserDao() {
		return userDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
