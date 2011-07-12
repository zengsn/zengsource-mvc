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

import java.util.List;

import org.zengsource.demo.mvc.dao.MessageDao;
import org.zengsource.demo.mvc.model.Message;
import org.zengsource.demo.mvc.model.User;

/**
 * Use this class for code convention recommandation.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class MessageServiceImpl implements MessageService {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private MessageDao messageDao;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public MessageServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public List<Message> find(User user, Integer start, Integer limit) {
		return this.messageDao.query(user, false, start, limit);
	}

	public Integer post(Message msg) {
		if (msg == null) {
			return FAILED;
		}
		if (this.messageDao.queryTotal() >= MAX) {
			return FULL;
		}
		return this.messageDao.save(msg) ? POST : FAILED;
	}

	public Object convert2Json(Message msg) {
		return "{'id':'" + msg.getId() + "','avatar':'"
				+ msg.getAuthor().getAvatar() + "','content':'"
				+ msg.getContent() + "','forwards':'" + msg.getForwards()
				+ "','comments':'" + msg.getComments() + "','poster':'"
				+ msg.getAuthor().getUsername() + "','lastpost':'"
				+ msg.getLastPost().getTime() + "'}";
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
}
