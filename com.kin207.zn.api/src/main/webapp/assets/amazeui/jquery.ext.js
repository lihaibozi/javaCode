(function($){
	$.UI = {
		scrollToBottom: function(containerId){
    		var body;
    		if(containerId == undefined){
    			body = $("body")[0];
    		}else{
    			body = $("#" + containerId)[0];
    		}
    		body.scrollTop = body.scrollHeight;
    	},
		load: function($a, callBack){
			var $div = $("#" + $a.attr("target"));
    		$div.html("");
    		var url = $a.attr("link") == "" ? $a.attr("href") : $a.attr("link");
    		$.HTTP.getHtml(url, function(html){
    			if(callBack != undefined && $.isFunction(callBack)){
					callBack(html);
				}else{
					$div.html(html);
				}
    		});
		},
		loadMore: function($a, callBack){
			$a.show();
			var pageNum = $a.attr("pageNum") == "" ? 1 : parseInt($a.attr("pageNum")) + 1;
			var url = $a.attr("link") == "" ? $a.attr("href") : $a.attr("link");
			if(url.indexOf("?") > 0){
				url = url + "&pageNum=" + pageNum;
			}else{
				url = url + "?pageNum=" + pageNum;
			}
			$.HTTP.getHtml(url, function(html){
				setTimeout(function(){
					$a.hide();
					if(callBack != undefined && $.isFunction(callBack)){
						if(html.trim() != ""){
							$a.attr("pageNum", pageNum);
						}
						callBack(html);
						return;
					}
					if(html.trim() == ""){
						$a.hide();
						return;
					}
					var $target = $("#" + $a.attr("target"));
					$target.append(html);
					$a.attr("pageNum", pageNum);
				}, 500);
			});
		},
		subForm: function(formId){
			$("#" + formId).submit();
		},
		scrollLoad: function($Link){
			var params = {isLoading: false, $a: $Link};
			function setLoaded(){
				params.isLoading = false;
			};
			function setLoading(){
				params.isLoading = true;
			}
			function isLoading(){
				return params.isLoading;
			}
			function loadData(){
				
			}
			function load(){
				if($(document).scrollTop() + $(window).height() >= $(document).height()){
					console.log("is loading = " + params.isLoading);
					if(!params.isLoading){
			    		setLoading();
			    		console.log("load start");
			    		var $a = params.$a;
			    		$.UI.loadMore($a, function(html){
		    				if(html.trim() != ""){
		    					var $target = $("#" + $a.attr("target"));
		    					$target.append(html);
		    					$target.find(".scrollLoad").scrollLoading();
		    					
		    					var callback = $a.attr("callback");
		    					
		    					if(callback != undefined){
		    						if (!$.isFunction(callback)){
		    							var $cb = eval('(' + callback + ')');
		    							$cb($a);
		    						}else{
		    							callback.call($a, $a);
		    						}
		    					}
		    				}
		    				setTimeout(function(){
		    					console.log("load finish");
		    					setLoaded();
	    					}, 300);
		    			});
		    		}
			    }
			}
			window.onscroll = load;
		},
		initSelectTabs:function(tabsSelector, selectedClass){
			$(tabsSelector).click(function(e){
				var $me = $(e.target);
				var $p = $me.parent();
				$p.find(tabsSelector).removeClass(selectedClass);
				$me.addClass(selectedClass);
				var callback = $me.attr("callback");
				if(callback != undefined){
					if (!$.isFunction(callback)){
						eval('(' + callback + ')');
					}else{
						callback.call($me, $me);
					}
				}
			});
		}
	};
	
	$.EVENT = {
    	submit: function(formId){
    		$("#" + formId).submit();
    	}
	};
	$.HTTP = {
		open: function(obj){
			var $me = $(obj);
			var url = $me.attr("href") || $me.attr("link");
			window.open(url); 
		},
		getHtml: function(url, callBack){
			$.HTTP._ajax('get', url, {}, 'html', callBack);
		},
		getJson: function(url, callBack){
			$.HTTP._ajax('get', url, {}, 'json', callBack);
		},
		subForm: function(form, callBack){
			var $form = $(form);
			var url = $form.attr("action");
			var data = $form.serializeArray();
			$.HTTP._ajax('post', url, data, 'json', callBack);
			return false;
		},
		_ajax: function(type, url, data, dataType, callBack){
			$.ajax({
				type: type,
				url: url,
				data: data,
				dataType: dataType,
				success: function(response){
					if(callBack != undefined && $.isFunction(callBack)){
						callBack(response);
					}
				}
			});
		}
	};
	
	$.CB = {
			reload: function(json){
				if(json.statusCode == 200){
					window.location.reload(false);
				}else{
					alert(json.msg);
				}
			}
		};
	
	$.Component = {
		pairStatus: function(selector, container){
			var $es = container == undefined ? $(selector) : $(container).find(selector);
			$es.click(function(event){
				event.preventDefault();
				var $e = $(this);
				
				if($e.attr("disable") != undefined){
					return;
				}
				
				var currentStatus = $e.attr("display");
				var $p = $e.parent();
				$p.find(selector).each(function(){
					var $me = $(this);
					if($me.attr("display") == currentStatus){
						$me.hide();
						$me.attr("display", 'false');
					}else{
						$me.show();
						$me.attr("display", 'true');
					}
				});
				var callback = $e.attr("callback");
				if(callback != undefined){
					if(!$.isFunction(callback)){
						callback = eval('(' + callback + ')');
					}else{
						callback.call($e, $e);
					}
				}
				$.HTTP.getJson($e.attr("href"), callback);
			});
			$es.each(function(){
				var $e = $(this);
				$e.hide();
				if($e.attr("display") == 'true'){
					$e.show();
				}
			});
		}	
	};
	
	$.Browser = {
		isPC: function(){
			var userAgentInfo = navigator.userAgent;
		    var Agents = ["Android", "iPhone",
		                "SymbianOS", "Windows Phone",
		                "iPad", "iPod"];
		    var flag = true;
		    for (var v = 0; v < Agents.length; v++) {
		        if (userAgentInfo.indexOf(Agents[v]) > 0) {
		            flag = false;
		            break;
		        }
		    }
		    return flag;
		},
		isAndroid: function(){
			var u = navigator.userAgent;
			u.indexOf('Android') > -1 || u.indexOf('Linux') > -1
		},
		isIOS: function(){
			var u = navigator.userAgent;
			return !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/)
		},
		isIPhone: function(){
			var u = navigator.userAgent;
			return u.indexOf('iPhone') > -1
		},
		isIPad: function(){
			var u = navigator.userAgent;
			return u.indexOf('iPad') > -1;
		},
		isWeiXin: function(){
		    var ua = window.navigator.userAgent.toLowerCase();
		    return ua.match(/MicroMessenger/i) == 'micromessenger';
		},
		isQQ: function(){
			var ua = window.navigator.userAgent.toLowerCase();
			return ua.match(/QQ\//i) == "qq/";
		}
	};
})(jQuery);

var TabBar = {
	tabs: [],
	current: null,
	init: function(tabBarId, clickTabCB){
		var This = TabBar;
		This.tabs = $("#" + tabBarId + " .tab-item");
		This.current = $("#" + tabBarId + " .tab-item:first");
		This.tabs.click(function(e){
			var $me = $(this);

			var $pre = This.current;
			if ($pre.attr("data-area") == $me.attr("data-area")) {
				return;
			}
			
			This.current = $me;
			This.tabs.each(function(){
				$(this).removeClass("active");
				This.hideArea($(this));
			});
			$me.addClass("active");
			This.showArea($me);
			
			if(clickTabCB != undefined){
				clickTabCB(This.tabs, $me);
			}
		});
	},
	hideArea: function(tab){
		var areaId = $(tab).attr("data-area");
		$("#" + areaId).hide();
	},
	showArea: function(tab){
		var areaId = $(tab).attr("data-area");
		$("#" + areaId).show();
	}
};
