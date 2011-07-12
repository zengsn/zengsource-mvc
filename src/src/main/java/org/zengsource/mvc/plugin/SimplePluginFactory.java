/**
 * 
 */
package org.zengsource.mvc.plugin;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author snzeng
 * 
 */
public class SimplePluginFactory implements PluginFactory {
	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	Logger logger = Logger.getLogger(getClass());

	private Map<String, Plugable> pluginMap = new HashMap<String, Plugable>();

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public SimplePluginFactory() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public boolean enable(String pluginId) throws PluginException {
		Plugable plugin = this.pluginMap.get(pluginId);
		if (plugin != null) {
			return plugin.enable();
		} else {
			throw new PluginException("No such plugin: " + pluginId);
		}
	}

	public boolean enableAll() throws PluginException {
		for (String pluginId : this.pluginMap.keySet()) {
			if (!enable(pluginId) ) {
				return false;
			}
		}
		return true;
	}

	public void disable(String pluginId) throws PluginException {
		Plugable plugin = this.pluginMap.get(pluginId);
		if (plugin != null) {
			plugin.disable();
		} else {
			throw new PluginException("No such plugin: " + pluginId);
		}
	}

	public void disableAll() throws PluginException {
		for (String pluginId : this.pluginMap.keySet()) {
			disable(pluginId);
		}
	}

	// ~ g^setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Map<String, Plugable> getPluginMap() {
		return pluginMap;
	}

	public void setPluginMap(Map<String, Plugable> pluginMap) {
		this.pluginMap = pluginMap;
	}

}
