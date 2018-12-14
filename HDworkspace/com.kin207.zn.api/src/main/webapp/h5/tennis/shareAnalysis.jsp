<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@include file="../../../common/jstl.jsp" %>
<%@include file="../../../common/basePath.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=1.0; user-scalable=no;"/>
    <title>我在悟牛的网球成绩</title>
    <script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/echarts.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/init.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/grade.css" />
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
                <span >免费下载</a></span>
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

    <div class="graph">
        <div class="graph_con">
            <div class="graph_main" id="graph_main"></div>
            <div class="graph_detail">
                <p>${returnMap.total_score}</p>
                <p>综合得分</p>
            </div>
        </div>
    </div>


    <div class="graph graph_pai">
        <div class="graph_con">
            <div class="graph_main" id="graph_pai"></div>
            <div class="grade_main">
                <div class="cheng_detail">
                    <div class="bar"></div>
                    <div class="bar bar_active" style="width:${ratioMap.total_score}%;"></div>
                    <p class="first-child-p">初级</p>
                    <p class="second-child-p">中级</p>
                    <p>高级</p>
                    <span class="sperator1"></span>
                    <span class="sperator2"></span>
                </div>
            </div>

            <div class="graph_detail">
                <p style="font-size: 0.08rem;">您的表现比以往${ratioMap.leadNum}场更棒,请继续保持!</p>
                <p>本场发挥</p>
            </div>
        </div>
    </div>


    <!-- <div class="graph graph_gui" style="margin-top: 0;">
        <div class="graph_con">
            <div class="graph_main" id="graph_gui"></div>
            <div class="graph_detail">
                <p></p>
                <p>成长轨迹</p>
            </div>
        </div>
    </div> -->


