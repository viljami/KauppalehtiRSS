<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Edit</title>
</head>
<body>
<h1>
	Editing feedItem ${feedItem.id} - ${feedItem.header}  ${feedItem.topic} 
</h1>
<form:form commandName="feedItem" style="padding:8px">
    ID - ${feedItem.id}<br/>
    <p>
        Header<br/>
        <form:input path="header"/>
    </p>
    <p>
        Topic<br/>
        <form:input path="topic"/>
    </p>
    <input type="submit" value="Save"/>
</form:form>
</body>
</html>
