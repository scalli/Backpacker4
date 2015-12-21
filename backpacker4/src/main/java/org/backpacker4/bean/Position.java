/**
 * Bean with properties that can be mapped to the corresponding database table.
 */
package org.backpacker4.bean;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.math.BigDecimal;

public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @NotNull
    private Long id;

    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    

    private BigDecimal latitude;


    private BigDecimal longitude;


	private String country;
    
    
	private String city;


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
    public void setLatitude( BigDecimal latitude ) {
        this.latitude = latitude;
    }
    public BigDecimal getLatitude() {
        return this.latitude;
    }

    public void setLongitude( BigDecimal longitude ) {
        this.longitude = longitude;
    }
    public BigDecimal getLongitude() {
        return this.longitude;
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
    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
 
        public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        sb.append(id);
        sb.append("|");
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
