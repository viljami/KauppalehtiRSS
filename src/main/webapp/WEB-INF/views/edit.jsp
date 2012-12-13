<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Edit</title>
</head>
<body>
<h1>
	Editing feedItem ${person.id}  
</h1>
<form:form commandName="person" style="padding:8px">
    ID - ${person.id}<br/>
    <p>
        Header<br/>
        <form:input path="firstName"/>
    </p>
    <p>
        Topic<br/>
        <form:input path="lastName"/>
    </p>
    <input type="submit" value="Save"/>
</form:form>
</body>
</html>
