<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Title : ${title}</h1>
	<h1>Message : ${message}</h1>

	<div class="col-sm-offset-4 col-sm-2">
		<!-- "ADMIN LOGIN" button ( HREF link ) -->
		<a role="button" class="btn btn-default btn-block" href="admin/home">ADMIN</a>
	</div>
	
	<div class="col-sm-offset-4 col-sm-2">
		<!-- "USER LOGIN" button ( HREF link ) -->
		<a role="button" class="btn btn-default btn-block" href="user/home">USER</a>
	</div>
	
	<div class="col-sm-offset-4 col-sm-2">
		<!-- "USER LOGIN" button ( HREF link ) -->
		<a role="button" class="btn btn-default btn-block" href="index">OVERZICHT</a>
	</div>
	
	<div class="col-sm-offset-4 col-sm-2">
		<!-- "CREATE" button ( HREF link ) -->
		<a role="button" class="btn btn-default btn-block" href="register/form">REGISTER</a>
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

		<c:if test="${pageContext.request.userPrincipal.name != null}">
			<h2>
				User : ${pageContext.request.userPrincipal.name} | <a
					href="javascript:formSubmit()"> Logout</a>
			</h2>
		</c:if>


	</sec:authorize>
</body>
</html>