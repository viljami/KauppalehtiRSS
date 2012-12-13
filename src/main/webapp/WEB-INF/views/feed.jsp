<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
	<title>Kauppalehti RSS</title>
	<link href="../resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<link href="../resources/css/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="hero-unit">
	<h1>Kauppalehti RSS</h1>
	<p>Four RSS feeds listed.</p>
</div>

<div id="content">
<c:forEach items="${feedItems}" var="v_feedItem">

	<div class="media">
	  	<a class="pull-left" href="${v_feedItem.link}" >
	    	<img class="media-object" data-src="holder.js/64x64">
	  	</a>
	  	<div class="media-body">
			<h4 class="media-heading"><a href="${v_feedItem.link}" >${v_feedItem.title}</a></h4>
			<p>${v_feedItem.category}</p>
			<p>${v_feedItem.description}</p>
		</div>
	</div>
</c:forEach>
</div>

</body>
</html>
