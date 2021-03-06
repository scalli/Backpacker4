
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.Feedback;
import org.backpacker4.bean.jpa.FeedbackEntity;
import java.util.Date;
import java.util.List;
import org.backpacker4.business.service.FeedbackService;
import org.backpacker4.business.service.mapping.FeedbackServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.FeedbackPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of FeedbackService
 */
@Component
public class FeedbackServiceImpl implements FeedbackService {

	private FeedbackPersistence feedbackPersistence;

	@Resource
	private FeedbackServiceMapper feedbackServiceMapper;
	
	public FeedbackServiceImpl() {
		feedbackPersistence = PersistenceServiceProvider.getService(FeedbackPersistence.class);
	}
		
	@Override
	public Feedback findById(Long id) {
		FeedbackEntity entity = feedbackPersistence.load(id);
		return feedbackServiceMapper.mapFeedbackEntityToFeedback(entity);
	}

	@Override
	public List<Feedback> findAll() {
		List<FeedbackEntity> entities = feedbackPersistence.loadAll();
		List<Feedback> beans = new ArrayList<Feedback>();
		for(FeedbackEntity entity : entities) {
			beans.add(feedbackServiceMapper.mapFeedbackEntityToFeedback(entity));
		}
		return beans;
	}

	@Override
	public Feedback save(Feedback feedback) {
		return update(feedback) ;
	}

	@Override
	public Feedback create(Feedback feedback) {
		if(feedbackPersistence.load(feedback.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		FeedbackEntity feedbackEntity = new FeedbackEntity();
		feedbackServiceMapper.mapFeedbackToFeedbackEntity(feedback, feedbackEntity);
		FeedbackEntity feedbackEntitySaved = feedbackPersistence.save(feedbackEntity);
		return feedbackServiceMapper.mapFeedbackEntityToFeedback(feedbackEntitySaved);
	}

	@Override
	public Feedback update(Feedback feedback) {
		FeedbackEntity feedbackEntity = feedbackPersistence.load(feedback.getId());
		feedbackServiceMapper.mapFeedbackToFeedbackEntity(feedback, feedbackEntity);
		FeedbackEntity feedbackEntitySaved = feedbackPersistence.save(feedbackEntity);
		return feedbackServiceMapper.mapFeedbackEntityToFeedback(feedbackEntitySaved);
	}

	@Override
	public void delete(Long id) {
		feedbackPersistence.delete(id);
	}

	public FeedbackPersistence getFeedbackPersistence() {
		return feedbackPersistence;
	}

	public void setFeedbackPersistence(FeedbackPersistence feedbackPersistence) {
		this.feedbackPersistence = feedbackPersistence;
	}

	public FeedbackServiceMapper getFeedbackServiceMapper() {
		return feedbackServiceMapper;
	}

	public void setFeedbackServiceMapper(FeedbackServiceMapper feedbackServiceMapper) {
		this.feedbackServiceMapper = feedbackServiceMapper;
	}

}
