$(document).ready(function(){
	var accountId = $.cookie("accountId");
	var token = $.cookie("token");
	show_ccn_file(accountId, token);
});

function show_ccn_file(accountId, token) {
	var url = "/omedia/showCcnFiles.do?accountId="+accountId
		+"&token="+token;
	$.getJSON(url, after_show_ccn_file);
}

function after_show_ccn_file(json) {
	if(json.result==1) {
		var ccn_files = json.ccnFiles;
		for(var i=0; i<ccn_files.length; i++) {
			var file = ccn_files[i];
			var name = file.ccnName;
			var size = format_file_size(file.size);
			var time = format_time(file.time);
			var tr = "<tr><td><a href=\"/omedia/download-file.do?path="+file.filePath+"\">"+name
				+"</a></td><td>"+size
				+"</td><td>"+time
				+"</td></tr>";
			$(tr).appendTo("table");
		}
	} else {
		process_result(json)
	}
}