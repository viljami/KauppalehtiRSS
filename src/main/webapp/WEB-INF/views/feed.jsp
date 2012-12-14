<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
	<title>Kauppalehti RSS</title>
	<link href="../resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
	<!--   <link href="../resources/css/styles.css" rel="stylesheet" type="text/css" /> -->
</head>
<body>

<header class="container">
	<nav id="nav" >
		<div class="navbar navbar-fixed-top">
			<div class="navbar-inner">
	    		<a class="brand" href="#">KauppalehtiRSS</a>
		
				<ul class="nav nav-pills">
		  			<li><a href="#footer">To Bottom</a></li>
		  			<li><a href="#nav">Back Up</a></li>
				</ul>
			</div>
		</div>
	</nav>
</header>

<div class="hero-unit">
	<h1>Kauppalehti RSS</h1>
	<p>Four RSS feeds listed.</p>
</div>


<div id="content" class="container">
<c:forEach items="${feedItems}" var="v_feedItem">

	<div class="media">
	<!-- 
	  	<a class="pull-left" href="${v_feedItem.link}" >
	    	<img class="media-object" src="${v_feedItem.imageURL}"/>
	  	</a>
	 -->
	  	<div class="media-body">
			<h4 class="media-heading"><a href="${v_feedItem.link}" >${v_feedItem.title}</a></h4>
			<blockquote>
				<p>${v_feedItem.description}
					<small>${v_feedItem.category} - ${v_feedItem.date} </small>
				</p>
			</blockquote>
		</div>
	</div>
</c:forEach>
</div>

<footer id="footer" >
	<address>
		Viljami Peltola<br>
		<a href="mailto:webengineer@viljamipeltola.com">webengineer@viljamipeltola.com</a>
	</address>
</footer>

</body>
</html>
