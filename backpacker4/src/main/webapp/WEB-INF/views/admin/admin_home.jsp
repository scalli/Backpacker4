<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<div xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:c="http://java.sun.com/jsp/jstl/core"
     xmlns:s="http://www.springframework.org/tags" xmlns:form="http://www.springframework.org/tags/form" 
     xmlns:util="urn:jsptagdir:/WEB-INF/tags/util" xmlns:input="urn:jsptagdir:/WEB-INF/tags/input" 
     version="2.0">
<jsp:directive.page contentType="text/html;charset=UTF-8"/>

<div class="bs-example">
    <nav role="navigation" class="navbar navbar-default">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="#" class="navbar-brand">Brand</a>
        </div>
        <!-- Collection of nav links and other content for toggling -->
        <div id="navbarCollapse" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="/backpacker4/admin/home">Home</a></li>
                <li><a href="/backpacker4/admin/feedback1">Your feedback</a></li>
                <li><a href="/backpacker4/admin/search/form">Search</a></li>
                <li><a href="/backpacker4/admin/places">Where are we?</a></li>
                <li ><a href="/backpacker4/admin/list">Who are we?</a></li>
                <li><a href="/backpacker4/admin/form?appuserid=${appuser.id}">Profile</a></li>
            </ul>
        </div>
    </nav>
</div>

<h1>This is the admin homepage</h1>
<h2>Welcome ${username}</h2>
<input id="appuserid" value="${appuser.id}" type="hidden"/>
<input id="latitude"  type="hidden"/>
<input id="longitude"  type="hidden"/>
<input id="country"  type="hidden"/>
<input id="city"  type="hidden"/>
</div>

<script>
window.onload = getLocation1;

function getLocation1() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition2, showError);
    } else {
        x.innerHTML = "Geolocation is not supported by this browser.";
    }
}

function showPosition2(position)
{  
    //GLOBAL VARIABLE
	geocoder = new google.maps.Geocoder();

	var lat = position.coords.latitude;
    var lng = position.coords.longitude;
    $( "latitude" ).val( lat );
    $( "longitude" ).val( lng );
	//GLOBAL VARIABLE!
    latlng = new google.maps.LatLng(lat, lng);
	
    var appuserid = $("#appuserid").val();
	
    getCountry1(lat,lng,appuserid,latlng, geocoder);
}

function showError(error) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            x.innerHTML = "User denied the request for Geolocation."
            break;
        case error.POSITION_UNAVAILABLE:
            x.innerHTML = "Location information is unavailable."
            break;
        case error.TIMEOUT:
            x.innerHTML = "The request to get user location timed out."
            break;
        case error.UNKNOWN_ERROR:
            x.innerHTML = "An unknown error occurred."
            break;
    }
}
</script>

<script type="text/javascript">
//<![CDATA[
//Get the country and city from the latitude and longitude
function getCity1 (lat,lng,country,appuserid,latlng, geocoder) {
// 	var latlng = new google.maps.LatLng(lat, lng);
// 	geocoder = new google.maps.Geocoder();
	geocoder.geocode({'latLng': latlng}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        console.log("results : " + results);
        if (results[1]) {
         //formatted address
//          alert(results[0].formatted_address);
        //find country name
         for (var i=0; i < results[0].address_components.length; i++) {
            for (var b=0;b < results[0].address_components[i].types.length;b++) {
            //there are different types that might hold a city admin_area_lvl_1 usually does in come cases looking for sublocality type will be more appropriate
                if (results[0].address_components[i].types[b] == "locality") {
                    //this is the object you are looking for
                    var city1= results[0].address_components[i];
                    var city = city1.long_name;
//                     alert("city:" + city.long_name);
                    doAjaxPost(lng,lat,country,city,appuserid); 
                }
            }
        }
        //city data
//         alert("city: " + city.short_name + " " + city.long_name + " country: " + country.short_name + " " + country.long_name);
        } else {
          alert("No results found");
          return null;
        }
      } else {
        alert("Geocoder failed due to: " + status);
        return null;
      }
    });
  }
//]]>
</script>

<script type="text/javascript">
//<![CDATA[
//Get the country and city from the latitude and longitude
function getCountry1(lat,lng,appuserid,latlng, geocoder) {
// 	var latlng = new google.maps.LatLng(lat, lng);
// 	geocoder = new google.maps.Geocoder();
	geocoder.geocode({'latLng': latlng}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        console.log("results : " + results);
        if (results[1]) {
         //formatted address
//          alert(results[0].formatted_address);
        //find country name
         for (var i=0; i < results[0].address_components.length; i++) {
            for (var b=0;b < results[0].address_components[i].types.length;b++) {
               if (results[0].address_components[i].types[b] == "country") {
                    //this is the object you are looking for
                    var country1= results[0].address_components[i];
                    var country = country1.long_name;
//                     alert("country:" + country.long_name);
                    getCity1(lat,lng,country,appuserid,latlng, geocoder); 
                }
            }
        }
        //city data
//         alert("city: " + city.short_name + " " + city.long_name + " country: " + country.short_name + " " + country.long_name);
        } else {
          alert("No results found");
          return null;
        }
      } else {
        alert("Geocoder failed due to: " + status);
        return null;
      }
    });
  }
//]]>
</script>

<script>
	function doAjaxPost(lng,lat,country,city,appuserid) {

	var params = "longitude="+lng + "&latitude=" + lat + "&country=" + country + "&city=" + city + "&appuserid=" + appuserid;
// 	alert("params: " + params);
	$.ajax({
        type: "GET",
        url: "/backpacker4/Ajax/SaveLocation",
        data: params,
        success: function(response){
        // we have the response
//         alert("response:" + response);
        if (response == false) {
//                alert("failed");
           }
        else {
//         	alert("succes")
		}
			 },
		error: function(e){
		alert('Error: ' + e);
        }
			});
	
}
 </script>

