
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.Photo;
import org.backpacker4.bean.jpa.PhotoEntity;
import java.util.Date;
import java.util.List;
import org.backpacker4.business.service.PhotoService;
import org.backpacker4.business.service.mapping.PhotoServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.PhotoPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of PhotoService
 */
@Component
public class PhotoServiceImpl implements PhotoService {

	private PhotoPersistence photoPersistence;

	@Resource
	private PhotoServiceMapper photoServiceMapper;
	
	public PhotoServiceImpl() {
		photoPersistence = PersistenceServiceProvider.getService(PhotoPersistence.class);
	}
		
	@Override
	public Photo findById(Long id) {
		PhotoEntity entity = photoPersistence.load(id);
		return photoServiceMapper.mapPhotoEntityToPhoto(entity);
	}

	@Override
	public List<Photo> findAll() {
		List<PhotoEntity> entities = photoPersistence.loadAll();
		List<Photo> beans = new ArrayList<Photo>();
		for(PhotoEntity entity : entities) {
			beans.add(photoServiceMapper.mapPhotoEntityToPhoto(entity));
		}
		return beans;
	}

	@Override
	public Photo save(Photo photo) {
		return update(photo) ;
	}

	@Override
	public Photo create(Photo photo) {
		if(photoPersistence.load(photo.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		PhotoEntity photoEntity = new PhotoEntity();
		photoServiceMapper.mapPhotoToPhotoEntity(photo, photoEntity);
		PhotoEntity photoEntitySaved = photoPersistence.save(photoEntity);
		return photoServiceMapper.mapPhotoEntityToPhoto(photoEntitySaved);
	}

	@Override
	public Photo update(Photo photo) {
		PhotoEntity photoEntity = photoPersistence.load(photo.getId());
		photoServiceMapper.mapPhotoToPhotoEntity(photo, photoEntity);
		PhotoEntity photoEntitySaved = photoPersistence.save(photoEntity);
		return photoServiceMapper.mapPhotoEntityToPhoto(photoEntitySaved);
	}

	@Override
	public void delete(Long id) {
		photoPersistence.delete(id);
	}

	public PhotoPersistence getPhotoPersistence() {
		return photoPersistence;
	}

	public void setPhotoPersistence(PhotoPersistence photoPersistence) {
		this.photoPersistence = photoPersistence;
	}

	public PhotoServiceMapper getPhotoServiceMapper() {
		return photoServiceMapper;
	}

	public void setPhotoServiceMapper(PhotoServiceMapper photoServiceMapper) {
		this.photoServiceMapper = photoServiceMapper;
	}

}
