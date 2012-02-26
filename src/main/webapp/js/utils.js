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

function setCookie(c_name,value,expiredays) {
	deleteCookie(c_name);
	var exdate=new Date();
	exdate.setDate(exdate.getDate()+expiredays);
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}

function getCookie(c_name) {
	if (document.cookie.length>0) {
		  c_start=document.cookie.indexOf(c_name + "=")
		  if (c_start!=-1) { 
		   	 	c_start=c_start + c_name.length+1 
		    	c_end=document.cookie.indexOf(";",c_start)
		    	if (c_end==-1) c_end=document.cookie.length
		    	return unescape(document.cookie.substring(c_start,c_end))
		  } 
	}
	return ""
}
function deleteCookie(key)
{
  // Delete a cookie by setting the date of expiry to yesterday
  date = new Date();
  date.setDate(date.getDate() -1);
  document.cookie = escape(key) + '=;expires=' + date;
}