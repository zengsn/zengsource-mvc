/**
 * 
 */
package org.zengsource.mvc.plugin;

import java.io.Serializable;

/**
 * @author snzeng
 *
 */
public interface PluginFactory extends Serializable {

	public boolean enableAll() throws PluginException;

	public boolean enable(String pluginId) throws PluginException;

	public void disableAll() throws PluginException;

	public void disable(String pluginId) throws PluginException;
	
}
