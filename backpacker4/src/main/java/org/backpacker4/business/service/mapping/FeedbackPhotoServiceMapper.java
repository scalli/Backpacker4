
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.FeedbackPhoto;
import org.backpacker4.bean.jpa.FeedbackPhotoEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class FeedbackPhotoServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public FeedbackPhotoServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'FeedbackPhotoEntity' to 'FeedbackPhoto'
	 * @param feedbackPhotoEntity
	 */
	public FeedbackPhoto mapFeedbackPhotoEntityToFeedbackPhoto(FeedbackPhotoEntity feedbackPhotoEntity) {
		if(feedbackPhotoEntity == null) {
			return null;
		}

		//--- Generic mapping 
		FeedbackPhoto feedbackPhoto = map(feedbackPhotoEntity, FeedbackPhoto.class);

		return feedbackPhoto;
	}
	
	/**
	 * Mapping from 'FeedbackPhoto' to 'FeedbackPhotoEntity'
	 * @param feedbackPhoto
	 * @param feedbackPhotoEntity
	 */
	public void mapFeedbackPhotoToFeedbackPhotoEntity(FeedbackPhoto feedbackPhoto, FeedbackPhotoEntity feedbackPhotoEntity) {
		if(feedbackPhoto == null) {
			return;
		}

		//--- Generic mapping 
		map(feedbackPhoto, feedbackPhotoEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}