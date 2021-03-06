
package org.backpacker4.bean.jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Composite primary key for entity "AlbumPhotoEntity" ( stored in table "album_photo" )
 *
 */
 @Embeddable
public class AlbumPhotoEntityKey implements Serializable {
    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY KEY ATTRIBUTES 
    //----------------------------------------------------------------------
    @Column(name="ID_ALBUM", nullable=false)
    private Long       idAlbum      ;
    
    @Column(name="ID_PHOTO", nullable=false)
    private Long       idPhoto      ;
    

    //----------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------
    public AlbumPhotoEntityKey() {
        super();
    }

    public AlbumPhotoEntityKey( Long idAlbum, Long idPhoto ) {
        super();
        this.idAlbum = idAlbum ;
        this.idPhoto = idPhoto ;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    //----------------------------------------------------------------------
    public void setIdAlbum( Long value ) {
        this.idAlbum = value;
    }
    public Long getIdAlbum() {
        return this.idAlbum;
    }

    public void setIdPhoto( Long value ) {
        this.idPhoto = value;
    }
    public Long getIdPhoto() {
        return this.idPhoto;
    }


    //----------------------------------------------------------------------
    // equals METHOD
    //----------------------------------------------------------------------
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		AlbumPhotoEntityKey other = (AlbumPhotoEntityKey) obj; 
		//--- Attribute idAlbum
		if ( idAlbum == null ) { 
			if ( other.idAlbum != null ) 
				return false ; 
		} else if ( ! idAlbum.equals(other.idAlbum) ) 
			return false ; 
		//--- Attribute idPhoto
		if ( idPhoto == null ) { 
			if ( other.idPhoto != null ) 
				return false ; 
		} else if ( ! idPhoto.equals(other.idPhoto) ) 
			return false ; 
		return true; 
	} 


    //----------------------------------------------------------------------
    // hashCode METHOD
    //----------------------------------------------------------------------
	public int hashCode() { 
		final int prime = 31; 
		int result = 1; 
		
		//--- Attribute idAlbum
		result = prime * result + ((idAlbum == null) ? 0 : idAlbum.hashCode() ) ; 
		//--- Attribute idPhoto
		result = prime * result + ((idPhoto == null) ? 0 : idPhoto.hashCode() ) ; 
		
		return result; 
	} 


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(idAlbum); 
		sb.append("|"); 
		sb.append(idPhoto); 
        return sb.toString();
    }
}
