package org.backpacker4.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "album"
 *
 */

@Entity
@Table(name="album", catalog="backpacker1" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="AlbumEntity.countAll", query="SELECT COUNT(x) FROM AlbumEntity x" )
} )
public class AlbumEntity implements Serializable {

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
    @Column(name="ALBUMNAME", nullable=false, length=100)
    private String     albumname    ;

    @Column(name="COMMENT")
    private String     comment      ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATEALBUM", nullable=false)
    private Date       datealbum    ;

	// "idUser" (column "ID_USER") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="ID_USER", referencedColumnName="ID")
    private AppuserEntity appuser     ;

    @ManyToMany(targetEntity=PhotoEntity.class)
    @JoinTable(name="album_photo", 
      joinColumns=@JoinColumn(name="ID_ALBUM", referencedColumnName="ID"),
      inverseJoinColumns=@JoinColumn(name="ID_PHOTO", referencedColumnName="ID")
     ) 

    private List<PhotoEntity> listOfPhoto ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public AlbumEntity() {
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
    //--- DATABASE MAPPING : ALBUMNAME ( VARCHAR ) 
    public void setAlbumname( String albumname ) {
        this.albumname = albumname;
    }
    public String getAlbumname() {
        return this.albumname;
    }

    //--- DATABASE MAPPING : COMMENT ( LONGTEXT ) 
    public void setComment( String comment ) {
        this.comment = comment;
    }
    public String getComment() {
        return this.comment;
    }

    //--- DATABASE MAPPING : DATEALBUM ( DATETIME ) 
    public void setDatealbum( Date datealbum ) {
        this.datealbum = datealbum;
    }
    public Date getDatealbum() {
        return this.datealbum;
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


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append("["); 
        sb.append(id);
        sb.append("]:"); 
        sb.append(albumname);
        // attribute 'comment' not usable (type = String Long Text)
        sb.append("|");
        sb.append(datealbum);
        return sb.toString(); 
    } 

}
