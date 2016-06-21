/**
 * @author ZhangHuihua@msn.com
 */
(function($){
	// jQuery validate

	// 自定义身份证校验器
	$.validator.addMethod("idcard", function(value, element) {
		return this.optional(element) || /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/.test(value);
	}, "长度或格式错误，只允许填写字母、数字");

	$.extend($.validator.messages, {
		required: "必填",
		remote: "请修正该字段",
		email: "电子邮件格式错误",
		url: "网址格式错误",
		date: "日期输入不合法",
		dateISO: "日期(ISO)输入不合法",
		number: "请输入数字",
		digits: "只能输入整数",
		creditcard: "信用卡号输入不合法",
		equalTo: "请再次输入相同的值",
		accept: "该字符串后缀名不合法",
		maxlength: $.validator.format("字符串最长为{0}"),
		minlength: $.validator.format("字符串最短为{0}"),
		rangelength: $.validator.format("字符串长度介于{0}和{1}之间"),
		range: $.validator.format("值介于{0}和{1}之间"),
		max: $.validator.format("值最大为{0}"),
		min: $.validator.format("值最小为{0}"),
		
		alphanumeric: "字母、数字、下划线格式错误",
		lettersonly: "必须是字母",
		phone: "数字、空格、括号格式错误",
		mobile: "请输入正确的手机号码！",
		telephone: "请输入正确的电话号码！",
		idcard:"格式错误，只允许填写字母X、数字"
	});
	
	// DWZ regional
	$.setRegional("datepicker", {
		dayNames: ['日', '一', '二', '三', '四', '五', '六'],
		monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
	});
	$.setRegional("alertMsg", {
		title:{error:"错误", info:"提醒", warn:"警告", correct:"成功", confirm:"确认提示"},
		butMsg:{ok:"确定", yes:"是", no:"否", cancel:"取消"}
	});
})(jQuery);
