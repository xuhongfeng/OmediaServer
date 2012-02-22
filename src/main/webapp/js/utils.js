function show_json(json) {
  		var items = [];
		$.each(json, function(key, val) {
			items.push('<li id="' + key + '">' + val + '</li>');
		});
		$('<ul/>', {
		    'class': 'my-new-list',
		    html: items.join('')
		}).appendTo('body');
}

function format_file_size(size) {
	var str;
	var m =  size/1000000;
	m -= m%1;
	size = size%1000000;
	var k = size/1000;
	k -= k%1;
	size = size%1000;
	size -= size%1;
	return m+"M"+k+"K"+size+"B";
}

function format_time(time) {
	var t = new Date();
	t.setTime(time);
	return t.getFullYear()+"-"+(t.getMonth()+1)+"-"+t.getDate()+" "
		+t.getHours()+":"+t.getMinutes()+":"+t.getSeconds();
}