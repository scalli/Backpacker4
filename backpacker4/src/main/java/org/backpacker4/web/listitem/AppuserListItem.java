
package org.backpacker4.web.listitem;

import org.backpacker4.bean.Appuser;
import org.backpacker4.web.common.ListItem;

public class AppuserListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public AppuserListItem(Appuser appuser) {
		super();

		this.value = ""
			 + appuser.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = appuser.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
