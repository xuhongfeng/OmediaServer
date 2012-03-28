$(document).ready(function(){
	$("#btn-ok").button();
	$("#btn-cancel").button();
	$("#btn-cancel").click(function(){
		window.location.href = "/omedia/login-vm.do";
	});
	$("form#form_register").validate({
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
			},
			pwconfirm:{
				equalTo : "#password"
			},
			email : {
				required:true,
				email : true
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
			},
			pwconfirm:{
				equalTo : "两次密码输入不一致"
			},
			email:{
				required : "邮箱不能为空",
				 email : "邮箱格式不正确"
			}
		},
		submitHandler : do_register
	});
});

var username;
var password;

function do_register() {
	username = $("#username").val();
	password = $("#password").val();
	var email = $("#email").val();
    var OMEDIA_VERSION = $("span#omedia_version").attr("value");
	var url = "/omedia/register.do?username="+username
		+"&password="+password
		+"&email="+email
		+"&omediaVersion="+OMEDIA_VERSION;
	$.getJSON(url, after_register);
}

function after_register(json) {
	if(json.result==RESULT_CODE.REGISTER.USERNAME_EXISTS) {
		alert("用户名已存在");
	} else if(json.result==RESULT_CODE.REGISTER.VERSION_WRONG) {
		alert("omedia版本错误");
	} else if(json.result==RESULT_CODE.SUCCESS) {
		setCookie('username',username,7);
		setCookie('password',"",7);
		setCookie('remember_me',false,7);
		alert("注册成功");
		window.location.href = "/omedia/html/login.html";
	} else {
		process_result(json);
	}
}
