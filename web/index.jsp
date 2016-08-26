<%-- 
    Document   : index
    Created on : 2016-1-9, 20:53:09
    Author     : zeng
language="java" contentType="text/html"
--%>

<%@page  contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/extend.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.min.css" type="text/css"></link>
    </head>
    <body>
        <h1><%= session.getAttribute("empId") %> <a href="<%= request.getContextPath() %>/login/logout.jsp">登出</a></h1>  
<!--        <div id="logindiv" style="width:350px;height:200px;padding-left:100px;">
            <form id="loginform" action="hello.ext"  method="get"  enctype="multipart/form-data" field="user">
                <div class="form-group">
                    <label for="exampleInputName2">用户名：</label>
                    <input class="form-control" type="text" field="userName" name="userName" placeholder="用户名"/>
                </div>
                <label>密码：</label><input class="form-control" type="text" field="password" name="password" placeholder="密码"/><br/>
                <input class="btn btn-default" type="button"  value="提交" onclick="getdata()"/>
            </form>
        </div>
        <div id="loginresult"style="width:350px;height:100px;padding-left:100px;"></div>-->
        <script type="text/javascript">
            function getdata(){
                var senddata = zeng.form.getformdata("loginform");
                var sendaction = zeng.form.getformAction("loginform");
                if(!zeng.isEmpty(senddata)){
                    var result = zeng.request.requestAjax(sendaction,senddata);
                    console.log(result);
                    $("#loginresult").text(result);
                }
            }
        </script>
    </body>
</html>
