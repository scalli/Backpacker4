
package org.backpacker4.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.*;

import java.util.Date;

public class FeedbackReadable implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    
    private Long id;
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    

	private String username;

    private String typeinfo;

    private BigDecimal latitude;

    private BigDecimal longitude;

	private String country;
    
	private String city;

    private String comment;

    private Date datefeedback;
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTypeinfo() {
		return typeinfo;
	}

	public void setTypeinfo(String typeinfo) {
		this.typeinfo = typeinfo;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDatefeedback() {
		return datefeedback;
	}

	public void setDatefeedback(Date datefeedback) {
		this.datefeedback = datefeedback;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



   


}
