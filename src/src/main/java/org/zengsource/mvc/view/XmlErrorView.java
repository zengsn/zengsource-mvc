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
public class XmlErrorView extends XmlView {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Map<String, String> errors;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public XmlErrorView() {
		errors = new HashMap<String, String>();
	}

	public XmlErrorView(String id, String msg) {
		this();
		this.errors.put(id, msg);
	}

	/**
	 * Format: id#msg
	 * 
	 * @param arr
	 */
	public XmlErrorView(String[] arr) {
		this();
		for (String str : arr) {
			String[] pair = str.split("#");
			if (pair.length == 2) {
				this.errors.put(pair[0], pair[1]);
			}
		}
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public void put(String id, String msg) {
		this.errors.put(id, msg);
	}

	@Override
	public void forward() throws MvcException {
		this.document = DocumentHelper.createDocument();
		Element root = this.document.addElement("message");
		root.addAttribute("success", "false");
		Element errors = root.addElement("errors");
		for (String id : this.errors.keySet()) {
			Element field = errors.addElement("field");
			field.addElement("id").addText(id + "");
			field.addElement("msg").addCDATA(this.errors.get(id) + "");
		}
		this.xml = this.document.asXML();
		super.forward();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