</div>
<script type="text/javascript">
	
    var _grade = echarts.init(document.getElementById('graph_main'));
    var _pai = echarts.init(document.getElementById('graph_pai'));
    //var _gui = echarts.init(document.getElementById('graph_gui'));

    var option = {
        tooltip: {
            show : false
        },
        radar: {
        	nameGap : 1,
            // shape: 'circle',
            indicator: [
                { name: '速度${returnMap.speed_score}'+'/100', max: 100},	// 图例
                { name: '${returnMap.sweetspot_score}'+'/100\n'+'甜点', max: 100},
                { name: '${returnMap.spin_score}'+'/100\n'+'旋转', max: 100}
            ],
            splitNumber : 4,
            //radius : 90,
            name: {
                formatter:'{value}',
                textStyle: {
                    color:'white'
                }
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(0, 0, 0, 0.4)'
                }
            },
            splitLine: {
                lineStyle: {
                    color: 'rgba(0, 0, 0, 0.4)'
                }
            }
        },
        series: [{
            name: '预算',
            type: 'radar',
            // areaStyle: {normal: {}},
            lineStyle : {
                normal : {
                    color : 'red'
                }
            },
            data : [{
                value : ['${returnMap.speed_score}', '${returnMap.sweetspot_score}', '${returnMap.spin_score}'],	// 鼠标点击图标显示
                name : '预算分配',
                symbolSize : 0,
                //symbolOffset : [0, '100%'],
                areaStyle : {
                    normal: {
                        color: '#D25A66'
                    }
                },
                lineStyle : {
                    normal : {
                        width : 0
                    }
                }
            }]
        }]
    };
    //配置图表设置并读取
  //配置图表设置并读取
    _grade.setOption(option);

    var _result = [], _resultAll = [];
    (function setCharData(u, b, p){
        var xVals = [];
        for( var i=0; i<=100; i++ ){
            xVals.push('$i');	// 字符串
        }

        var yVals1 = [],
            yVals2 = [],
            value,
            factor = Math.pow(2 *Math.PI, 0.5) * b;

        var sumP = 0.0, higglightIndex = 0;

        for(i=0; i<=2*(u-1); i++){
            value = Math.exp(0 - Math.pow((i - u), 2.0) / (2 * Math.pow(b, 2.0))) / factor;
            yVals1.push([value, i]);	// 所有值
            sumP += value;

            if(sumP < p){	// 待画出的值
                var entry = [value, i];	// y , x
                entry.data = sumP;
                yVals2.push(entry);
                higglightIndex = i;
            }
        }
         var i=0,len=yVals1.length;
        for(i=0;i<len;i++){
        	 _resultAll.push(yVals1[i][0]);
        }
        i=0,len = yVals2.length;
        for(i;i<len;i++){
        	_result.push(yVals2[i][0]);	
        } 

        /* for(let [key, value] of yVals1){
            _resultAll.push(key);
        }

        for(let [key, value] of yVals2){
            _result.push(key);
        } */
    })(50,30, '${ratioMap.ratio}');
    option = {
            //这里的是采用什么方式触发数据，这里显示的竖轴，跟随鼠标移动到节点上会显示内容
            tooltip: {
                trigger: 'axis',
                show : false
            },
            //代表x轴，这里type类型代表字符
            xAxis: [{
                type: 'category',
                boundaryGap: false,//这里表示是否补齐空白
                data: ['', '', ''],
                splitLine:{lineStyle:{color:'rgba(255,255,255,0.2)', type:'dashed'}, show:true}
            }],
            //代表y轴，这里type类型代表num类型
            yAxis: [{
                type: 'value',
                axisLine:{lineStyle:{color:'rgba(255,255,255,0.2)', type:'dashed'}},
                splitLine:{lineStyle:{color:'rgba(255,255,255,0.2)', type:'dashed'}},
                axisLabel : {show:false}
            }],
            //图表类型，type表示按照什么类型图表显示，line代表折线，下面的内容与
            //legend一一对应
            series: [{
                name: '',
                type: 'line',
                smooth: true,
                lineStyle : {normal:{opacity:0}},
                itemStyle: {
                    normal: {
                        color : 'rgba(0,0,0,0)',
                        opacity : 0
                    }
                },
                areaStyle: {
                    normal:{
                        color : 'rgb(103, 95, 127)'
                    }
                },
                data: _resultAll
            },{
                name: '',
                type: 'line',
                smooth: true,
                lineStyle : {normal:{opacity:0}},
                itemStyle: {
                    normal: {
                        color : 'rgba(0,0,0,0)',
                        opacity : 0
                    }
                },
                areaStyle: {
                    normal:{
                        color : 'rgba(253,202,17, 1)',
                    }
                },
                data: _result
            }]
        };
        _pai.setOption(option);


    option = {
        tooltip : {
            trigger: 'axis'
        },
        color : ['#FDCA11', '#2892BD', '#5DD658', '#FD5658'],
        legend: {
            itemWidth:14,
            itemHeight:10,
            data : [{
                name : '甜点',
                icon : 'circle'
            },{
                name : '旋转',
                icon : 'circle'
            },{
                name : '速度',
                icon : 'circle'
            },{
                name : '分数',
                icon : 'circle'
            }],
            //data:['邮件营销','联盟广告','视频广告','直接访问'],
            textStyle : {
                color : 'white',
                fontSize : 12
            },
            top : '5%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['周一','周二','周三','周四','周五','周六'],
                splitLine:{show:true,lineStyle:{color:'rgba(255,255,255,0.2)', type:'dashed'}},
                axisLine:{lineStyle:{color:'rgba(255,255,255,0.4)', type:'dashed'}}
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLine:{lineStyle:{color:'rgba(255,255,255,0.4)', type:'dashed'}},
                splitLine:{lineStyle:{color:'rgba(255,255,255,0.2)', type:'dashed'}}
            }
        ],
        series : [
            {
                name:'甜点',
                type:'line',
                smooth: true,
                stack: '总量',
                //lineStyle : {normal:{color:'rgba(255,255,255,0.4)', type:'dashed', width:1}},
                areaStyle: {normal: {color:'rgba(0,0,0,0)'}},
                data:[12, 32, 10, 13, 9, 10]
            },
            {
                name:'旋转',
                type:'line',
                smooth: true,
                stack: '总量',
                areaStyle: {normal: {color:'rgba(0,0,0,0)'}},
                data:[22, 18, 19, 23, 29, 33]
            },
            {
                name:'速度',
                type:'line',
                smooth: true,
                stack: '总量',
                areaStyle: {normal: {color:'rgba(0,0,0,0)'}},
                data:[15, 23, 20, 15, 19, 33]
            },
            {
                name:'分数',
                type:'line',
                smooth: true,
                stack: '总量',
                areaStyle: {normal: {color:'rgba(0,0,0,0)'}},
                data:[32, 33, 30, 33, 39, 33]
            }
        ]
    };
    
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