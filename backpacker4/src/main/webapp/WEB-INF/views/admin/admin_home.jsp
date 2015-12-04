<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!-- File generated by Telosys Tools Generator ( version 2.1.1 ) - Date 2015-10-28 ( Time 11:00:54 ) -->

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
                <li><a href="/backpacker4/admin/feedback">Your feedback</a></li>
                <li><a href="/backpacker4/admin/search/form">Search</a></li>
                <li><a href="/backpacker4/admin/places">Where are we?</a></li>
                <li ><a href="/backpacker4/admin/list">Who are we?</a></li>
                <li><a href="/backpacker4/register/form/${appuser.id}">Profile</a></li>
            </ul>
        </div>
    </nav>
</div>

<h1>This is the admin homepage</h1>
<h2>Welcome ${username}</h2>
</div>

