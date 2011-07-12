/**
 * 
 */
package org.zengsource.mvc.view;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.zengsource.mvc.MvcException;


/**
 * @author zeng.xiaoning
 * 
 */
public class XmlResultView extends XmlView {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Map<String, String> results;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public XmlResultView() {
		results = new HashMap<String, String>();
	}

	public XmlResultView(String id, String msg) {
		this();
		this.results.put(id, msg);
	}

	/**
	 * Format: id#msg
	 * 
	 * @param arr
	 */
	public XmlResultView(String[] arr) {
		this();
		for (String str : arr) {
			String[] pair = str.split("#");
			if (pair.length == 2) {
				this.results.put(pair[0], pair[1]);
			}
		}
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void put(String id, String msg) {
		this.results.put(id, msg);
	}

	@Override
	public void forward() throws MvcException {
		this.document = DocumentHelper.createDocument();
		Element root = this.document.addElement("message");
		root.addAttribute("success", "true");
		Element ele = root.addElement("result");
		for (String id : this.results.keySet()) {
			Element field = ele.addElement(id);
			field.addCDATA(this.results.get(id) + "");
		}
		this.xml = this.document.asXML();
		super.forward();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Map<String, String> getResults() {
		return results;
	}
	
	public void setResults(Map<String, String> results) {
		this.results = results;
	}
}
