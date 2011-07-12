/**
 * 
 */
package org.zengsource.mvc.view;

import java.io.IOException;

import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.Redirect;
import org.zengsource.util.StringUtil;

/**
 * @author snzeng
 * 
 */
public class RedirectView extends DispatchView {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private Redirect redirect;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public RedirectView(Redirect redirect) {
		this.redirect = redirect;
	}

	public RedirectView(String page) {
		this.page = page;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public void forward() throws MvcException {
		if (StringUtil.isBlank(this.page)) {
			this.page = "/redirect.jsp";
		}
		//getRequest().setAttribute("redirect", this.redirect);
		//super.forward();
		try {
			getResponse().sendRedirect(this.page);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Redirect getRedirect() {
		return redirect;
	}

	public void setRedirect(Redirect redirect) {
		this.redirect = redirect;
	}

}
