<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jstl.jsp" %>
<%@include file="../../../common/basePath.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>悟牛小编</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
    <style type="text/css">
        *{ margin: 0; padding:0;}
        body{ font-family: "黑体";}
        /*布局模块*/
        section.container{ width:100%; margin-bottom: 1em; }
        /*时间*/
        .container div.layout{
            width:90%; margin:1em auto; text-align:center;
        }
        .container ._inner_timer{
            width:20%; border:0; border-bottom:1px solid #eee;
            display: inline-block;
            vertical-align: middle;
        }
        .container ._line_timer{
            width:30%; display: inline-block;
            text-align: center;
            color:#ccc; font-size:0.7em;
            margin:0 3%;
        }
        /*内容*/
        .container .main{
            width:93%; margin: 0 auto; border:1px solid #ccc;
            -webkit-border-radius:2px 2px 2px;
            -moz-border-radius:2px 2px 2px;
            border-radius:2px 2px 2px;
            padding:2% 0 0 0; background:#eee;
        }
        .container .main ._content_main{ width: 95%; margin:0 auto;}
        /*标题*/
        .container .main ._title_main{ color:#888; }
        /*图片*/
        .container .main ._img_main{  width:100%;  height:10em; margin:0.5em 0;  }
        .container .main ._img_main img{
            width:100%;  height: 100%;
            -webkit-border-radius:2px 2px 2px;
            -moz-border-radius:2px 2px 2px;
            border-radius:2px 2px 2px;
        }
        /*说明*/
        .container .main ._detail_main{ color:#ccc; margin-bottom:0.6em; }
        /*查看全文*/
        .container .main ._footer_main{
            border-top:1px solid #ccc; width:96%;
            padding:0.5em 2%; height: auto; line-height:100%;
        }
        .container .main ._footer_main span{ display: inline-block; vertical-align: middle;}
        .container .main ._footer_main span:last-child{float:right;}
        a{text-decoration:none;}
    </style>
</head>
<body>
	
	
	
	
	 <section class="container">
	 <c:forEach items="${activityList }" var="act">
	 <div>
        <div class="time layout">
            <p class="_inner_timer"></p>
            <p class="_line_timer">${act.startTime}</p>
            <p class="_inner_timer"></p>
        </div>
        <div class="main">
        	 <a href='${act.link}' >
            <div class="_content_main">
                <p class="_title_main">${act.name}</p>
                <div class="_img_main">
                    <img src="${act.image}">
                </div>
                <p class="_detail_main">${act.depict}</p>
            </div>
            <div class="_footer_main">
                <span>查看全文</span>
                <span>></span>
            </div>
            </a>
        </div>
        
        </div>
      </c:forEach>
      
    </section>
	
</body>
</html>