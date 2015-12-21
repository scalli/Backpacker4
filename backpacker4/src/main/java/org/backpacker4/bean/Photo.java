/**
 * Bean with properties that can be mapped to the corresponding database table.
 */
package org.backpacker4.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Size( max = 100 )
    private String description;

    @NotNull
    private Long idPosition;


    private Date datetaken;

    @Size( max = 2147483647 )
    private String comment;

    @NotNull
    @Size( min = 1, max = 1000 )
    private String fullphoto;

    @NotNull
    @Size( min = 1, max = 1000 )
    private String thumbnail;



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
    public void setDescription( String description ) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public void setIdPosition( Long idPosition ) {
        this.idPosition = idPosition;
    }
    public Long getIdPosition() {
        return this.idPosition;
    }

    public void setDatetaken( Date datetaken ) {
        this.datetaken = datetaken;
    }
    public Date getDatetaken() {
        return this.datetaken;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }
    public String getComment() {
        return this.comment;
    }

    public void setFullphoto( String fullphoto ) {
        this.fullphoto = fullphoto;
    }
    public String getFullphoto() {
        return this.fullphoto;
    }

    public void setThumbnail( String thumbnail ) {
        this.thumbnail = thumbnail;
    }
    public String getThumbnail() {
        return this.thumbnail;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(description);
        sb.append("|");
        sb.append(idPosition);
        sb.append("|");
        sb.append(datetaken);
        // attribute 'comment' not usable (type = String Long Text)
        sb.append("|");
        sb.append(fullphoto);
        sb.append("|");
        sb.append(thumbnail);
        return sb.toString(); 
    } 


}
