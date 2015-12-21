
package org.backpacker4.web.listitem;

import org.backpacker4.bean.Feedback;
import org.backpacker4.web.common.ListItem;

public class FeedbackListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public FeedbackListItem(Feedback feedback) {
		super();

		this.value = ""
			 + feedback.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = feedback.toString();
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
