
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.FeedbackPhoto;
import org.backpacker4.bean.jpa.FeedbackPhotoEntity;
import org.backpacker4.bean.jpa.FeedbackPhotoEntityKey;
import org.backpacker4.business.service.FeedbackPhotoService;
import org.backpacker4.business.service.mapping.FeedbackPhotoServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.FeedbackPhotoPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of FeedbackPhotoService
 */
@Component
public class FeedbackPhotoServiceImpl implements FeedbackPhotoService {

	private FeedbackPhotoPersistence feedbackPhotoPersistence;

	@Resource
	private FeedbackPhotoServiceMapper feedbackPhotoServiceMapper;
	
	public FeedbackPhotoServiceImpl() {
		feedbackPhotoPersistence = PersistenceServiceProvider.getService(FeedbackPhotoPersistence.class);
	}
		
	@Override
	public FeedbackPhoto findById(Long idFeedback, Long idPhoto) {
		FeedbackPhotoEntity entity = feedbackPhotoPersistence.load(idFeedback, idPhoto);
		return feedbackPhotoServiceMapper.mapFeedbackPhotoEntityToFeedbackPhoto(entity);
	}

	@Override
	public List<FeedbackPhoto> findAll() {
		List<FeedbackPhotoEntity> entities = feedbackPhotoPersistence.loadAll();
		List<FeedbackPhoto> beans = new ArrayList<FeedbackPhoto>();
		for(FeedbackPhotoEntity entity : entities) {
			beans.add(feedbackPhotoServiceMapper.mapFeedbackPhotoEntityToFeedbackPhoto(entity));
		}
		return beans;
	}

	@Override
	public FeedbackPhoto save(FeedbackPhoto feedbackPhoto) {
		return update(feedbackPhoto) ;
	}

	@Override
	public FeedbackPhoto create(FeedbackPhoto feedbackPhoto) {
		if(feedbackPhotoPersistence.load(feedbackPhoto.getIdFeedback(), feedbackPhoto.getIdPhoto()) != null) {
			throw new IllegalStateException("already.exists");
		}
		FeedbackPhotoEntity feedbackPhotoEntity = new FeedbackPhotoEntity();
		feedbackPhotoServiceMapper.mapFeedbackPhotoToFeedbackPhotoEntity(feedbackPhoto, feedbackPhotoEntity);
		FeedbackPhotoEntity feedbackPhotoEntitySaved = feedbackPhotoPersistence.save(feedbackPhotoEntity);
		return feedbackPhotoServiceMapper.mapFeedbackPhotoEntityToFeedbackPhoto(feedbackPhotoEntitySaved);
	}

	@Override
	public FeedbackPhoto update(FeedbackPhoto feedbackPhoto) {
		FeedbackPhotoEntity feedbackPhotoEntity = feedbackPhotoPersistence.load(feedbackPhoto.getIdFeedback(), feedbackPhoto.getIdPhoto());
		feedbackPhotoServiceMapper.mapFeedbackPhotoToFeedbackPhotoEntity(feedbackPhoto, feedbackPhotoEntity);
		FeedbackPhotoEntity feedbackPhotoEntitySaved = feedbackPhotoPersistence.save(feedbackPhotoEntity);
		return feedbackPhotoServiceMapper.mapFeedbackPhotoEntityToFeedbackPhoto(feedbackPhotoEntitySaved);
	}

	@Override
	public void delete(Long idFeedback, Long idPhoto) {
		feedbackPhotoPersistence.delete(idFeedback, idPhoto);
	}

	public FeedbackPhotoPersistence getFeedbackPhotoPersistence() {
		return feedbackPhotoPersistence;
	}

	public void setFeedbackPhotoPersistence(FeedbackPhotoPersistence feedbackPhotoPersistence) {
		this.feedbackPhotoPersistence = feedbackPhotoPersistence;
	}

	public FeedbackPhotoServiceMapper getFeedbackPhotoServiceMapper() {
		return feedbackPhotoServiceMapper;
	}

	public void setFeedbackPhotoServiceMapper(FeedbackPhotoServiceMapper feedbackPhotoServiceMapper) {
		this.feedbackPhotoServiceMapper = feedbackPhotoServiceMapper;
	}

}
