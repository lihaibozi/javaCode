//document.write("<script type='javascript' src=’http://cdn.bootcss.com/jquery/2.1.1/jquery.js’></script>");
$(function() {
    var getCouponBtn = document.getElementsByClassName('get-btn')[0],
        phone = document.getElementsByClassName('phone-input')[0];

    var openIntroBtn = document.getElementsByClassName('to-intro')[0],
        closeIntroBtn = document.getElementsByClassName('closebtn')[0],
        introBox = document.getElementsByClassName('intro-wrapper')[0];

    var ruleWrapper = document.getElementsByClassName( 'rule-wrapper' )[0];

    // 添加动画
    openIntroBtn.onclick = function() {
        introBox.style.display = 'block';
        introBox.classList.remove('fadeOut');
        introBox.classList.remove('animation');
        introBox.classList.add('fadeIn');
        introBox.classList.add('animation');

    };

    closeIntroBtn.onclick = function() {
        introBox.classList.remove('fadeIn');
        introBox.classList.remove('animation');
        introBox.classList.add('fadeOut');
        introBox.classList.add('animation');

        setTimeout(function() {
            introBox.style.display = 'none';
        }, 800);
    };

    // 输入手机号认证
    phone.onchange = function() {
        var errorText = document.getElementsByClassName("error-text")[0];
//        var errorTextCheck = document.getElementsByClassName("error-text-check")[0];
         if (isphone(phone.value)) {
             $(errorText).css('display', 'none');
             // 让正确文显示出来
             // 让按钮能点击
             $(getCouponBtn).removeAttr('disabled');
             $(getCouponBtn).removeClass('disable');
             $(getCouponBtn).addClass('able')   
     		$.ajax({
      			type:'post',
      			url : 'check?phone='+phone.value,
      			success:function(data) {
      				if(data=="true") {
      					$('.error-text-check').css('display', 'none');
      		             $(getCouponBtn).removeAttr('disabled');
      		             $(getCouponBtn).removeClass('disable');
      		             $(getCouponBtn).addClass('able') 
      				} else {
      					$('.error-text-check').css('display', 'block');
      	               // 让按钮不能点击
      	               $(getCouponBtn).attr('disabled', 'disabled');
      	               $(getCouponBtn).removeClass('able');
      	               $(getCouponBtn).addClass('disable');
      					return false;
      				}
      			}
      		});
           }else {
               // 让错误文本显示出来
               $(errorText).css('display', 'block');
               // 让正确文本不显示出来
               $('.error-text-check').css('display', 'none');
               // 让按钮不能点击
               $(getCouponBtn).attr('disabled', 'disabled');
               $(getCouponBtn).removeClass('able');
               $(getCouponBtn).addClass('disable');
               return false;
          }
         
    };
    
    getCouponBtn.onclick = function() {
		$.ajax({
			type:'post',
			url : 'save?phone='+phone.value,
			success:function(data) {
				if(data=="true") {
			       var url = './getcoupon.jsp';
			        window.open(url, "_parent");
				} 
			}
		});


  };

    
    
    
    
    function isphone(inputString)
    {
         var partten = /^1[3,5,8]\d{9}$/;
         var fl=false;
         if(partten.test(inputString))
         {
              return true;
         }
         else
         {
              return false;
              
         }
    }

//    // 领取优惠券按钮
//    getCouponBtn.onclick = function() {
//    	var errorTextCheck = document.getElementsByClassName("error-text-check")[0];
//  		$.ajax({
//			type:'post',
//			url : 'save?phone='+phone.value,
//			success:function(data) {
//				if(data=="true") {
//			       var url = './getcoupon.jsp';
//			        window.open(url, "_parent");
//				} else {
//					errorTextCheck.classList.remove("hide");
//					return false;
//				}
//			}
//		});
//
//
//    };

    $('#main').fullpage({
        dragAndMove: true,
        navigation: false,
        css3: true,
        fixedElement: '.arrow',
        onLeave: function(index, nextIndex, direction) {
            if ($(introBox).css('display') === 'block') {
                $(closeIntroBtn).trigger('click');
            }
        }
    });

    // console.log(document.documentElement.clientHeight);

});
