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

import java.util.List;

import org.zengsource.demo.mvc.model.Message;
import org.zengsource.demo.mvc.model.User;


/**
 * Message DAO Interface.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public interface MessageDao {
	
	public List<Message> query(User user, Boolean own, Integer start, Integer limit);

	public boolean save(Message msg);
	
	public Integer queryTotal();

}
