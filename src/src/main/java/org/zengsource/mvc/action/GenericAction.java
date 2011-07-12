/**
 * 
 */
package org.zengsource.mvc.action;

import org.zengsource.mvc.MvcException;
import org.zengsource.mvc.view.AbstractView;
import org.zengsource.mvc.view.DispatchView;
import org.zengsource.util.NumberUtil;
import org.zengsource.util.StringUtil;

/**
 * @author zeng.xiaoning
 * 
 */
public class GenericAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	// ~ 对象属性 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //
	
	private String actionName;

	private String name;
	private String forward;

	private String q;
	private String id;
	private String opt;
	private String start;
	private String limit;
	private String field;
	private String value;

	// ~ 构造方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	public GenericAction() {
	}

	// ~ 逻辑方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	protected AbstractView doService() throws MvcException {
		logger.info("Run business logic of action:" + getActionName());
		if (StringUtil.notBlank(forward)) {
			return new DispatchView(this.forward);
		}
		return new DispatchView(getContext().getDefaultDispatchPage());
	}

	protected Integer getStartInt() {
		return NumberUtil.string2Integer(start, 0);
	}

	protected Integer getLimitInt() {
		return NumberUtil.string2Integer(limit, 0);
	}

	// ~ g^setX ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

	@Override
	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
