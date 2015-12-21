/**
 * Bean with properties that can be mapped to the corresponding database table.
 */
package org.backpacker4.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Album implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    private Long idUser;

    @NotNull
    @Size( min = 1, max = 100 )
    private String albumname;

    @Size( max = 2147483647 )
    private String comment;

    @NotNull
    private Date datealbum;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Long id ) {
        this.id = id ;
    }

    public Long getId() {
        return this.id;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setIdUser( Long idUser ) {
        this.idUser = idUser;
    }
    public Long getIdUser() {
        return this.idUser;
    }

    public void setAlbumname( String albumname ) {
        this.albumname = albumname;
    }
    public String getAlbumname() {
        return this.albumname;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }
    public String getComment() {
        return this.comment;
    }

    public void setDatealbum( Date datealbum ) {
        this.datealbum = datealbum;
    }
    public Date getDatealbum() {
        return this.datealbum;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(idUser);
        sb.append("|");
        sb.append(albumname);
        // attribute 'comment' not usable (type = String Long Text)
        sb.append("|");
        sb.append(datealbum);
        return sb.toString(); 
    } 


}
