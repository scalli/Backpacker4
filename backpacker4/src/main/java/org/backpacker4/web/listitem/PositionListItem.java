
package org.backpacker4.web.listitem;

import org.backpacker4.bean.Position;
import org.backpacker4.web.common.ListItem;

public class PositionListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public PositionListItem(Position position) {
		super();

		this.value = ""
			 + position.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = position.toString();
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
