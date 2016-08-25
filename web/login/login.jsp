<%-- 
    Document   : index
    Created on : 2016-1-9, 20:53:09
    Author     : zeng
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/extend.js" ></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/bootstrap.min.css" type="text/css" ></link>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/bootstrap.min.js" /></script>
    </head>
    <body>
        <button id="outtest" type="button" class="btn btn-lg btn-danger"  data-container="body" data-toggle="popover" >点我弹出/隐藏弹出框</button>
        <table class="table table-hover">
        <tr class="active">
            <td class="active" data-container="body" data-toggle="popover">...</td>
            <td class="success" data-container="body" data-toggle="popover">...</td>
            <td class="warning" >...</td>
        </tr>
        </table>
        
        <h1>Hello World!</h1>
        <div id="logindiv" style="width:350px;height:200px;padding-left:100px;">
            <form id="loginform" action="com.zeng.util.LoginCheck.checklogout.ext"  method="get"  enctype="multipart/form-data" field="user">
                <div class="form-group">
                    <label for="exampleInputName2">用户名：</label>
                    <input class="form-control" type="text" field="userName" name="userName" placeholder="用户名"/>
                </div>
                <label>密码：</label><input class="form-control" type="text" field="password" name="password" placeholder="密码"/><br/>
                <input class="btn btn-default" type="button"  value="提交" onclick="getdata()"/>
            </form>
        </div>
        <div id="loginresult"style="width:350px;height:100px;padding-left:100px;"></div>
<!--        <button id="outtest" type="button" class="btn btn-lg btn-danger" data-toggle="popover" title="Popover title" data-content="And here's some amazing content. It's very engaging. Right?">点我弹出/隐藏弹出框</button>-->
        
        <script type="text/javascript">
            var tmprecall;
            function  setrecallfunc(funcname){
                    tmprecall = funcname;
                
            }
            setrecallfunc(ongetdata);
            
            function getdata(){
                if(tmprecall instanceof Function)
                    tmprecall();
            }
            
            function ongetdata(){
                var senddata = zeng.form.getformdata("loginform");
                var sendaction = zeng.form.getformAction("loginform");
                if(!zeng.isEmpty(senddata)){
                    var result = zeng.request.requestAjax(sendaction,senddata);
                    console.log(result);
                    $("#loginresult").text(result);
                }                
            }
            $(function () {
                var tmpobj = $('[data-toggle="popover"]');
                var tmpdata={placement:"auto",trigger:"click",html : true,content:$("#loginform").html()};
                tmpobj.popover(tmpdata);
                //tmpobj.setContent("122333");
            })
            
        </script>
    </body>
</html>
