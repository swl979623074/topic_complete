(function() {
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
	$(".list li").hover(function() {
		$(this).css("background", "lightblue");
		$(this).children("div").eq(1).children('div').eq(2).show();
	}, function() {
		$(this).css("background", "lightgoldenrodyellow");
		$(this).children("div").eq(1).children('div').eq(2).hide();
	});
	$(".list li").click(function(e) {
		console.log("user ID: " + $(this).attr("userId")); // 获取用户id
		console.log(e.target.alt); // 事件类型 delete update other
	})

})

function openDialog(title) {
	$(".delete_or_update_dialog").removeClass("hide");
	if (title == "delete") {
		$(".update").hasClass("hide") ? null : $(".update").addClass('hide');
		$(".delete").removeClass("hide");
	} else {
		$(".delete").hasClass("hide") ? null : $(".delete").addClass('hide');
		$(".update").removeClass("hide");
	}
}
function hideDialog() {
	$(".delete").hasClass("hide") ? null : $(".delete").addClass('hide');
	$(".update").hasClass("hide") ? null : $(".update").addClass('hide');
	$(".delete_or_update_dialog").hasClass("hide") ? null : $(
			".delete_or_update_dialog").addClass('hide');
}

/** **********************************model*********************************** */
(function() {
	window.model = {
		getUserList : function(cb_showUsers, data) {
			$.post("/Topic/userController/getUserList", data, function(res) {
				if (res.status == "SUCCESS") {
					cb_showUsers(res.list);
				}
			})
		}
	};
})();

/** **********************************view*********************************** */
(function() {
	window.view = {
		changeToTopicModel : function() {
			$(".users").addClass("hide");
			$(".topic").removeClass("hide");
			$(".recommend").removeClass("hide");
			$(".currentTopic").removeClass("visibility_hidden");
			$(".currentUser").removeClass("visibility_hidden");
			$("#addFriend").removeClass("visibility_hidden");
		},
		changeToUsersModel : function() {
			$(".users").removeClass("hide");
			$(".topic").addClass("hide");
			$(".recommend").addClass("hide");
			$(".currentTopic").addClass("visibility_hidden");
			$(".currentUser").removeClass("visibility_hidden");
			$("#addFriend").addClass("visibility_hidden");

		},
		showUsers : function(usersList) {
			var html = "";
			var len = usersList.length;
			for(var i = 0;i<len;i++){
				
			}
			$(".usersList").html(html);
		}
	};
})();

/** **********************************control*********************************** */
(function() {
	var parent_window = window.parent.window.location.href;
	var strs = parent_window.split("/");
	var parentType = strs[strs.length - 1];
	if (parentType == "users.html") {
		window.view.changeToUsersModel();
	} else if (parentType == "topic.html") {
		window.view.changeToTopicModel();
	}
})();