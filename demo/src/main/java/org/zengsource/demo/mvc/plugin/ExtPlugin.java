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

package org.zengsource.demo.mvc.plugin;

import java.io.IOException;
import java.util.Properties;

import org.zengsource.mvc.plugin.PluginException;
import org.zengsource.mvc.plugin.PluginTemplate;
import org.zengsource.util.ClassLoaderUtil;

/**
 * Use this plugin to load ExtJS library.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class ExtPlugin extends PluginTemplate {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void disable() throws PluginException {
	}

	public boolean enable() throws PluginException {
		Properties extProperties = new Properties();
		try {
			extProperties.load(ClassLoaderUtil.getResourceAsStream("ext.properties"));
			String extUrl = extProperties.getProperty("ext.url", "");
			getRequest().setAttribute("_EXT_URL_", extUrl);
			logger.info("enable() -> Set ext url to " + extUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return true;
	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
