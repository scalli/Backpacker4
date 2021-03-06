/**
 * Bean with properties that can be mapped to the corresponding database table.
 */
package org.backpacker4.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class Feedback implements Serializable {

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
    private Long idTypeinfo;

    @NotNull
    private Long idPosition;

    @Size( max = 2147483647 )
    private String comment;

    @NotNull
    private Date datefeedback;



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

    public void setIdTypeinfo( Long idTypeinfo ) {
        this.idTypeinfo = idTypeinfo;
    }
    public Long getIdTypeinfo() {
        return this.idTypeinfo;
    }

    public void setIdPosition( Long idPosition ) {
        this.idPosition = idPosition;
    }
    public Long getIdPosition() {
        return this.idPosition;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }
    public String getComment() {
        return this.comment;
    }

    public void setDatefeedback( Date datefeedback ) {
        this.datefeedback = datefeedback;
    }
    public Date getDatefeedback() {
        return this.datefeedback;
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
        sb.append(idTypeinfo);
        sb.append("|");
        sb.append(idPosition);
        // attribute 'comment' not usable (type = String Long Text)
        sb.append("|");
        sb.append(datefeedback);
        return sb.toString(); 
    } 


}
