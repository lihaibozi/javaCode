function checkNull(name,title,rName){
		var value = $("#"+name).val().replace(/^\s+|\s+$/g,"");
		if(value==null||value==""){
			$("#"+rName).tips({
				side:3,
	            msg:title,
	            bg:'#AE81FF',
	            time:2
	        });
			return true;
		}else{
			return false;
		}
	}

