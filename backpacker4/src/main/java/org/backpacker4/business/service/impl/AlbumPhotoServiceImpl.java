
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.AlbumPhoto;
import org.backpacker4.bean.jpa.AlbumPhotoEntity;
import org.backpacker4.bean.jpa.AlbumPhotoEntityKey;
import org.backpacker4.business.service.AlbumPhotoService;
import org.backpacker4.business.service.mapping.AlbumPhotoServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.AlbumPhotoPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of AlbumPhotoService
 */
@Component
public class AlbumPhotoServiceImpl implements AlbumPhotoService {

	private AlbumPhotoPersistence albumPhotoPersistence;

	@Resource
	private AlbumPhotoServiceMapper albumPhotoServiceMapper;
	
	public AlbumPhotoServiceImpl() {
		albumPhotoPersistence = PersistenceServiceProvider.getService(AlbumPhotoPersistence.class);
	}
		
	@Override
	public AlbumPhoto findById(Long idAlbum, Long idPhoto) {
		AlbumPhotoEntity entity = albumPhotoPersistence.load(idAlbum, idPhoto);
		return albumPhotoServiceMapper.mapAlbumPhotoEntityToAlbumPhoto(entity);
	}

	@Override
	public List<AlbumPhoto> findAll() {
		List<AlbumPhotoEntity> entities = albumPhotoPersistence.loadAll();
		List<AlbumPhoto> beans = new ArrayList<AlbumPhoto>();
		for(AlbumPhotoEntity entity : entities) {
			beans.add(albumPhotoServiceMapper.mapAlbumPhotoEntityToAlbumPhoto(entity));
		}
		return beans;
	}

	@Override
	public AlbumPhoto save(AlbumPhoto albumPhoto) {
		return update(albumPhoto) ;
	}

	@Override
	public AlbumPhoto create(AlbumPhoto albumPhoto) {
		if(albumPhotoPersistence.load(albumPhoto.getIdAlbum(), albumPhoto.getIdPhoto()) != null) {
			throw new IllegalStateException("already.exists");
		}
		AlbumPhotoEntity albumPhotoEntity = new AlbumPhotoEntity();
		albumPhotoServiceMapper.mapAlbumPhotoToAlbumPhotoEntity(albumPhoto, albumPhotoEntity);
		AlbumPhotoEntity albumPhotoEntitySaved = albumPhotoPersistence.save(albumPhotoEntity);
		return albumPhotoServiceMapper.mapAlbumPhotoEntityToAlbumPhoto(albumPhotoEntitySaved);
	}

	@Override
	public AlbumPhoto update(AlbumPhoto albumPhoto) {
		AlbumPhotoEntity albumPhotoEntity = albumPhotoPersistence.load(albumPhoto.getIdAlbum(), albumPhoto.getIdPhoto());
		albumPhotoServiceMapper.mapAlbumPhotoToAlbumPhotoEntity(albumPhoto, albumPhotoEntity);
		AlbumPhotoEntity albumPhotoEntitySaved = albumPhotoPersistence.save(albumPhotoEntity);
		return albumPhotoServiceMapper.mapAlbumPhotoEntityToAlbumPhoto(albumPhotoEntitySaved);
	}

	@Override
	public void delete(Long idAlbum, Long idPhoto) {
		albumPhotoPersistence.delete(idAlbum, idPhoto);
	}

	public AlbumPhotoPersistence getAlbumPhotoPersistence() {
		return albumPhotoPersistence;
	}

	public void setAlbumPhotoPersistence(AlbumPhotoPersistence albumPhotoPersistence) {
		this.albumPhotoPersistence = albumPhotoPersistence;
	}

	public AlbumPhotoServiceMapper getAlbumPhotoServiceMapper() {
		return albumPhotoServiceMapper;
	}

	public void setAlbumPhotoServiceMapper(AlbumPhotoServiceMapper albumPhotoServiceMapper) {
		this.albumPhotoServiceMapper = albumPhotoServiceMapper;
	}

}
