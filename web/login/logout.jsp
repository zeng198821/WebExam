<%-- 
    Document   : logout
    Created on : 2016-3-19, 11:27:27
    Author     : zeng
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%= session.getAttribute("empId") %> 已退出
        <% 
            session.setAttribute("empId", null); 
            response.sendRedirect(request.getContextPath()+"/login/login.jsp");
        %>
        
    </body>
</html>
