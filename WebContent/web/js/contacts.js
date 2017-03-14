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
	$(".list li").hover(function(){
		$(this).css("background","lightblue");
		$(this).children("div").eq(1).children('div').eq(2).show();
	},function(){
		$(this).css("background","lightgoldenrodyellow");
		$(this).children("div").eq(1).children('div').eq(2).hide();
	});
	$(".list li").click(function(e){
		console.log("user ID: "+$(this).attr("userId"));	//获取用户id
		console.log(e.target.alt)	//事件类型 delete update other
	})
	
})

function openDialog(title){
	$(".delete_or_update_dialog").removeClass("hide");
	if(title == "delete"){
		$(".update").hasClass("hide") ? null :$(".update").addClass('hide');
		$(".delete").removeClass("hide");
	}else{
		$(".delete").hasClass("hide") ? null :$(".delete").addClass('hide');
		$(".update").removeClass("hide");
	}
}
function hideDialog(){
	$(".delete").hasClass("hide") ? null :$(".delete").addClass('hide');
	$(".update").hasClass("hide") ? null :$(".update").addClass('hide');
	$(".delete_or_update_dialog").hasClass("hide") ? null :$(".delete_or_update_dialog").addClass('hide');
}
