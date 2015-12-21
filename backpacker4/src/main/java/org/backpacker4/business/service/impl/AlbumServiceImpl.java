
package org.backpacker4.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.backpacker4.bean.Album;
import org.backpacker4.bean.jpa.AlbumEntity;
import java.util.Date;
import java.util.List;
import org.backpacker4.business.service.AlbumService;
import org.backpacker4.business.service.mapping.AlbumServiceMapper;
import org.backpacker4.persistence.PersistenceServiceProvider;
import org.backpacker4.persistence.services.AlbumPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of AlbumService
 */
@Component
public class AlbumServiceImpl implements AlbumService {

	private AlbumPersistence albumPersistence;

	@Resource
	private AlbumServiceMapper albumServiceMapper;
	
	public AlbumServiceImpl() {
		albumPersistence = PersistenceServiceProvider.getService(AlbumPersistence.class);
	}
		
	@Override
	public Album findById(Long id) {
		AlbumEntity entity = albumPersistence.load(id);
		return albumServiceMapper.mapAlbumEntityToAlbum(entity);
	}

	@Override
	public List<Album> findAll() {
		List<AlbumEntity> entities = albumPersistence.loadAll();
		List<Album> beans = new ArrayList<Album>();
		for(AlbumEntity entity : entities) {
			beans.add(albumServiceMapper.mapAlbumEntityToAlbum(entity));
		}
		return beans;
	}

	@Override
	public Album save(Album album) {
		return update(album) ;
	}

	@Override
	public Album create(Album album) {
		if(albumPersistence.load(album.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		AlbumEntity albumEntity = new AlbumEntity();
		albumServiceMapper.mapAlbumToAlbumEntity(album, albumEntity);
		AlbumEntity albumEntitySaved = albumPersistence.save(albumEntity);
		return albumServiceMapper.mapAlbumEntityToAlbum(albumEntitySaved);
	}

	@Override
	public Album update(Album album) {
		AlbumEntity albumEntity = albumPersistence.load(album.getId());
		albumServiceMapper.mapAlbumToAlbumEntity(album, albumEntity);
		AlbumEntity albumEntitySaved = albumPersistence.save(albumEntity);
		return albumServiceMapper.mapAlbumEntityToAlbum(albumEntitySaved);
	}

	@Override
	public void delete(Long id) {
		albumPersistence.delete(id);
	}

	public AlbumPersistence getAlbumPersistence() {
		return albumPersistence;
	}

	public void setAlbumPersistence(AlbumPersistence albumPersistence) {
		this.albumPersistence = albumPersistence;
	}

	public AlbumServiceMapper getAlbumServiceMapper() {
		return albumServiceMapper;
	}

	public void setAlbumServiceMapper(AlbumServiceMapper albumServiceMapper) {
		this.albumServiceMapper = albumServiceMapper;
	}

}
