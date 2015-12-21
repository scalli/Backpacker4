
package org.backpacker4.web.listitem;

import org.backpacker4.bean.Typeinfo;
import org.backpacker4.web.common.ListItem;

public class TypeinfoListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public TypeinfoListItem(Typeinfo typeinfo) {
		super();

		this.value = ""
			 + typeinfo.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = typeinfo.toString();
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
