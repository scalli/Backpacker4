package org.backpacker4.test;

import org.backpacker4.bean.FeedbackPhoto;

public class FeedbackPhotoFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public FeedbackPhoto newFeedbackPhoto() {

		Long idFeedback = mockValues.nextLong();
		Long idPhoto = mockValues.nextLong();

		FeedbackPhoto feedbackPhoto = new FeedbackPhoto();
		feedbackPhoto.setIdFeedback(idFeedback);
		feedbackPhoto.setIdPhoto(idPhoto);
		return feedbackPhoto;
	}
	
}
