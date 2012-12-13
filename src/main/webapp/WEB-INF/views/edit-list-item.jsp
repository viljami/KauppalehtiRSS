<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Edit</title>
</head>
<body>
<h1>
	Editing feedItem ${listItem.id}  
</h1>
<form:form commandName="listItem" style="padding:8px">
    ID - ${listItem.id}<br/>
    <p>
        Title<br/>
        <form:input path="title"/>
    </p>
    <p>
        Date<br/>
        <form:input path="date"/>
    </p>
    <input type="submit" value="Save"/>
</form:form>

</body>
</html>
