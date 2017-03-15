var userMsg = window.sessionStorage;
alert(userMsg.getItem("userAccount"))

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


$(function() {
	$("#mainmenu").click(function(event) {
		var type = ($(event.target).parent().parent())[0].id;
		var url = getUrl(type);
		if(url != null){
			$("#changUrl").attr('src', url)
		}
	})

	function getUrl(type) {
		var url = null;
		switch(type) {
			case "menu_users":
				url = "./users.html";
				break;
			case "menu_topic":
				url = "./topic.html";
				break;
			case "menu_news":
				url = "./news.html";
				break;
			case "menu_user":
				url = "./user.html";
				break;
			case "menu_setup":
				url = "./setup.html";
				break;
			default:
				break;
		}
		return url;
	}
})

