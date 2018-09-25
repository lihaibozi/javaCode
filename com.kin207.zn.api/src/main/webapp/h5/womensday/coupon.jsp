<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jstl.jsp" %>
<%@include file="/common/basePath.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimal-ui">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>LA特卖会</title>
    <script src="js/flexible.js"></script>
    <link rel="stylesheet" href="scss/reset.min.css">
    <link rel="stylesheet" href="scss/coupon.min.css">
    

</head>
<body>
    <div id="main">
        <div class="logo"></div>
        <div class="ticket">
            <div class="money"></div>
            <div class="phonebox">
                <input class="phone-input" type="tel" name="iphone" id ="iphone" placeholder="请输入手机号" >
                <!-- <em class="placeholder show">请输入手机号</em> -->
            </div>
            <span class="error-text hide" >请输入正确的手机号</span>
            <span class="error-text-check " >您已经参与请留意短信通知</span>
            <div class="btn-box " id="btnbox" >
                <span class="get-btn" ></span>
            </div>
            <em class="dashed"></em>
            <div class="rule-wrapper">
                <p class="rule-title">抵用券使用规则</p>
                <p>1、凭此券购买衣服立减100元（劲爆价99元商品除外）；</p>
                <p>2、此券不可叠加使用，每人仅限领取1张；</p>
                <p>3、抵用券有效期：2017年3月5日-2017年4月12日；</p>
                <p>4、咨询热线：<span class="phone-num">156 2655 9188</span> ／ <span class="phone-num"> 186 2039 3613</span>；</p>
                <p>5、地址：深圳市南山区蛇口中心路卓越维港北区大新银行旁</p>
                <p>6、本活动最终使用权归深圳市云裳盛宴贸易有限公司所有</p>
            </div>
        </div>
        <a href="#" class="to-intro">LA服装会所简介 >></a>
    </div>

    <div class="intro-wrapper">
        <div class="container">
            <button class="closebtn"></button>
            <img src="imgs/LOGO-3.png" >
            <div class="intro-text"></div>
        </div>
    </div>
    <script src="js/main.js"></script>
    
</body>
</html>

