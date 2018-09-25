<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<div id="screen_cover" style="position: fixed; top: 0; bottom: 0; left: 0; right: 0; z-index: 9999; background-color: #000; opacity: 0.6; display: none;">
</div>

<div id="dialog_alert_1" style="position: absolute;z-index: 10000;left: 50%;top:50%;margin-left: -150px; margin-top: -180px; display: none;">
	<img class="click-btn" src="assets/images/img_no.png" style="width: 300px;height:364px;;"  >
</div>


<div id="dialog_alert_2" style="position: absolute;z-index: 10000;left: 50%; top:50%; margin-left: -150px; margin-top: -182px; width: 300px; height: 364px; display: none;">
	<img alt="" src="assets/images/dialog_lotter.png" style="width: 100%;"  >
	
	<div id="screen_alert_content" style="position: relative;font-size: 28px;color: rgb(225,242,180);text-align: center;top: -50%;margin-top: -15px;">中奖信息</div>
	
	<a class="click-btn" href="javascript:void(0);">
		<div style="text-align: center; position: relative; bottom: 80px;">
			<img alt="" src="assets/images/btn_confirm.png" style="width: 240px;">
		</div>
	</a>
</div>

<div id="dialog_alert_3" style="position: absolute;z-index: 10000;left: 50%; top:50%; margin-left: -150px; margin-top: -182px; width: 300px; height: 364px; display: none;">
	<img alt="" src="assets/images/dialog_lotter.png" style="width: 100%;"  >
	
	<div id="screen_alert_content" style="position: relative;font-size: 28px;color: rgb(225,242,180);text-align: center;top: -50%;margin-top: -15px;">中奖信息</div>
	
	<a class="click-btn" href="h5/mall/addressAddEdit" target="_blank">
		<div style="text-align: center; position: relative; bottom: 80px;">
			<img alt="" src="assets/images/btn_write.png" style="width: 240px;">
		</div>
	</a>
</div>

<script type="text/javascript">
	var LotteryDialog = {
		clickCB: null,
		$cover: null,
		$alert: null,
		alert: function(alertId, content, clickCB){
			var ME = LotteryDialog;
			
			if (clickCB != undefined && clickCB != null){
				ME.clickCB = clickCB;
			}else {
				ME.clickCB = null;
			}
			
			ME.$cover = $("#" + "screen_cover");
			ME.$alert = $("#" + alertId);
			
			if (content != undefined) {
				ME.$content = ME.$alert.find("#screen_alert_content");
				ME.$content.html(content);
			}
			
			var $clickBtn = ME.$alert.find(".click-btn");
			$clickBtn.click(function(){
				ME.hide();
			});
			
			ME.$cover.show();
			ME.$alert.show();
		},
		hide: function(){
			var ME = LotteryDialog;
			ME.$cover.hide();
			ME.$alert.hide();
			
			if (ME.clickCB != undefined && ME.clickCB != null) {
				ME.clickCB();
			}
		}
	};
</script>