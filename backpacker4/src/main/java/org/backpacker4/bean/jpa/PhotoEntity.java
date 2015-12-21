package org.backpacker4.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "photo"
 *
 */

@Entity
@Table(name="photo", catalog="backpacker1" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="PhotoEntity.countAll", query="SELECT COUNT(x) FROM PhotoEntity x" )
} )
public class PhotoEntity implements Serializable {

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
    @Column(name="DESCRIPTION", length=100)
    private String     description  ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATETAKEN")
    private Date       datetaken    ;

    @Column(name="COMMENT")
    private String     comment      ;

    @Column(name="FULLPHOTO", nullable=false, length=1000)
    private String     fullphoto    ;

    @Column(name="THUMBNAIL", nullable=false, length=1000)
    private String     thumbnail    ;

	// "idPosition" (column "ID_POSITION") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToMany(mappedBy="listOfPhoto", targetEntity=AlbumEntity.class)
    private List<AlbumEntity> listOfAlbum ;

    @ManyToOne
    @JoinColumn(name="ID_POSITION", referencedColumnName="ID")
    private PositionEntity position    ;

    @ManyToMany(mappedBy="listOfPhoto", targetEntity=FeedbackEntity.class)
    private List<FeedbackEntity> listOfFeedback;

    @OneToMany(mappedBy="photo", targetEntity=AppuserEntity.class)
    private List<AppuserEntity> listOfAppuser;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public PhotoEntity() {
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
    //--- DATABASE MAPPING : DESCRIPTION ( VARCHAR ) 
    public void setDescription( String description ) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    //--- DATABASE MAPPING : DATETAKEN ( DATETIME ) 
    public void setDatetaken( Date datetaken ) {
        this.datetaken = datetaken;
    }
    public Date getDatetaken() {
        return this.datetaken;
    }

    //--- DATABASE MAPPING : COMMENT ( LONGTEXT ) 
    public void setComment( String comment ) {
        this.comment = comment;
    }
    public String getComment() {
        return this.comment;
    }

    //--- DATABASE MAPPING : FULLPHOTO ( VARCHAR ) 
    public void setFullphoto( String fullphoto ) {
        this.fullphoto = fullphoto;
    }
    public String getFullphoto() {
        return this.fullphoto;
    }

    //--- DATABASE MAPPING : THUMBNAIL ( VARCHAR ) 
    public void setThumbnail( String thumbnail ) {
        this.thumbnail = thumbnail;
    }
    public String getThumbnail() {
        return this.thumbnail;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfAlbum( List<AlbumEntity> listOfAlbum ) {
        this.listOfAlbum = listOfAlbum;
    }
    public List<AlbumEntity> getListOfAlbum() {
        return this.listOfAlbum;
    }

    public void setPosition( PositionEntity position ) {
        this.position = position;
    }
    public PositionEntity getPosition() {
        return this.position;
    }

    public void setListOfFeedback( List<FeedbackEntity> listOfFeedback ) {
        this.listOfFeedback = listOfFeedback;
    }
    public List<FeedbackEntity> getListOfFeedback() {
        return this.listOfFeedback;
    }

    public void setListOfAppuser( List<AppuserEntity> listOfAppuser ) {
        this.listOfAppuser = listOfAppuser;
    }
    public List<AppuserEntity> getListOfAppuser() {
        return this.listOfAppuser;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(description);
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
