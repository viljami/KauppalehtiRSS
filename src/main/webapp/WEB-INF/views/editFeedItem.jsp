<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Edit</title>
</head>
<body>
<h1>
	Editing feedItem ${feedItem.id}  
</h1>
<form:form commandName="feedItem" style="padding:8px">
    ID - ${feedItem.id}<br/>
    <p>
        Category<br/>
        <form:input path="category"/>
    </p>
    <p>
        Title<br/>
        <form:input path="title"/>
    </p>
      <p>
        Description<br/>
        <form:input path="description"/>
    </p>
    <p>
        Date<br/>
        <form:input path="date"/>
    </p>
    <p>
        Link<br/>
        <form:input path="link"/>
    </p>
    <input type="submit" value="Save"/>
</form:form>

</body>
</html>
