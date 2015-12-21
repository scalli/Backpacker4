
package org.backpacker4.web.listitem;

import org.backpacker4.bean.Feedback;
import org.backpacker4.bean.FeedbackReadable;
import org.backpacker4.web.common.ListItem;

public class FeedbackReadableListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public FeedbackReadableListItem(FeedbackReadable feedbackr) {
		super();

		this.value = ""
			 + feedbackr.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = feedbackr.toString();
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
