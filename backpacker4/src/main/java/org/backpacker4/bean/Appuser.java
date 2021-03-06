/**
 * Bean with properties that can be mapped to the corresponding database table.
 */
package org.backpacker4.bean;

import java.io.Serializable;

import javax.validation.constraints.*;


public class Appuser implements Serializable {

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
    @Size( min = 1, max = 100 )
    private String firstname;

    @NotNull
    @Size( min = 1, max = 100 )
    private String lastname;

    @NotNull
    @Size( min = 1, max = 150 )
    private String email;

    @NotNull
    @Size( min = 1, max = 50 )
    private String username;

    @NotNull
    @Size( min = 1, max = 50 )
    private String userpassword;


    private Long idPhoto;


    private Long idPosition;



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
    public void setFirstname( String firstname ) {
        this.firstname = firstname;
    }
    public String getFirstname() {
        return this.firstname;
    }

    public void setLastname( String lastname ) {
        this.lastname = lastname;
    }
    public String getLastname() {
        return this.lastname;
    }

    public void setEmail( String email ) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    public void setUsername( String username ) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

    public void setUserpassword( String userpassword ) {
        this.userpassword = userpassword;
    }
    public String getUserpassword() {
        return this.userpassword;
    }

    public void setIdPhoto( Long idPhoto ) {
        this.idPhoto = idPhoto;
    }
    public Long getIdPhoto() {
        return this.idPhoto;
    }

    public void setIdPosition( Long idPosition ) {
        this.idPosition = idPosition;
    }
    public Long getIdPosition() {
        return this.idPosition;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
        sb.append(firstname);
        sb.append("|");
        sb.append(lastname);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(username);
        sb.append("|");
        sb.append(userpassword);
        sb.append("|");
        sb.append(idPhoto);
        sb.append("|");
        sb.append(idPosition);
        return sb.toString(); 
    } 


}
