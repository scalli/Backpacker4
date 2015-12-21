package org.backpacker4.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "feedback"
 *
 */

@Entity
@Table(name="feedback", catalog="backpacker1" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="FeedbackEntity.countAll", query="SELECT COUNT(x) FROM FeedbackEntity x" )
} )
public class FeedbackEntity implements Serializable {

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
    @Column(name="COMMENT")
    private String     comment      ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATEFEEDBACK", nullable=false)
    private Date       datefeedback ;

	// "idUser" (column "ID_USER") is not defined by itself because used as FK in a link 
	// "idTypeinfo" (column "ID_TYPEINFO") is not defined by itself because used as FK in a link 
	// "idPosition" (column "ID_POSITION") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne 
    @JoinColumn(name="ID_USER", referencedColumnName="ID")
    private AppuserEntity appuser     ;

    @ManyToMany(targetEntity=PhotoEntity.class)
    @JoinTable(name="feedback_photo", 
      joinColumns=@JoinColumn(name="ID_FEEDBACK", referencedColumnName="ID"),
      inverseJoinColumns=@JoinColumn(name="ID_PHOTO", referencedColumnName="ID")
     ) 

    private List<PhotoEntity> listOfPhoto ;

    @ManyToOne
    @JoinColumn(name="ID_TYPEINFO", referencedColumnName="ID")
    private TypeinfoEntity typeinfo    ;

    @ManyToOne
    @JoinColumn(name="ID_POSITION", referencedColumnName="ID")
    private PositionEntity position    ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public FeedbackEntity() {
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
    //--- DATABASE MAPPING : COMMENT ( LONGTEXT ) 
    public void setComment( String comment ) {
        this.comment = comment;
    }
    public String getComment() {
        return this.comment;
    }

    //--- DATABASE MAPPING : DATEFEEDBACK ( DATETIME ) 
    public void setDatefeedback( Date datefeedback ) {
        this.datefeedback = datefeedback;
    }
    public Date getDatefeedback() {
        return this.datefeedback;
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

    public void setListOfPhoto( List<PhotoEntity> listOfPhoto ) {
        this.listOfPhoto = listOfPhoto;
    }
    public List<PhotoEntity> getListOfPhoto() {
        return this.listOfPhoto;
    }

    public void setTypeinfo( TypeinfoEntity typeinfo ) {
        this.typeinfo = typeinfo;
    }
    public TypeinfoEntity getTypeinfo() {
        return this.typeinfo;
    }

    public void setPosition( PositionEntity position ) {
        this.position = position;
    }
    public PositionEntity getPosition() {
        return this.position;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        // attribute 'comment' not usable (type = String Long Text)
        sb.append(datefeedback);
        return sb.toString(); 
    } 

}
