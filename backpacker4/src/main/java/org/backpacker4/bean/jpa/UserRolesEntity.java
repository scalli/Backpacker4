package org.backpacker4.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;


import javax.persistence.*;

/**
 * Persistent class for entity stored in table "user_roles"
 */

@Entity
@Table(name="user_roles", catalog="backpacker1" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="UserRolesEntity.countAll", query="SELECT COUNT(x) FROM UserRolesEntity x" )
} )
public class UserRolesEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="iduser_roles", nullable=false)
    private Integer    iduserRoles  ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="USERROLE", nullable=false, length=45)
    private String     userrole     ;

	// "userid" (column "USERID") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="USERID", referencedColumnName="ID")
    private AppuserEntity appuser     ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public UserRolesEntity() {
		super();
    }
    
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
    //--- DATABASE MAPPING : USERROLE ( VARCHAR ) 
    public void setUserrole( String userrole ) {
        this.userrole = userrole;
    }
    public String getUserrole() {
        return this.userrole;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setAppuser( AppuserEntity appuser ) {
        this.appuser = appuser;
    }
    public AppuserEntity getAppuser() {
        return this.appuser;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(iduserRoles);
        sb.append("]:"); 
        sb.append(userrole);
        return sb.toString(); 
    } 

}
