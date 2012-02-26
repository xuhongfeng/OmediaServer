$(document).ready(function(){
	$("#btn-login").button();
	$("#btn-register").button();
	$("#btn-register").click(function(){
		window.location.href = "/omedia/html/register.html";
	});
	$("form#form_login").validate({
		rules:{
			username:{
				required : true,
				minlength : 4,
				maxlength : 32
			},
			password:{
				required : true,
				minlength : 4,
				maxlength : 32
			}
		},
		messages:{
			username:{
				required : "用户名不能为空",
				minlength : "用户名不能少于4位",
				maxlength : "用户名不能多于32位"
			},
			password:{
				required : "密码不能为空",
				minlength : "密码不能少于4位",
				maxlength : "密码不能多于32位"
			}
		},
		submitHandler : do_login
	});
	$("#username").val(getCookie('username'));
	$("#password").val(getCookie('password'));
	if(getCookie('remember_me') == "true") {
		$("#cb_pw")[0].checked = true;
	}
	$("#cb_pw").click(function(){
		setCookie('remember_me',this.checked,7);
	});
});

function do_login() {
	var username = $("#username").val();
	var password = $("#password").val();
	setCookie('username',username,7);
	if($("#cb_pw")[0].checked) {
		setCookie('password',password,7);
	} else {
		setCookie('password',"",7);
	}
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
		setCookie('accountId',json.accountId,7);
		setCookie('token',json.token,7);
		window.location.href = "/omedia/html/main.html";
	} else {
		process_result(json);
	}
}