
package org.backpacker4.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "appuser"
 *
 */

@Entity
@Table(name="appuser", catalog="backpacker1" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="AppuserEntity.countAll", query="SELECT COUNT(x) FROM AppuserEntity x" )
} )
public class AppuserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long       id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="FIRSTNAME", nullable=false, length=100)
    private String     firstname    ;

    @Column(name="LASTNAME", nullable=false, length=100)
    private String     lastname     ;

    @Column(name="EMAIL", nullable=false, length=150)
    private String     email        ;

    @Column(name="USERNAME", nullable=false, length=50)
    private String     username     ;

    @Column(name="USERPASSWORD", nullable=false, length=50)
    private String     userpassword ;

	// "idPhoto" (column "ID_PHOTO") is not defined by itself because used as FK in a link 
	// "idPosition" (column "ID_POSITION") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="ID_POSITION", referencedColumnName="ID")
    private PositionEntity position    ;

    @OneToMany(mappedBy="appuser", targetEntity=UserRolesEntity.class)
    private List<UserRolesEntity> listOfUserRoles;

    @ManyToOne
    @JoinColumn(name="ID_PHOTO", referencedColumnName="ID")
    private PhotoEntity photo       ;

    @OneToMany(mappedBy="appuser", targetEntity=AlbumEntity.class)
    private List<AlbumEntity> listOfAlbum ;

    @OneToMany(mappedBy="appuser", targetEntity=FeedbackEntity.class, cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FeedbackEntity> listOfFeedback;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public AppuserEntity() {
		super();
    }
    
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
    //--- DATABASE MAPPING : FIRSTNAME ( VARCHAR ) 
    public void setFirstname( String firstname ) {
        this.firstname = firstname;
    }
    public String getFirstname() {
        return this.firstname;
    }

    //--- DATABASE MAPPING : LASTNAME ( VARCHAR ) 
    public void setLastname( String lastname ) {
        this.lastname = lastname;
    }
    public String getLastname() {
        return this.lastname;
    }

    //--- DATABASE MAPPING : EMAIL ( VARCHAR ) 
    public void setEmail( String email ) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    //--- DATABASE MAPPING : USERNAME ( VARCHAR ) 
    public void setUsername( String username ) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

    //--- DATABASE MAPPING : USERPASSWORD ( VARCHAR ) 
    public void setUserpassword( String userpassword ) {
        this.userpassword = userpassword;
    }
    public String getUserpassword() {
        return this.userpassword;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setPosition( PositionEntity position ) {
        this.position = position;
    }
    public PositionEntity getPosition() {
        return this.position;
    }

    public void setListOfUserRoles( List<UserRolesEntity> listOfUserRoles ) {
        this.listOfUserRoles = listOfUserRoles;
    }
    public List<UserRolesEntity> getListOfUserRoles() {
        return this.listOfUserRoles;
    }

    public void setPhoto( PhotoEntity photo ) {
        this.photo = photo;
    }
    public PhotoEntity getPhoto() {
        return this.photo;
    }

    public void setListOfAlbum( List<AlbumEntity> listOfAlbum ) {
        this.listOfAlbum = listOfAlbum;
    }
    public List<AlbumEntity> getListOfAlbum() {
        return this.listOfAlbum;
    }

    public void setListOfFeedback( List<FeedbackEntity> listOfFeedback ) {
        this.listOfFeedback = listOfFeedback;
    }
    public List<FeedbackEntity> getListOfFeedback() {
        return this.listOfFeedback;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(firstname);
        sb.append("|");
        sb.append(lastname);
        sb.append("|");
        sb.append(email);
        sb.append("|");
        sb.append(username);
        sb.append("|");
        sb.append(userpassword);
        return sb.toString(); 
    } 

}
