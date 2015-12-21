
package org.backpacker4.web.listitem;

import org.backpacker4.bean.Photo;
import org.backpacker4.web.common.ListItem;

public class PhotoListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public PhotoListItem(Photo photo) {
		super();

		this.value = ""
			 + photo.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = photo.toString();
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
