/**
 * 
 */
package org.zengsource.mvc.action.editor;

import java.io.Serializable;
import java.util.Map;

import org.zengsource.mvc.MvcException;


/**
 * @author zeng.xiaoning
 * @since 6.0
 */
public interface ActionEditor extends Serializable {

	public String getActionName(String actionHierachy) throws MvcException;
	
	public Map<String, String> getDataMap(String actionHierachy) throws MvcException;

}
