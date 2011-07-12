/**
 * 
 */
package org.zengsource.mvc.view;

import java.io.IOException;
import java.io.Writer;

import org.zengsource.mvc.MvcException;


/**
 * @author zeng.xiaoning
 * 
 */
public class HtmlView extends AbstractView {

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String html;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public HtmlView() {
	}

	public HtmlView(String html) {
		this.html = html;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public void forward() throws MvcException {
		logger.info("Output HTML: " + this.html);
		getResponse().setContentType("text/html");
		getResponse().setCharacterEncoding("UTF-8");
		try {
			Writer writer = getResponse().getWriter();
			writer.write(this.html);
		} catch (IOException e) {
			throw new MvcException(e);
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
