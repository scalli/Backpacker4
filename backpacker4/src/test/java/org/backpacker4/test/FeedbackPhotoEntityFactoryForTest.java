package org.backpacker4.test;

import org.backpacker4.bean.jpa.FeedbackPhotoEntity;

public class FeedbackPhotoEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public FeedbackPhotoEntity newFeedbackPhotoEntity() {

		Long idFeedback = mockValues.nextLong();
		Long idPhoto = mockValues.nextLong();

		FeedbackPhotoEntity feedbackPhotoEntity = new FeedbackPhotoEntity();
		feedbackPhotoEntity.setIdFeedback(idFeedback);
		feedbackPhotoEntity.setIdPhoto(idPhoto);
		return feedbackPhotoEntity;
	}
	
}
