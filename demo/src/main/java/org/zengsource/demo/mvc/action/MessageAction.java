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

import java.util.List;

import org.zengsource.demo.mvc.model.Message;
import org.zengsource.demo.mvc.model.User;
import org.zengsource.demo.mvc.service.MessageService;
import org.zengsource.demo.mvc.service.UserService;
import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.action.MultipleAction;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.HtmlView;
import org.zengsource.mvc.view.XmlErrorView;
import org.zengsource.mvc.view.XmlView;

/**
 * Blog Action.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class MessageAction extends MultipleAction {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private UserService userService;
	private MessageService messageService;

	/** Bean field mapping to request data. */
	private String message;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public MessageAction() {
		// TODO Auto-generated constructor stub
	}

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	/** List blogs in JSON formal. */
	public AbstractView doListJson() throws MvcException {
		String uid = (String) getSession(true).getAttribute("_USER_");
		User user = this.userService.getById(uid);
		List<Message> messages = this.messageService.find(user, getStartInt(),
				getLimitInt());
		StringBuilder sb = new StringBuilder();
		sb.append("{'totalCount':'65429','messages':[");
		for (Message msg : messages) {
			sb.append(this.messageService.convert2Json(msg));
			sb.append(",");
			// sb.append("{'id':'123','avatar':'http://tp3.sinaimg.cn/1863668134/50/1297075499/1','content':'blahblahblahblahblahblahblahblahblahblah','forwards':'100','comments':'100','poster':'demo','lastpost':'1303489434'},");
			// sb.append("{'id':'124','avatar':'http://tp3.sinaimg.cn/1863668134/50/1297075499/1','content':'blahblahblahblahblahblahblahblahblahblah','forwards':'100','comments':'100','poster':'demo','lastpost':'1303489434'}");
		}
		if (sb.lastIndexOf(",") == sb.length() - 1) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]}");
		return new HtmlView(sb.toString());
	}

	/** Post new message. */
	public AbstractView doPost() throws MvcException {
		String uid = (String) getSession(true).getAttribute("_USER_");
		User user = this.userService.getById(uid);
		Message msg = new Message();
		msg.setAuthor(user);
		msg.setContent(getMessage().replaceAll("\n", "\\\\n") + "");
		Integer result = this.messageService.post(msg);
		if (MessageService.POST.equals(result)) {
			return XmlView.SUCCESS;
		} else if (MessageService.FULL.equals(result)) {
			return new XmlErrorView("message", "Reach max messages limit " + MessageService.MAX);
		} else {
			return new XmlErrorView("message", "Unknow error!");
		}
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

}
