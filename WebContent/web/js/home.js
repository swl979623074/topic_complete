var userMsg = window.sessionStorage;
var userAccount = userMsg.getItem("userAccount");
var userId = userMsg.getItem("userId");
var userAlias = userMsg.getItem("userAlias");

(function(initMsg){
	console.log(initMsg);
	if("" != userId && null != userId && "null" != userId){
		if("" != userAlias && null != userAlias && "null" != userAlias){
			console.log("userAlias: "+ userAlias);
			$(".usermsg a:first").text(userAlias);
		}else{
			$(".usermsg a:first").text(userAccount);
		}
	}
})("init user message");


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

