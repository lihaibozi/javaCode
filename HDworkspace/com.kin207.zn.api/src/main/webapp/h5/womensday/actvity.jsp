<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jstl.jsp" %>
<%@include file="/common/basePath.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>淘宝不等双十一，LA特卖有新衣</title>
    <script src="js/flexible.js"></script>
    <link rel="stylesheet" href="css/reset.min.css">
    <link rel="stylesheet" href="css/jquery.fullpage.min.css">
    <link rel="stylesheet" href="css/index.min.css">
    
    <!-- <script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.js"></script> -->
    
</head>
<body>
    <div id="main">
        <div class="section">
            <div class="logo rel fadeIn " >
                <img class="pabos" src="imgs/LOGO-1.png" width="100%">
            </div>
            <div class="title rel fadeIn ">
                <img class="pabos" src="imgs/title.svg" width="100%">
            </div>
            <div class="sale rel bounceIn ">
                <img class="pabos" src="imgs/sale.svg" width="100%">
            </div>
            <div class="next fadeIn ">
                <a href="javascript:;">下一页，领取优惠券再买单更优惠～</a>
            </div>
            <div class="arrow arrowMove ">
                <img src="imgs/arrow.svg">
            </div>
        </div>
        <div class="section">
            <div class="cou-logo rel">
                <img class="pabos" src="imgs/LOGO-2.png" alt="">
            </div>
            <div class="ticket">
                <div class="money"></div>
                <div class="phonebox">
                    <input class="phone-input" type="tel" name="phone" placeholder="请输入手机号" >
                </div>
                <div class="text">
                    <span class="error-text">请输入正确的手机号</span>
                    <span class="error-text-check" >您已经参与请留意短信通知</span>
                </div>
                <div class="btn-box">
                    <button class="get-btn able" disabled></button>
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
            <div class="intro-wrapper">
                <div class="container">
                    <button class="closebtn"></button>
                    <img src="imgs/LOGO-3.png" >
                    <div class="intro-text"></div>
                </div>
            </div>
        </div>
    </div>

    <script src="js/jquery-1.12.4.js"></script>
    <script src="js/jquery.fullpage.min.js"></script>
    <script src="js/jquery.fullpage.extensions.min.js"></script>
    <script src="js/main.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  <script>
    wx.config({
		debug: false,
		appId:'${appid}',
		timestamp: '${timestamp}',
		nonceStr:'${nonceStr}',
		signature:'${signature}',

		jsApiList: [
			'checkJsApi',
			'onMenuShareTimeline',
			'onMenuShareAppMessage',
			'onMenuShareQQ',
			'onMenuShareWeibo',
			'hideMenuItems',
			'showMenuItems',
			'hideAllNonBaseMenuItem',
			'showAllNonBaseMenuItem',
			'translateVoice',
			'startRecord',
			'stopRecord',
			'onRecordEnd',
			'playVoice',
			'pauseVoice',
			'stopVoice',
			'uploadVoice',
			'downloadVoice',
			'chooseImage',
			'previewImage',
			'uploadImage',
			'downloadImage',
			'getNetworkType',
			'openLocation',
			'getLocation',
			'hideOptionMenu',
			'showOptionMenu',
			'closeWindow',
			'scanQRCode',
			'chooseWXPay',
			'openProductSpecificView',
			'addCard',
			'chooseCard',
			'openCard'
		]
	});

	wx.ready(function () {
		var shareData = {
			title: '淘宝不等双十一，LA特卖有新衣',
			desc: '3月女神节，100元抵扣券，立减100元',
			link: '${url}',
			imgUrl: 'http://ikin207.oss-cn-shenzhen.aliyuncs.com/la/LOGO-1.png',
			success: function (res) {
				alert("分享快乐");

			},
		};
		wx.onMenuShareAppMessage(shareData);
		wx.onMenuShareAppMessage(shareData);
		wx.onMenuShareTimeline(shareData);
		wx.onMenuShareQQ(shareData);
		wx.onMenuShareWeibo(shareData);
	});
	wx.error(function (res) {
		alert(res.errMsg);
	});
</script>
</body>
</html>
