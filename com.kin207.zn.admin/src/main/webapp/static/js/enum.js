
function Map(){
	this.container = new Object();
}
Map.prototype.put = function(key, value){
	this.container[key] = value;
};
Map.prototype.get = function(key){
	return this.container[key];
};
Map.prototype.keyArray = function(){
	var keys = new Array();
	for(var key in this.container){
		keys.push(key);
	}
	return keys;
};

function loadSelect(enumObj,selectId){
	if(enumObj!=null||enumObj!=""){
		var data = [{ "text": "请选择", "id": "0"}];
		$.each(enumObj.keyArray(),function(i,key){
			data.push({ "text": enumObj.get(key), "id": key });
		});
		$("#"+selectId).combobox("loadData",data);
	}
}

/**
 * youtuer js 常量配置
 */
//任务状态
var YTT_ORDER_TYPE = new Map();
YTT_ORDER_TYPE.put(0, "大巴");
YTT_ORDER_TYPE.put(1, "导游");
YTT_ORDER_TYPE.put(2, "翻译");
YTT_ORDER_TYPE.put(3, "美食");
YTT_ORDER_TYPE.put(4, "接机");
YTT_ORDER_TYPE.put(5, "VIP");


var Form = {//表单生成相关
	    select_tmpl: "<span class='form-label'><label><span class='l'>{label}</span> <select id='{id}' data-vid='{id}' name='{name}' class='form-control input-sm form-inline {class}' style='{style}' {disabled} {reg}>{options}</select></label></span> ",
	    option_tmpl: "<option value='{value}' {selected}>{text}</option>",
	    radio_tmpl: '<label class="checkbox-inline"><input type="radio" class="{class}" name="{name}" value="{value}"> {text} </label>',
	    checkbox_tmpl: '<label class="checkbox-inline"><input type="checkbox" class="{class}" name="{name}" value="{value}"> {text} </label>',
	    radio_check_box: ' <span class="form-label"><span class="checkboxes"><span class="l">{label}</span> <div class="radio_check_box ">{elements}</div></span></span> ',
	    make: function (type, data, opts) {
	        var string = "";
	        data = data.container || data; //如果存在container就优先使用,后台的协议.

	        for (var i in data) {
	            if (type == "select") {

	                string = string + format(Form.option_tmpl, {value: i, text: data[i], selected: opts.selected == i ? "selected" : "", disabled: opts.disabled});
	            }

	            if (type == "radio") {
	                string = string + format(Form.radio_tmpl, $.extend({value: i, text: data[i], checked: in_array(i, opts.checked) ? "checked" : ""}, opts));
	            }

	            if (type == "checkbox") {
	                string = format(Form.checkbox_tmpl, $.extend({value: i, text: data[i], checked: in_array(i, opts.checked) ? "checked" : ""}, opts)) + string;
	            }
	        }

	        if (type == "select") {

	            if (opts.placeholder && type == "select") {
	                string = format(Form.option_tmpl, {value: "", text: opts.placeholder, selected: "selected"}) + string;
	            }
	            if(opts.noreg){
	                return format(Form.select_tmpl, $.extend({options: string}, opts));
	            }

	            return format(Form.select_tmpl, $.extend({options: string,"reg":"data-reg=''"}, opts));

	        }

	        if (type == "radio" || type == "checkbox") {
	            return format(Form.radio_check_box, $.extend({elements: string}, opts));
	        }
	    },
	    getDate: function(id){
	        return $("#" + id + " input").val();
	    },
	    getData: function (ele, isJSON) {
	        ele.find("[disabled]").attr("_disabled", "").removeAttr("disabled");
	        var formData = isJSON ? ele.serializeArray() : ele.serialize();
	        ele.find("[_disabled]").attr("disabled", true).removeAttr("_disabled");
	        return formData;
	    },
	    getJSONData: function () {
	        return Form.getData(ele, true)
	    }
	}

function format(template, data) {
    var _regex = /\{([\w\.]*)\}/g;
    return template.replace(_regex, function (str, key) {
        var keys = key.split("."), value = data[keys.shift()];
        $.each(keys, function () {
            value = value[this]
        });
        return value === null || value === undefined ? "" : value
    })
}







