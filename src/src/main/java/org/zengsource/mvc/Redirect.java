/**
 * 
 */
package org.zengsource.mvc;

import java.io.Serializable;

/**
 * @author zeng.xiaoning
 * 
 */
public class Redirect implements Serializable {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	private String url;
	private String time = "0";
	private String title = "正在转接...";
	private String message = "请稍候...";

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public Redirect() {
	}
	
	public Redirect(String url) {
		this.url = url;
	}

	public Redirect(String url, String time, String title, String message) {
		super();
		this.url = url;
		this.time = time;
		this.title = title;
		this.message = message;
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	@Override
	public String toString() {
		return this.url;
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
