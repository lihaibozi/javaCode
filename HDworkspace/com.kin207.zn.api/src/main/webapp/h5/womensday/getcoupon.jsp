<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jstl.jsp" %>
<%@include file="/common/basePath.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimal-ui">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>LA特卖会</title>
    <script src="js/flexible.js"></script>
    <link rel="stylesheet" href="css/reset.min.css">
    <link rel="stylesheet" href="css/getcoupon.min.css">
</head>
<body>
    <div id="main">
        <div class="logo"></div>
        <div class="ticket">
            <img class="ok" src="imgs/ok.svg" >
            <span>优惠已发送手机，凭短信立减100元</span>
            <img class="erweima" src="imgs/erweima.png" alt="LA专卖店二维码">
            <b>长按识别，了解更多</b>
            <strong>淘宝不等双十一，LA特卖有新衣</strong>
        </div>
        <a href="javascript:;" class="to-intro">LA服装会所简介 >></a>
    </div>

    <div class="intro-wrapper">
        <div class="container">
            <button class="closebtn"></button>
            <img src="imgs/LOGO-3.png" >
            <div class="intro-text"></div>
        </div>
    </div>

    <script src="js/getcoupon.js"></script>
</body>
</html>
