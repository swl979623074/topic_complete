var userMsg = window.sessionStorage;
var userAccount = userMsg.getItem("userAccount");
var userId = userMsg.getItem("userId");
var userAlias = userMsg.getItem("userAlias");

(function(initMsg){
	if("" != userId && null != userId && "null" != userId){
		if("" != userAlias && null != userAlias && "null" != userAlias){
			$(".usermsg a:first").text(userAlias);
		}else{
			$(".usermsg a:first").text(userAccount);
		}
	}
	$("#mainmenu").find("img").eq(0).attr("data-selected","true").css("background","#86EE60");
	$("#mainmenu").find("img").click(function(){
		$("#mainmenu").find("img[data-selected]").removeAttr("data-selected").css("background","white");
		$(this).attr("data-selected","true").css("background","#86EE60");
	});
	$("#mainmenu").find("img").hover(function(){
		if(!$(this).attr("data-selected"))
			$(this).css("background","#C1EA9F");
	},function(){
		if(!$(this).attr("data-selected"))
			$(this).css("background","#FFF");
	})
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
		if(type != menu_news && userId == null){
			alert("请先登录");
			window.location.href = "./../html/login.html";
		}
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
