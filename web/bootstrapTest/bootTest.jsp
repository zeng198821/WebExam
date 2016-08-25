<%-- 
    Document   : bootTest
    Created on : 2016-3-26, 14:08:02
    Author     : zeng
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BootStrap Test</title>
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/extend.js" ></script>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/bootstrap.min.css" type="text/css" ></link>
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/bootstrap.min.js" /></script>
</head>
<body>
    <button id="outtest" type="button" class="btn btn-lg btn-danger"  data-container="body" data-toggle="popover" >点我弹出/隐藏弹出框</button>
    <table class="table table-hover">
        <tr class="active">
            <td class="active" data-container="body" data-toggle="popover">Boot</td>
            <td class="success" data-container="body" data-toggle="popover">Strap</td>
            <td class="warning" >nothing</td>
        </tr>
    </table> 

</body>
<script>
    $(function () {
        var tmpobj = $('[data-toggle="popover"]');
        var tmphtml = '<form id="loginform" action="com.zeng.org.LoginCheck.checklogout.ext"  method="get"  enctype="multipart/form-data" field="user">        <div class="form-group">            <label for="exampleInputName2">用户名：</label>            <input class="form-control" type="text" field="userName" name="userName" placeholder="用户名"/>        </div> <button type="button" class="btn btn-success" aria-label="Left Align">        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>    </button>    <button type="button" class="btn  btn-danger" aria-label="Left Align">        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>    </button>   </form>';
        var tmpdata = {placement: "auto", trigger: "click", html: true, content: tmphtml};
        tmpobj.popover(tmpdata);
        //tmpobj.setContent("122333");
        regEvent();
    });
    function  regEvent() {
        $(".table").bind("click", function () {
            alert($(this).text());
        });

    }
    function SuperType(name) {
        alert("SuperType name:" + name );
        this.name = name;
        this.colors = ["red", "blue", "green"];
    }
    SuperType.prototype.sayName = function () {
        alert(this.name);
    };
    function SubType(name, age) {
        SuperType.call(this, name); //第二次调用 SuperType()
        this.age = age;
    }
    SubType.prototype = new SuperType(); //第一次调用 SuperType()
    SubType.prototype.zeng="ss";
    SubType.prototype.constructor = SubType;
    SubType.prototype.sayAge = function () {
        alert(this.age);
    };
    var tt = new  SubType("tts",23);
    tt.sayAge();
    var aa = new  SubType("aas",23);
    aa.prototype.zeng="bb";
    console.log(tt.zeng);
    console.log(aa.zeng);





</script>
</html>
