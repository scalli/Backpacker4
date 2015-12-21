
package org.backpacker4.web.listitem;

import org.backpacker4.bean.UserRoles;
import org.backpacker4.web.common.ListItem;

public class UserRolesListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public UserRolesListItem(UserRoles userRoles) {
		super();

		this.value = ""
			 + userRoles.getIduserRoles()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = userRoles.toString();
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
