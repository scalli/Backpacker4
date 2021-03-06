
package org.backpacker4.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.backpacker4.bean.Feedback;
import org.backpacker4.bean.jpa.FeedbackEntity;
import org.backpacker4.bean.jpa.AppuserEntity;
import org.backpacker4.bean.jpa.TypeinfoEntity;
import org.backpacker4.bean.jpa.PositionEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class FeedbackServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public FeedbackServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'FeedbackEntity' to 'Feedback'
	 * @param feedbackEntity
	 */
	public Feedback mapFeedbackEntityToFeedback(FeedbackEntity feedbackEntity) {
		if(feedbackEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Feedback feedback = map(feedbackEntity, Feedback.class);

		//--- Link mapping ( link to Appuser )
		if(feedbackEntity.getAppuser() != null) {
			feedback.setIdUser(feedbackEntity.getAppuser().getId());
		}
		//--- Link mapping ( link to Typeinfo )
		if(feedbackEntity.getTypeinfo() != null) {
			feedback.setIdTypeinfo(feedbackEntity.getTypeinfo().getId());
		}
		//--- Link mapping ( link to Position )
		if(feedbackEntity.getPosition() != null) {
			feedback.setIdPosition(feedbackEntity.getPosition().getId());
		}
		return feedback;
	}
	
	/**
	 * Mapping from 'Feedback' to 'FeedbackEntity'
	 * @param feedback
	 * @param feedbackEntity
	 */
	public void mapFeedbackToFeedbackEntity(Feedback feedback, FeedbackEntity feedbackEntity) {
		if(feedback == null) {
			return;
		}

		//--- Generic mapping 
		map(feedback, feedbackEntity);

		//--- Link mapping ( link : feedback )
		if( hasLinkToAppuser(feedback) ) {
			AppuserEntity appuser1 = new AppuserEntity();
			appuser1.setId( feedback.getIdUser() );
			feedbackEntity.setAppuser( appuser1 );
		} else {
			feedbackEntity.setAppuser( null );
		}

		//--- Link mapping ( link : feedback )
		if( hasLinkToTypeinfo(feedback) ) {
			TypeinfoEntity typeinfo2 = new TypeinfoEntity();
			typeinfo2.setId( feedback.getIdTypeinfo() );
			feedbackEntity.setTypeinfo( typeinfo2 );
		} else {
			feedbackEntity.setTypeinfo( null );
		}

		//--- Link mapping ( link : feedback )
		if( hasLinkToPosition(feedback) ) {
			PositionEntity position3 = new PositionEntity();
			position3.setId( feedback.getIdPosition() );
			feedbackEntity.setPosition( position3 );
		} else {
			feedbackEntity.setPosition( null );
		}

	}
	
	/**
	 * Verify that Appuser id is valid.
	 * @param Appuser Appuser
	 * @return boolean
	 */
	private boolean hasLinkToAppuser(Feedback feedback) {
		if(feedback.getIdUser() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Verify that Typeinfo id is valid.
	 * @param Typeinfo Typeinfo
	 * @return boolean
	 */
	private boolean hasLinkToTypeinfo(Feedback feedback) {
		if(feedback.getIdTypeinfo() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Verify that Position id is valid.
	 * @param Position Position
	 * @return boolean
	 */
	private boolean hasLinkToPosition(Feedback feedback) {
		if(feedback.getIdPosition() != null) {
			return true;
		}
		return false;
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