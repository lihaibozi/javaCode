<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<div id="screen_cover" style="position: fixed; top: 0; bottom: 0; left: 0; right: 0; z-index: 9999; background-color: #000; opacity: 0.6; display: none;">
</div>
<div id="screen_alert" style="background-color: #fff; position: absolute; top: 50%; left: 50%; margin: -68px 0 0 -135px; width: 270px; height: 149px; z-index: 10000; border-radius: 10px; display: none;">
	<div id="screen_alert_title" style="display: block; text-align: center; padding: 20px 10px 10px 10px; line-height: 28px; font-size: 18px; color: #616672;">Amaze UI</div>
	<div id="screen_alert_content" style="display: block; text-align: center; padding: 0 10px 20px 10px; font-size: 20px; color: #616672;">Hello World Biu</div>
	<button onclick="Dialog.hide();" style="position: absolute; bottom: 0; width: 100%; line-height: 44px; padding: 0; font-size: 20px; background-color: #fff; border: none; border-top: 1px solid #e3e3e3; border-radius: 0 0 10px 10px; color: #15171c;">确 定</button>
</div>

<script type="text/javascript">
	var Dialog = {
		clickCB: null,
		alert: function(title, content, clickCB){
			
			if (clickCB != undefined && clickCB != null){
				Dialog.clickCB = clickCB;
			}
			
			var $cover = $("#screen_cover");
			var $alert = $("#screen_alert");
			var $title = $alert.find("#screen_alert_title");
			var $content = $alert.find("#screen_alert_content");
			
			$title.html(title);
			$content.html(content);
			
			$cover.show();
			$alert.show();
		},
		hide: function(){
			var $cover = $("#screen_cover");
			var $alert = $("#screen_alert");
			
			$cover.hide();
			$alert.hide();
			
			if (Dialog.clickCB != undefined && Dialog.clickCB != null){
				Dialog.clickCB();
			}
		}
	};
</script>