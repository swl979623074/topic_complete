(function(){
	window.onresize = function() {
		throttle(window.onload)
	}

	function throttle(method, content) {
		clearTimeout(method.tId)
		method.tId = setTimeout(function() {
			method.call(content)
		}, 100)
	}
	
	window.onload = function() {
	    html = document.getElementsByTagName("html")[0];
	    html.style.fontSize = window.innerWidth / 100 + "px";
	}
})();

function showDialog(){
	$(".createTopic").removeClass('hide');
}
function hideDialog(){
	$(".createTopic").addClass('hide');
}
