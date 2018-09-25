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
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/init.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/shujufenbu.css" />
</head>
<body>
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
                    <img src="${pageContext.request.contextPath}/h5/img/shuju/per_name.png" />
 					 <span>${returnMap.nickName}</span>
                </span>
        </div>
    </div>

    <div class="graph">
        <div class="graph_main" id="graph_main"></div>
        <div class="graph_detail">
            <p>${returnMap.total_strike}</p>
            <p>击球总数</p>
        </div>
    </div>

    <div class="time">
        <div class="time_main">
            <div class="time_top">
                <div class="one">
                    <img src="${pageContext.request.contextPath}/h5/img/shuju/time.png" />
                    <div>
                        <span>${returnMap.duration}</span>
                        <span>运动时长(min)</span>
                    </div>
                </div>
                <div class="two">
                    <img src="${pageContext.request.contextPath}/h5/img/shuju/kcal.png" />
                    <div>
                        <span>${returnMap.calorie}</span>
                        <span>消耗热量(kCal)</span>
                    </div>
                </div>
            </div>
            <div class="time_bottom">
                <div class="one first">
                    <span class="speed">${returnMap.best_serve}<span>km/h</span></span>
                    <span>最快发球速度</span>
                </div>
                <div class="one">
                    <span class="speed">${returnMap.best_strike}<span>km/h</span></span>
                    <span>最快击球速度</span>
                </div>
                <div class="one last">
                    <span class="speed">${returnMap.avg_strike}<span>km/h</span></span>
                    <span>平均击球拍速</span>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
    var mychar = echarts.init(document.getElementById('graph_main'));
	
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
            
        },
        series: [{
                name:'详情',
                type:'pie',
                radius: ['40%', '55%'],
                label:{
                	normal:{
                		formatter: "{c}\n{b}"
                	}
                }, 
                data:[
                    {value:'${returnMap.best_serve}', name:'平击',
                        label:{
                            normal :{
                                textStyle : {
                                    color : '#fff'
                                }
                            }
                        }
                    },
                    
                    {value:'${returnMap.best_serve}', name:'截击',
                        label:{
                            normal :{
                                textStyle : {
                                    color : '#fff'
                                }
                            }
                        }
                    },
                    {value:'${returnMap.best_serve}', name:'上旋',
                        label:{
                            normal :{
                                textStyle : {
                                    color : '#fff'
                                }
                            }
                        }
                    },
                    {value:'${returnMap.best_serve}', name:'切削',
                        label:{
                            normal :{
                                textStyle : {
                                    color : '#fff'
                                }
                            }
                        }
                    },
                    {value:'${returnMap.best_serve}', name:'发球',
                        label:{
                            normal :{
                                textStyle : {
                                    color : '#fff'
                                }
                            }
                        }
                    },
                    {value:'${returnMap.best_serve}', name:'高压球',
                        label:{
                            normal :{
                                textStyle : {
                                    color : '#fff'
                                }
                            }
                        }
                    }
                ]
            }
        ],
        color : ['#FDCA11', '#45FD93','#78D424','#831CF8','#FD5658','#1F7FD5']
    };
    //配置图表设置并读取
    mychar.setOption(option);
    
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