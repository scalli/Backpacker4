package org.backpacker4.bean.jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Composite primary key for entity "FeedbackPhotoEntity" ( stored in table "feedback_photo" )
 *
 */
 @Embeddable
public class FeedbackPhotoEntityKey implements Serializable {
    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY KEY ATTRIBUTES 
    //----------------------------------------------------------------------
    @Column(name="ID_FEEDBACK", nullable=false)
    private Long       idFeedback   ;
    
    @Column(name="ID_PHOTO", nullable=false)
    private Long       idPhoto      ;
    

    //----------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------
    public FeedbackPhotoEntityKey() {
        super();
    }

    public FeedbackPhotoEntityKey( Long idFeedback, Long idPhoto ) {
        super();
        this.idFeedback = idFeedback ;
        this.idPhoto = idPhoto ;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    //----------------------------------------------------------------------
    public void setIdFeedback( Long value ) {
        this.idFeedback = value;
    }
    public Long getIdFeedback() {
        return this.idFeedback;
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
		FeedbackPhotoEntityKey other = (FeedbackPhotoEntityKey) obj; 
		//--- Attribute idFeedback
		if ( idFeedback == null ) { 
			if ( other.idFeedback != null ) 
				return false ; 
		} else if ( ! idFeedback.equals(other.idFeedback) ) 
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
		
		//--- Attribute idFeedback
		result = prime * result + ((idFeedback == null) ? 0 : idFeedback.hashCode() ) ; 
		//--- Attribute idPhoto
		result = prime * result + ((idPhoto == null) ? 0 : idPhoto.hashCode() ) ; 
		
		return result; 
	} 


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(idFeedback); 
		sb.append("|"); 
		sb.append(idPhoto); 
        return sb.toString();
    }
}
