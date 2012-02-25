$(document).ready(function(){
	$("#btn-login").button();
	$("#btn-register").button();
	$("#btn-login").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		do_login(username, password);
	});
	$("#btn-register").click(function(){
		window.location.href = "/omedia/html/register.html";
	});
});

function do_login(username, password) {
	var url = "/omedia/login.do?username="+username
		+"&password="+password
		+"&omediaVersion="+OMEDIA_VERSION;
	$.getJSON(url, after_login);
}

function after_login(json) {
	if(json.result==RESULT_CODE.LOGIN.FAILED) {
		alert("用户名或密码错误");
	} else if(json.result==RESULT_CODE.LOGIN.VERSION_WRONG) {
		alert("omedia版本错误");
	} else if(json.result==RESULT_CODE.SUCCESS) {
		$.cookie('accountId', json.accountId, { expires: 30 });
		$.cookie('token', json.token, { expires: 30 });
		window.location.href = "/omedia/html/main.html";
	} else {
		process_result(json);
	}
}