/**
 * 
 */
package org.zengsource.mvc.action.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zengsource.mvc.MvcException;


/**
 * @author zeng.xiaoning
 * 
 */
public class DataActionEditor extends DefaultActionEditor {
	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String regex;
	private String separator;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public DataActionEditor() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public String getActionName(String actionHierachy) throws MvcException {
		String[] slices = actionHierachy.split(this.separator);
		return slices[0].replace("/", ".");
	}

	@Override
	public Map<String, String> getDataMap(String actionHierachy) throws MvcException {
		int index = actionHierachy.indexOf(this.separator);
		int lastDot = actionHierachy.lastIndexOf(".");
		String dataStr = actionHierachy.substring(index, lastDot);
		Pattern pattern = Pattern.compile(this.regex, Pattern.CASE_INSENSITIVE
				+ Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(dataStr);
		int groupCount = matcher.groupCount();
		if (groupCount != 2) {
			throw new MvcException("Wrong matched group count: " + groupCount);
		}
		Map<String, String> data = new HashMap<String, String>();
		while (matcher.find()) {
			String name = matcher.group(0);
			String value = matcher.group(1);
			data.put(name, value);
			
		}
		return data;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
