(function($) {
    $.fn.extend({
        serializeObject : function() {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [ o[this.name] ];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        }
    });
    $.extend({
    	checkMobile : function(str) {
	    	var re = /^1\d{10}$/;
	    	if (re.test(str)) {
	    		return true;
	    	} else {
	    		return false;
	    	}
    	},
    	checkPhone : function(str){
	    	var re = /^0\d{2,3}-?\d{7,8}$/;
	    	if(re.test(str)){
	    		return true;
	    	}else{
	    		return false;
	    	}
    	},
    	checkUser : function(str){
    		//验证规则：字母、数字、下划线组成，字母开头，4-16位。
	    	var re = /^[a-zA-z]\w{3,15}$/;
	    	if(re.test(str)){
	    		return true;
	    	}else{
	    		return false;
	    	}
    	},
    	checkEmail : function(str){
    		var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
			if(re.test(str)){
	    		return true;
	    	}else{
	    		return false;
	    	}
    	},
    	checkIdCard : function(str){  
    		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
    		var re = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
			if (re.test(str)){
				return true;
			} else {
				return false;
			}
    	}
    });
})(jQuery)