function process_result(json) {
	if(json.result==-1) {
		alert("服务器错误");
	} else if(json.result == 3){
		alert("cookie过期，请重新登陆");
		window.location.href = "/omedia";
	}
}