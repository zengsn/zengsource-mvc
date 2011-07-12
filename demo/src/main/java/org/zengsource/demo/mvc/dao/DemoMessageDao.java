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
package org.zengsource.demo.mvc.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.zengsource.demo.mvc.model.Message;
import org.zengsource.demo.mvc.model.User;

/**
 * Use this class for code convention recommandation.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class DemoMessageDao implements MessageDao {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static LinkedList<Message> DEMO = new LinkedList<Message>();

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private UserDao userDao;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public DemoMessageDao() {
	}
	
	private void initDemo() {
		Message msg = new Message();
		msg.setAuthor(this.userDao.queryById("demo"));
		msg.setContent("This is a demo message!");
		DEMO.add(msg);
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public List<Message> query(User user, Boolean own, Integer start,
			Integer limit) {
		if (DEMO.size() ==0) {
			initDemo();
		}
		LinkedList<Message> messages = new LinkedList<Message>();
		if (user == null) {
			return null;
		}
		List<User> users = new ArrayList<User>();
		users.add(user); // include self
		if (!own) { // get follows also
			users.addAll(user.getFollows());
		}
		for (Message msg : DEMO) {
			boolean matched = false;
			for (User u : users) {
				if (u.getId().equals(msg.getAuthor().getId())) {
					matched = true;
					break;
				}
			}
			if (matched) {
				if (start != null && start > 0) {
					start--;
					continue;
				}
				if (limit != null && limit > 0) {
					messages.add(msg);
					limit--;
				}
			}
		}
		return messages;
	}
	
	public boolean save(Message msg) {
		DEMO.addFirst(msg);
		return true;
	}
	
	public Integer queryTotal() {
		return DEMO.size();
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
