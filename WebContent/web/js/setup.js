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

$(function(){
	$("div").click(function(event){
		var type = event.target.id;
		var url = getUrl(type);
		if(url != null){
			toggleClass(event.target);
			$("#changUrl").attr('src',url);
		}
	})
	
	function getUrl(type){
		var url = null;
		switch(type){
			case "topicManager":url='./topicmanager.html';break;
			case "summary":url='./summary.html';break;
			case "pwd":url='./pwd.html';break;
			case "grade":url='./grade.html';break;
			default:break;
		}
		return url;
	}
	
	function toggleClass(ele){
		$(".menu_select").removeClass("menu_select");
		$(ele).addClass("menu_select")
	}
})
