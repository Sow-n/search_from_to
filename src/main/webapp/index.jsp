<%-- 
    Document   : index
    Created on : Dec 9, 2021, 6:09:47 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        if (from == null && to == null) {
            from = "";
            to = "";
        }
    %>
    <body>
        <h1>Search doctor information by age !</h1>
        <p style="color: red">${errorMessage}</p>
        <form action="DoctorControllerServlet" method="GET">
            From: <input type="search" name="from" value="<%= from%>"/>
            To: <input type="search" name="to" value="<%= to%>"/>
            <button>Search</button>
        </form>
        <br/>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Age</th>
                <th>Address</th>
            </tr>
            <c:forEach var="doctor" items="${DOCTOR_LIST}">
                <tr>
                    <td>${doctor.id}</td>
                    <td>${doctor.name}</td>
                    <td>
                        ${doctor.age}
                    </td>
                    <td>
                        ${doctor.address}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>