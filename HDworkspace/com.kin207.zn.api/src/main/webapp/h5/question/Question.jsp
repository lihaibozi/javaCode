<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jstl.jsp" %>
<%@include file="/common/basePath.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
    <style type="text/css">
        *{ margin:0; padding:0; }

        /*底部按钮处理*/
        footer{ position:fixed;
            bottom:0; padding:0.4em 0;
            height:2em; width: 100%; background:#eee;
        }
        footer div{width:49%; text-align: center; padding: 0.5em 0;}
        footer ._footer_left{ float:left; border-right:1px solid #ccc; }
        footer ._footer_right{ float:right; }
        ._footerActive{ color:blue; }

        /*内容区域*/
        .main{ margin-bottom:10em; clear:both; }
        .main div{ padding:0.6em 0.8em; }
        .main ._title{ border-bottom:1px solid #eee; font-size:0.8em; font-weight:bold;}
        .main ._content{ background:#eee; font-size:0.5em; line-height:2em; display:none;}
        .main ._active{ display:block; }

        /*头部设置*/
        header{ padding:0.6em; border-bottom:1px solid #eee;height:auto;}
        header h4{display:inline-block;}
        header img{float:right;}
        header input[type='text']{
            height:18px;
            float:right; border:none;outline-style:none; border-bottom:1px solid #eee;
        }
    </style>
    <script type="text/javascript" src="jquery1.8.3.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("._title").on('click', function(){
                var next = $(this).next();
                if(!next.hasClass("_active")){
                    $(".main").find("._active").slideUp().removeClass("_active");
                    next.slideDown().addClass("_active");
                }
            });

            function doSearch(val){
                clean();
                var titles = $(".main").find("._title:contains("+val+")");
                $.each(titles, function(index,title){
                    title = $(title);
                    var content = title.text(),
                        flagText = content.split(val),
                        i, ln=flagText.length,
                        result = "";
                    for(i=0; i<ln-1; i++){
                        result += flagText[i];
                        var span = "<span style='color:red;'>"+val+"</span>";
                        result += span;
                    }
                    result += flagText[i];
                    title.html(result);

                    if(index===0){
                        var next = title.next();
                        if(!next.hasClass("_active")){
                            $(".main").find("._active").slideUp().removeClass("_active");
                            next.slideDown().addClass("_active");
                        }
                    }
                });
            }

            function clean(){
                var titles = $(".main").find("._title");
                $.each(titles, function(index,title){
                    title = $(title);
                    title.text(title.text());
                });
            }

            var timer;
            $("#search").on("keyup",function(){
                if(timer){
                    clearTimeout( timer );
                }
                var me = $(this),
                        value = me.val();
                timer = setTimeout(function () {
                    doSearch(value);
                }, 1000);
            });
        });
    </script>
</head>
<body>
    <header>
        <h4>常见问题</h4>
        <img src="../img/search.png"/>
        <input type="text" id="search">
    </header>

    <div class="main">
    <c:forEach items="${questionList }" var="qtl" varStatus="ql">
        <div class="_title">${ql.index+1}:${qtl.title}</div>
        <div class="_content _active">${qtl.depict}</div>
    </c:forEach>
    </div>

<!--     <footer class="main_footer">
        <div class="_footer_left _footerActive">常见问题</div>
        <div class="_footer_right">意见反馈</div>
    </footer> -->

</body>
</html>