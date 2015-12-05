<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/nl_NL/sdk.js#xfbml=1&version=v2.5";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	</script>
	<script>window.twttr = (function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0],
	    t = window.twttr || {};
	  if (d.getElementById(id)) return t;
	  js = d.createElement(s);
	  js.id = id;
	  js.src = "https://platform.twitter.com/widgets.js";
	  fjs.parentNode.insertBefore(js, fjs);
	 
	  t._e = [];
	  t.ready = function(f) {
	    t._e.push(f);
	  };
	 
	  return t;
	}(document, "script", "twitter-wjs"));
		</script>
	
	<div class="container">
		<h1>Backpackworld</h1>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="col-sm-4">
				Welcome to Backpackworld! 
				<p>
				For backpackers, by backpackers... You'll find lots of feedback on different backpack
				locations. Do not hestitate to share your own experiences with other backpackers!
				</p>
			</div>
		</div>
		<div class="row">
			<div class="bphome"></div>
		</div>
	</div>
	
	<div class="container">
		<div class="row">
			<div class="col-sm-4">
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Login as administrator</h3>
				  </div>
				  <div class="panel-body">
				    <!-- "ADMIN LOGIN" button ( HREF link ) -->
					<a role="button" class="btn btn-default btn-block" href="admin/home">ADMIN</a>

				  </div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-default">
				  <div class="panel-heading">
				    <h3 class="panel-title">Login as user</h3>
				  </div>
				  <div class="panel-body">
				    <!-- "USER LOGIN" button ( HREF link ) -->
					<a role="button" class="btn btn-default btn-block" href="user/home">USER</a>
				  </div>
				</div>
			</div>
			<div class="col-sm-4">
					<div class="panel panel-default">
					  <div class="panel-heading">
					    <h3 class="panel-title">Register as new user</h3>
					  </div>
					  <div class="panel-body">
					   <!-- "CREATE" button ( HREF link ) -->
						<a role="button" class="btn btn-default btn-block" href="register/form">REGISTER</a>
					  </div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="fb-like" data-href="http://localhost:1979/backpacker4/" data-layout="standard" data-action="like" data-show-faces="true" data-share="true"></div>
			</div>
			<div class="row">
			<p>&nbsp;</p>
			</div>
			<div class="row">
				<a class="twitter-share-button"
				  href="https://twitter.com/intent/tweet?text=Backpackworld"
				  data-size="large">
				Tweet</a>
			</div>
		</div>
		

	<sec:authorize access="hasRole('ROLE_USER')">
		<!-- For login user -->
		<c:url value="/j_spring_security_logout" var="logoutUrl" />
		<form action="${logoutUrl}" method="post" id="logoutForm">
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form>
		<script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
		</script>




	</sec:authorize>
</body>
</html>