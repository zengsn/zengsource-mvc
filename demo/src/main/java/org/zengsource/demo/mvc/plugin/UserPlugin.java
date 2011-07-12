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

import org.zengsource.mvc.plugin.PluginException;
import org.zengsource.mvc.plugin.PluginTemplate;
import org.zengsource.mvc.view.RedirectView;
import org.zengsource.util.StringUtil;

/**
 * Use this class for code convention recommandation.
 * 
 * @author Zeng Shaoning (http://zsn.cc)
 * @version 1.0.0
 * @since 6.0
 */
public class UserPlugin extends PluginTemplate {

	// ~ STATIC FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;

	// ~ STATIC METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT FIELDS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String ignored;

	// ~ CONSTRUCTORS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ OBJECT METHODS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public boolean enable() throws PluginException {
		String[] ignoredUrls = this.ignored == null ? null : this.ignored.split(",");
		for (String url : ignoredUrls) {
			if (getRequest().getRequestURI().contains(url)) {
				return true;
			}
		}
		String user = (String) getRequest().getSession(true).getAttribute("_USER_");
		if (StringUtil.isBlank(user)) {
			getRequest().setAttribute("_BACK_", getRequest().getRequestURI());
			getRequest().setAttribute("_VIEW_",
					new RedirectView("./signin.jxp?back=" + getRequest().getRequestURL()));
		}
		return false;
	}

	public void disable() throws PluginException {
		// TODO Auto-generated method stub

	}

	// ~ g^setXXX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getIgnored() {
		return ignored;
	}

	public void setIgnored(String ignored) {
		this.ignored = ignored;
	}

}
