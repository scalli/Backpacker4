/**
 * Bean with properties that can be mapped to the corresponding database table.
 */
package org.backpacker4.bean;

import java.io.Serializable;

import javax.validation.constraints.*;


public class AlbumPhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long idAlbum;
    @NotNull
    private Long idPhoto;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    


    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setIdAlbum( Long idAlbum ) {
        this.idAlbum = idAlbum ;
    }

    public Long getIdAlbum() {
        return this.idAlbum;
    }

    public void setIdPhoto( Long idPhoto ) {
        this.idPhoto = idPhoto ;
    }

    public Long getIdPhoto() {
        return this.idPhoto;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------

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
