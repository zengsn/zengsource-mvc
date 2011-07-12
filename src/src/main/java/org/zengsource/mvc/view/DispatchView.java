/**
 * 
 */
package org.zengsource.mvc.view;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.zengsource.mvc.MvcException;
import org.zengsource.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class DispatchView extends AbstractView {
	
	// ~ 静态属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	protected String page;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public DispatchView() {
	}
	
	public DispatchView(String page) {
		this.page = page;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public void forward() throws MvcException {
		if (StringUtil.isBlank(this.page)) {
			throw new MvcException("No page specified!");
		}
		logger.info("Dispatching to " + this.page);
		RequestDispatcher dispatcher = getRequest().getRequestDispatcher(page);
		try {
			dispatcher.forward(getRequest(), getResponse());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ~ 静态方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	public static AbstractView error(String title, String message) {
		DispatchView view = new DispatchView("/error.jsp");
		view.getRequest().setAttribute("title", title);
		view.getRequest().setAttribute("message", message);
		return view;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
