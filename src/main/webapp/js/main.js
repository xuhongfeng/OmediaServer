$(document).ready(function(){
	$("#tabs").tabs();
	show_ccn_file();
	$("span#head_username").wrapInner(getCookie("username"));
});

function show_ccn_file() {
	var accountId = getCookie("accountId");
	var token = getCookie("token");
	var url = "/omedia/showCcnFiles-vm.do?accountId="+accountId
		+"&token="+token;
	$.ajax({
  		url: url,
  		success: after_show_ccn_file,
  		contentType: "text/html; charset=utf-8"
	});
}

function after_show_ccn_file(vm) {
	$("div#div-files").empty();
	$("div#div-files").wrapInner(vm);
}