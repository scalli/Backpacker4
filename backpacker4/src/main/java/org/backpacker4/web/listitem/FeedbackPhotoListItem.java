
package org.backpacker4.web.listitem;

import org.backpacker4.bean.FeedbackPhoto;
import org.backpacker4.web.common.ListItem;

public class FeedbackPhotoListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public FeedbackPhotoListItem(FeedbackPhoto feedbackPhoto) {
		super();

		this.value = ""
			 + feedbackPhoto.getIdFeedback()
			 + "|"  + feedbackPhoto.getIdPhoto()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = feedbackPhoto.toString();
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
