/**
 * 
 */
package org.zengsource.mvc.view;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import org.zengsource.mvc.MvcException;
import org.zengsource.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class XmlView extends AbstractView {

	public static final XmlView SUCCESS = new XmlView("<response success=\"true\"></response>");

	public static Element getXmlResponseRoot() {
		return getXmlResponseRoot(true);
	}

	public static Element getXmlResponseRoot(boolean success) {
		return DocumentHelper.createDocument().addElement("response").addAttribute("success",
				success + "");
	}

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	protected String xml;
	protected Document document;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public XmlView() {
	}

	public XmlView(Document document) {
		this.document = document;
		this.xml = document.asXML();
	}

	public XmlView(String xml) {
		this.xml = xml;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public void forward() throws MvcException {
		logger.info("Output XML: \n" + formatXML());
		getResponse().setContentType("application/xml");
		getResponse().setCharacterEncoding("UTF-8");
		try {
			Writer writer = getResponse().getWriter();
			if (StringUtil.isBlank(this.xml) && this.document != null) {
				this.xml = this.document.asXML();
			}
			writer.write(this.xml);
		} catch (IOException e) {
			throw new MvcException(e);
		}
	}

	private String formatXML() {
		if (this.document == null) {
			try {
				this.document = DocumentHelper.parseText(this.xml);
			} catch (DocumentException e) {
				return this.xml;
			}
		}
		StringWriter sw = new StringWriter();
		OutputFormat of = OutputFormat.createPrettyPrint();
		XMLWriter xmlWriter = new XMLWriter(sw, of);
		try {
			xmlWriter.write(this.document);
		} catch (IOException e) {
			return this.xml;
		}
		return sw.toString();
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

}
