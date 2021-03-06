package org.backpacker4.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.backpacker4.bean.jpa.AlbumPhotoEntity;
import org.backpacker4.bean.jpa.AlbumPhotoEntityKey;
import org.backpacker4.persistence.commons.jpa.GenericJpaService;
import org.backpacker4.persistence.commons.jpa.JpaOperation;
import org.backpacker4.persistence.services.AlbumPhotoPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "AlbumPhoto" )
 *
 */
public class AlbumPhotoPersistenceJPA extends GenericJpaService<AlbumPhotoEntity, AlbumPhotoEntityKey> implements AlbumPhotoPersistence {

	/**
	 * Constructor
	 */
	public AlbumPhotoPersistenceJPA() {
		super(AlbumPhotoEntity.class);
	}

	@Override
	public AlbumPhotoEntity load( Long idAlbum, Long idPhoto ) {
		// Build the composite key
		AlbumPhotoEntityKey key = new AlbumPhotoEntityKey( idAlbum, idPhoto );
		return super.load( key );
	}

	@Override
	public boolean delete( Long idAlbum, Long idPhoto ) {
		// Build the composite key
		AlbumPhotoEntityKey key = new AlbumPhotoEntityKey( idAlbum, idPhoto );
		return super.delete( key );
	}

	@Override
	public boolean delete(AlbumPhotoEntity entity) {
		if ( entity != null ) {
			// Build the composite key
			AlbumPhotoEntityKey key = new AlbumPhotoEntityKey( entity.getIdAlbum(), entity.getIdPhoto() );
			return super.delete( key );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("AlbumPhotoEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}
