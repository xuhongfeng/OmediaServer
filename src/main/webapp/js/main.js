$(document).ready(function(){
	var accountId = $.cookie("accountId");
	var token = $.cookie("token");
	show_ccn_file(accountId, token);
});

function show_ccn_file(accountId, token) {
	var url = "/omedia/showCcnFilesVm.do?accountId="+accountId
		+"&token="+token;
	$.ajax({
  		url: url,
  		success: after_show_ccn_file,
  		contentType: "text/html; charset=utf-8"
	});
}

function after_show_ccn_file(vm) {
	$("div#div_content").empty();
	$("div#div_content").wrapInner(vm);
}