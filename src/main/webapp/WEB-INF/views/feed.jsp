<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Kauppalehti RSS</title>
</head>
<body>
<h1>Listing Feed</h1>
<c:forEach items="${feedItems}" var="v_feedItem">
	<a href="edit?id=${v_feedItem.id}">${v_feedItem.id} - ${v_feedItem.date},  ${v_feedItem.title} - ${v_feedItem.description} - ${v_feedItem.link}</a>
	<br />
</c:forEach>
<a href="editFeedItem"> Add feedItem</a>
</body>
</html>
