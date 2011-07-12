/**
 * 
 */
package org.zengsource.mvc.plugin;

import java.io.Serializable;

/**
 * @author snzeng
 *
 */
public interface Plugable extends Serializable {
	
	public boolean enable() throws PluginException;
	
	public void disable() throws PluginException;

}
