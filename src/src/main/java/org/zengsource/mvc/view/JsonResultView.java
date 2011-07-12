/**
 * 
 */
package org.zengsource.mvc.view;

import java.util.HashMap;
import java.util.Map;

import org.zengsource.mvc.MvcException;


/**
 * @author snzeng
 * 
 */
public class JsonResultView extends HtmlView {

	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Map<String, String> resultMap;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public JsonResultView() {
		resultMap = new HashMap<String, String>();
	}

	public JsonResultView(String name, String value) {
		this();
		resultMap.put(name, value);
	}

	public JsonResultView(HashMap<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void put(String name, String value) {
		this.resultMap.put(name, value);
	}

	@Override
	public void forward() throws MvcException {
		StringBuilder json = new StringBuilder();
		json.append("\n{\n");
		json.append("  success:true,\n");
		json.append("  result: {\n");
		for (String name : this.resultMap.keySet()) {
			json.append("    " + name + ":'" + this.resultMap.get(name) + "'\n");
		}
		json.append("  }\n");
		json.append("}\n");
		setHtml(json.toString());
		super.forward();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

}
