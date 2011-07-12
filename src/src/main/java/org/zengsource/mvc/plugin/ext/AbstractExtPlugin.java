/**
 * 
 */
package org.zengsource.mvc.plugin.ext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import org.zengsource.mvc.plugin.PluginException;
import org.zengsource.mvc.plugin.PluginTemplate;
import org.zengsource.util.StringUtil;

/**
 * @author snzeng
 * @since 6.0
 */
public abstract class  AbstractExtPlugin extends PluginTemplate {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private static final long serialVersionUID = 1L;
	public static final String MODE_PRODUCTION = "production";
	public static final String MODE_DEVELOPMENT = "development";
	public static final String DEFAULT_VERSION = "3.2.1";

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public AbstractExtPlugin() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	/**
	 * Ext 库的链接地址。
	 */
	public abstract String getExtUrl();
	
	/**
	 * Ext 库在文件系统中的路径。
	 * @return
	 */
	public abstract String getExtPath();

	public boolean enable() throws PluginException {

		// Root URL
		String extURL = getExtUrl();
		if (extURL != null && !extURL.startsWith("http://")) {
			extURL = getRequest().getContextPath() + extURL;
		}
		getRequest().setAttribute("_EXT_URL_", extURL);

		// Ext configuration
		// String extRoot = defaults.getProperty("jxsite.ext.home");
		String extRoot = getExtPath();
		logger.info("Ext root path: " + extRoot);
		Properties extProperties = new Properties();
		try {
			extProperties.load(new FileReader(new File(extRoot + "/ext.properties")));

			// Version
			String version = extProperties.getProperty("version", DEFAULT_VERSION);
			getRequest().setAttribute("_EXT_VERSION_", version);

			// production or development
			String mode = extProperties.getProperty("mode", MODE_PRODUCTION);
			if (MODE_DEVELOPMENT.equals(mode)) {
				// Source
				String source = extProperties.getProperty("source", "source");
				getRequest().setAttribute("_EXT_SOURCE_", source);

				SAXReader extReader = new SAXReader();
				Document doc = extReader.read(new File(extRoot + "/" + version + "/" + source
						+ "/ext.jsb"));
				List<?> targetList = doc.getRootElement().elements("target");
				for (Object obj : targetList) {
					Element targetEle = (Element) obj;
					String targetName = targetEle.attributeValue("name");
					if ("Everything".equals(targetName)) {
						List<?> includeList = targetEle.elements("include");
						List<String> sourceFiles = new ArrayList<String>();
						for (Object obj2 : includeList) {
							Element includeEle = (Element) obj2;
							String file = includeEle.attributeValue("name");
							if (StringUtil.notBlank(file)) {
								sourceFiles.add(file.replaceAll("\\\\", "/"));
							}
						}
						getRequest().setAttribute("_EXT_SOURCE_ALL_", sourceFiles);
						break;
					}
				}
			} else { // production mode
				// Let page do this
			}
			return true;
		} catch (IOException e) {
			throw new PluginException("Cannot read ext.properties!", e);
		} catch (DocumentException e) {
			throw new PluginException("Cannot read ext.jsb!", e);
		}
	}

	public void disable() throws PluginException {
		// TODO Auto-generated method stub
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

}
