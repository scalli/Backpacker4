
package org.backpacker4.bean.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "position"
 *
 */

@Entity
@Table(name="position", catalog="backpacker1" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="PositionEntity.countAll", query="SELECT COUNT(x) FROM PositionEntity x" )
} )
public class PositionEntity implements Serializable {

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
    @Column(name="LATITUDE")
    private BigDecimal latitude     ;

    @Column(name="LONGITUDE")
    private BigDecimal longitude    ;
    
    @Column(name="COUNTRY")
    private String country     ;

    @Column(name="CITY")
    private String city    ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="position", targetEntity=PhotoEntity.class)
    private List<PhotoEntity> listOfPhoto ;

    @OneToMany(mappedBy="position", targetEntity=FeedbackEntity.class)
    private List<FeedbackEntity> listOfFeedback;

    @OneToMany(mappedBy="position", targetEntity=AppuserEntity.class)
    private List<AppuserEntity> listOfAppuser;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public PositionEntity() {
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
    //--- DATABASE MAPPING : LATITUDE ( DECIMAL ) 
    public void setLatitude( BigDecimal latitude ) {
        this.latitude = latitude;
    }
    public BigDecimal getLatitude() {
        return this.latitude;
    }

    //--- DATABASE MAPPING : LONGITUDE ( DECIMAL ) 
    public void setLongitude( BigDecimal longitude ) {
        this.longitude = longitude;
    }
    public BigDecimal getLongitude() {
        return this.longitude;
    }

  //--- DATABASE MAPPING : COUNTRY ( VARCHAR ) 
    public void setCountry( String country ) {
        this.country = country;
    }
    public String getCountry() {
        return this.country;
    }
    
  //--- DATABASE MAPPING : CITY ( VARCHAR ) 
    public void setCity( String city ) {
        this.city = city;
    }
    public String getCity() {
        return this.city;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfPhoto( List<PhotoEntity> listOfPhoto ) {
        this.listOfPhoto = listOfPhoto;
    }
    public List<PhotoEntity> getListOfPhoto() {
        return this.listOfPhoto;
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
        sb.append(latitude);
        sb.append("|");
        sb.append(longitude);
        sb.append("|");
        sb.append(country);
        sb.append("|");
        sb.append(city);
        return sb.toString(); 
    } 

}
