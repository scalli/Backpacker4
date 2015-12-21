/**
 * Bean with properties that can be mapped to the corresponding database table.
 */
package org.backpacker4.bean;

import java.io.Serializable;

import javax.validation.constraints.*;


public class UserRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Integer iduserRoles;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @NotNull
    @Size( min = 1, max = 45 )
    private String userrole;

    @NotNull
    private Long userid;



    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setIduserRoles( Integer iduserRoles ) {
        this.iduserRoles = iduserRoles ;
    }

    public Integer getIduserRoles() {
        return this.iduserRoles;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    public void setUserrole( String userrole ) {
        this.userrole = userrole;
    }
    public String getUserrole() {
        return this.userrole;
    }

    public void setUserid( Long userid ) {
        this.userid = userid;
    }
    public Long getUserid() {
        return this.userid;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(iduserRoles);
        sb.append("|");
        sb.append(userrole);
        sb.append("|");
        sb.append(userid);
        return sb.toString(); 
    } 


}
