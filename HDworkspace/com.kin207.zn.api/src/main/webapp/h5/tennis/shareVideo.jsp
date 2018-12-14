<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@include file="/common/jstl.jsp" %>
<%@include file="/common/basePath.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=1.0; user-scalable=no;"/>
    <title>我在悟牛的网球成绩</title>
    <script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/init.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/video.css" />
</head>
<body style="overflow-x:hidden; width:100%;">
	<input type="hidden" id="praise" value="praise"/>
    <div class="container">
        <div class="tip">
            <div class="tip_main">
                <div class="logo">
                    <img src="${pageContext.request.contextPath}/h5/img/logo.png" />
                </div>
                <div>
                    <p>打网球，就用悟牛智能网球</p>
                    <p>更多球友信赖的网球助手神器</p>
                </div>
                <div class="download" id="downLoand">
                    <img src="${pageContext.request.contextPath}/h5/img/download.png" />
                    <span>免费下载</span>
                </div>
            </div>
        </div>

    <div class="personInfo">
	            <span class="per_img">
	                <img src="${returnMap.photo}" />
	            </span>
	        <div class="per_name_con">
	                <span class="per_name">
	                    <img src="${pageContext.request.contextPath}/h5/img/grade/per_name.png" />
	                    <span>${returnMap.nickName}</span>
	                </span>
	        </div>
	    </div>
		
       <div class="video_con" style="overflow-x:hidden;">
            <div class="video">
                <video id="myVideo" src="${returnMap.linkUrl}">
                    	您的浏览器不支持 video 标签。
                </video>
                <img class="video-logo" src="${pageContext.request.contextPath}/h5/img/video/video.png" >
                <span class="play" id="videoBtn">
  <%--                   <img class="video-playbg" src="${pageContext.request.contextPath}/h5/img/video/playbg.png" >
                    <img class="video-play" src="${pageContext.request.contextPath}/h5/img/video/play.png" > --%>
                </span>
            </div>
            <div class="detail">
                <div class="time">
                    <img src="${pageContext.request.contextPath}/h5/img/video/time.png" />
                    <span>${returnMap.time}</span>
                </div>
                <div class="address" style="width:0.85rem;">
                    <%-- <img src="${pageContext.request.contextPath}/h5/img/video/address.png" />
                    <span>深圳市南山区</span> --%>
                </div>
                <div class="add_click">
<%--                     <img src="${pageContext.request.contextPath}/h5/img/video/add_click.png" id = "img"/>
                    <span id="spanid">${praises}</span> --%>
                </div>
            </div>
        </div>

        <div class="grade_con">
            <div class="grade_main">
                <div class="grade_top">
                    <p>本拍得分</p>
                    <p>${returnMap.total_score}</p>
                </div>
                <div class="grade_bottom">
                    <div class="g_rotate">
                        <span>旋转</span>
                        <span>${returnMap.spin_score}分</span>
                    </div>
                    <div class="g_rotate">
                        <span>速度</span>
                        <span>${returnMap.speed_score}分</span>
                    </div>
                    <div class="g_rotate">
                        <span>甜点</span>
                        <span>${returnMap.sweetspot_score}分</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="grade_con cheng_con">
            <div class="grade_main">
                <div class="cheng_top">本拍打出的成绩</div>
                <div class="cheng_detail">
                    <div class="bar"></div>
                    <div class="bar bar_active" style="width:${returnMap.total_score}%;"></div>
                    <p>初级</p>
                    <p>中级</p>
                    <p>高级</p>
                    <span class="sperator1"></span>
                    <span class="sperator2"></span>
                </div>
            </div>
        </div>

    </div>

<script type="text/javascript">
	    $(function(){
	    	    $('#downLoand').click(function(){
		       	var u = navigator.userAgent;
		    	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		    	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		    	if(isAndroid){
		    		window.open("http://www.baidu.com",'_blank'); 
		    	}
		    	if(isiOS){
		    		window.open("http://www.ikin207.com",'_blank'); 
		    	}
	    	});
	        var $videoBtn = $('#videoBtn'),
	            $video = $('#myVideo'),
	            video = $video.get(0);
	        isPlay = false, isReady=false;
	        video.oncanplay = function(){
	            isReady = true;
	        };
	        video.onpause = function(){
	            isPlay = false;
	        };
	        video.onplay = function(){
	          isPlay = true;
	        };
	        video.onended = function(){
	            video.currentTime = 0;
	            $videoBtn.show();
	        };
	        $video.click(function(){
	            if(isPlay){
	                video.pause();
	                $videoBtn.show();
	            }
	        });
	        $videoBtn.click(function(){
	            $videoBtn.hide();
	            if(isReady){
	                video.play();
	            }
	        });
	    });
	    
</script>
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
			title: '我的战绩',
			desc: '我已经成为优秀的人',
			link: '${url}',
			imgUrl: 'http://ikin207.oss-cn-shenzhen.aliyuncs.com/avatar/76.jpg',
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