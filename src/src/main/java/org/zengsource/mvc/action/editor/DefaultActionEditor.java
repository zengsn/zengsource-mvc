/**
 * 
 */
package org.zengsource.mvc.action.editor;

import java.util.Map;

import org.zengsource.mvc.MvcException;


/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public class DefaultActionEditor implements ActionEditor {
	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public DefaultActionEditor() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getActionName(String actionHierachy) throws MvcException {
		int lastDot = actionHierachy.lastIndexOf(".");
		if (lastDot > -1) {
			actionHierachy = actionHierachy.substring(0, lastDot);
		}
		return actionHierachy.replace("/", ".");
	}

	public Map<String, String> getDataMap(String actionHierachy) throws MvcException {
		return null;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
}
