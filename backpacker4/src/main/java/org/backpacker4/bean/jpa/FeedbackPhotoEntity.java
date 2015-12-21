
package org.backpacker4.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;


import javax.persistence.*;

/**
 * Persistent class for entity stored in table "feedback_photo"
 *
 */

@Entity
@Table(name="feedback_photo", catalog="backpacker1" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="FeedbackPhotoEntity.countAll", query="SELECT COUNT(x) FROM FeedbackPhotoEntity x" )
} )
public class FeedbackPhotoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( EMBEDDED IN AN EXTERNAL CLASS )  
    //----------------------------------------------------------------------
	@EmbeddedId
    private FeedbackPhotoEntityKey compositePrimaryKey ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public FeedbackPhotoEntity() {
		super();
		this.compositePrimaryKey = new FeedbackPhotoEntityKey();       
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE COMPOSITE KEY 
    //----------------------------------------------------------------------
    public void setIdFeedback( Long idFeedback ) {
        this.compositePrimaryKey.setIdFeedback( idFeedback ) ;
    }
    public Long getIdFeedback() {
        return this.compositePrimaryKey.getIdFeedback() ;
    }
    public void setIdPhoto( Long idPhoto ) {
        this.compositePrimaryKey.setIdPhoto( idPhoto ) ;
    }
    public Long getIdPhoto() {
        return this.compositePrimaryKey.getIdPhoto() ;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------

    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        if ( compositePrimaryKey != null ) {  
            sb.append(compositePrimaryKey.toString());  
        }  
        else {  
            sb.append( "(null-key)" ); 
        }  
        sb.append("]:"); 
        return sb.toString(); 
    } 

}
