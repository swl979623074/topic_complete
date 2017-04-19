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

// $(function() {
// $(".list li").click(function(e) {
// console.log("user ID: " + $(this).attr("userId")); // 获取用户id
// console.log(e.target.alt); // 事件类型 delete update other
// })
// })

/** **********************************model*********************************** */
(function() {
	window.model = {
		getFriendList : function(cb_showUsers, data) {
			$.post("/Topic/userController/getFriend", data, function(res) {
				if (res.status == "SUCCESS") {
					cb_showUsers(res.list);
				}
			});
		},
		closeFriendWindowAndMissMsg : function(data) {
			$.post("/Topic/friendController/updateOpenWindowAndMissMsg", data,
					function(res) {
						if (res.status == "SUCCESS") {
							console.log("closeFriendWindowAndMissMsg success");
						}
					});
		},
		closeFriendWindow : function(data) {
			$.post("/Topic/friendController/closeWindow", data, function(res) {
				if (res.status == "SUCCESS") {
					console.log("closeFriendWindow success");
				}
			});
		},
		updateRemark : function(cb_showNewRemark, data) {
			$.post("/Topic/friendController/updateRemark", data, function(res) {
				if (res.status == "SUCCESS") {
					console.log("updateRemark success");
					cb_showNewRemark(data.remark);
				}
			});
		},
		deleteFriend : function(cb_deleteFriend, data) {
			$.post("/Topic/friendController/deleteFriend", data, function(res) {
				if (res.status == "SUCCESS") {
					cb_deleteFriend(data.friendId)
					console.log("deleteFriend success");
				}
			});
		},
		getFriendMissMessage : function(cb_showFriendMessage, data) {
			$.post("/Topic/messageController/getFriendMissMsg", data, function(
					res) {
				console.log(res);
			})
		},
		getFriendMsg : function(cb_showFriendMsg, data) {
			$.post("/Topic/messageController/getFriendMsg", data,
					function(res) {
						cb_showFriendMsg(res.list);
					})
		},
		getUserMsg : function(cb_showUserMsg, data) {
			$.post("/Topic/userController/getUserMsg", data, function(res) {
				if (res.status == "SUCCESS") {
					cb_showUserMsg(res.userMsg);
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
			$(".list li").hover(function() {
				$(this).css("background", "lightblue");
				$(this).children("div").eq(1).children('div').eq(2).show();
			}, function() {
				$(this).css("background", "lightgoldenrodyellow");
				$(this).children("div").eq(1).children('div').eq(2).hide();
			});
		},
		showFriend : function(list) {
			var html = "";
			var len = list.length;
			var delete_flag = "../img/logo.png";
			var update_flag = "../img/logo.png";
			var newMsg_flag = "../img/logo.png";
			for ( var i = 0; i < len; i++) {
				var friendId = list[i].userId;
				var userDegree = list[i].userDegree;
				var userSex = list[i].userSex;
				var userphoto = list[i].userphoto;
				var firendRemark = list[i].firendRemark == null ? list[i].userAlias
						: list[i].firendRemark;
				var friendMissMsg = list[i].friendMissMsg;
				var showNewMsgClass = friendMissMsg == 0 ? "newmsg hide"
						: "newmsg";
				html += "<li onclick='clickFriend(this)' onmouseover='friendOver(this)' onmouseout='friendOut(this)' data-selectedId='"
						+ friendId
						+ "'><div><img src='"
						+ userphoto
						+ "' /></div><div><div><span class='friendRemark'>"
						+ firendRemark
						+ "</span></div><div><span>"
						+ userSex
						+ "</span> <span>等级："
						+ userDegree
						+ "级</span></div><div><div><img src='"
						+ delete_flag
						+ "' data-userId='"
						+ friendId
						+ "' alt='delete' title='delete' onclick='openDialog(this)' /></div><div><img src='"
						+ update_flag
						+ "' data-userId='"
						+ friendId
						+ "' alt='update' title='update' data-missnum='"
						+ friendMissMsg
						+ "' onclick='openDialog(this)' /></div></div></div><div alt='new message' title='new message' class='"
						+ showNewMsgClass
						+ "'><img src='"
						+ newMsg_flag
						+ "' /></div></li>	";
			}
			$(".usersList").html(html);
		},
		showNewRemark : function(remark) {
			$(".friendRemark").html(remark);
		},
		deleteFriend : function(friendId) {
			$("li[data-selectedId='" + friendId + "']").remove();
		},
		showContactMessage : function(list) {
			var len = list.length;
			var i = len;
			var html = "";
			while (i--) {
				var userCome = list[i].userCome;
				var convContent = list[i].convContent;
				var convTime = list[i].convTime;
				var userPhoto = list[i].userPhoto;

				if (userCome == window.userId) {
					html += "<li ><div class='right'><div class='content'><p><time>"
							+ convTime
							+ "</time></p><p><span>"
							+ convContent
							+ "</span></p></div><div class='headImg'><img title='' src='"
							+ userPhoto + "' /></div></div></li>";
				} else {
					html += "<li><div  class='left'><div class='headImg'><img title='' src='"
							+ userPhoto
							+ "' /></div><div class='content'><p><time>"
							+ convTime
							+ "</time></p><p><span>"
							+ convContent
							+ "</span></p></div></div></li>";
				}

			}
			var old_docHeight = $(".statement").height();
			$(".statement").prepend(html);
			var boxHeight = $(".statement").parent().height();
			var moreBoxHeight = $(".statement").parent().children("div").eq(0)
					.height();
			var docHeight = $(".statement").height();
			$(".statement").parent().scrollTop(
					docHeight + moreBoxHeight - boxHeight - old_docHeight);
		},
		showUserMsg : function(data) {
			$("#usreMsg_account").html(data.userAccount);
			$("#usreMsg_alias").html(data.userAlias);
			$("#usreMsg_createtime").html(data.userCreatetime);
			$("#usreMsg_degree").html(data.userDegree);
			$("#usreMsg_profession").html(data.userProfession);
		}
	};
})();

/** **********************************control*********************************** */
(function() {
	var userMsg = window.sessionStorage;
	window.userId = userMsg.getItem("userId");

	window.page = {
		pageSize : 0,
		pageNum : 10
	};
	window.getWindow_friendId = null;
	window.getWindow_groupId = null;

	window.li_selectedId = null;

	var parent_window = window.parent.window.location.href;
	var strs = parent_window.split("/");
	window.parentType = strs[strs.length - 1];

	if (window.parentType == "users.html") {
		window.view.changeToUsersModel();
		window.model.getFriendList(window.view.showFriend, {
			userId : window.userId
		});
	} else if (window.parentType == "topic.html") {
		window.view.changeToTopicModel();
	}
})();

/** **********************************event*********************************** */
(function() {
	window.openDialog = function(that) {
		var title = $(that).attr("title");
		$(".delete_or_update_dialog").removeClass("hide");
		if (title == "delete") {
			$(".update").hasClass("hide") ? null : $(".update")
					.addClass('hide');
			$(".delete").removeClass("hide");
		} else {
			$(".delete").hasClass("hide") ? null : $(".delete")
					.addClass('hide');
			$(".update").removeClass("hide");
			$(".update").val("");
		}
		window.li_selectedId = $(that).parent().parent().parent().parent()
				.attr("data-selectedId");
	};
	window.hideDialog = function() {
		$(".delete").hasClass("hide") ? null : $(".delete").addClass('hide');
		$(".update").hasClass("hide") ? null : $(".update").addClass('hide');
		$(".delete_or_update_dialog").hasClass("hide") ? null : $(
				".delete_or_update_dialog").addClass('hide');
		window.li_selectedId = null;
	};
	window.friendOver = function(that) {
		$(that).css("background", "lightblue");
		$(that).children("div").eq(1).children('div').eq(2).show();
	};
	window.friendOut = function(that) {
		$(that).css("background", "lightgoldenrodyellow");
		$(that).children("div").eq(1).children('div').eq(2).hide();
	};
	window.clickFriend = function(that) {
		window.page.pageSize = 0;
		$(".statement").html("");
		
		var friendId = $(that).attr("data-selectedId");
		var target = $(that).find(".newmsg");
		var missNum = $(target).attr("data-missnum");
		$(that).parent().find(".selected_li").removeClass("selected_li");
		$(that).addClass("selected_li");

		window.model.getUserMsg(window.view.showUserMsg, {
			userId : friendId
		});

		if (window.getWindow_friendId != null)
			window.model.closeFriendWindow({
				userId : window.userId,
				friendId : window.getWindow_friendId
			});

		window.model.closeFriendWindowAndMissMsg({
			userId : userId,
			friendId : friendId
		});

		if (missNum > 0)
			window.model.getFriendMissMessage(window.view.showContactMessage, {
				userId : userId,
				friendId : friendId,
				missNum : missNum
			});

		window.getWindow_friendId = friendId;
		$(target).attr("data-missnum", '0');
		$(target).addClass("hide");
	};
	window.commintUpdateOrDelete = function(that) {
		var delete_ele = $(that).parent().parent().find("span:not(.hide)");
		var update_ele = $(that).parent().parent().find("input:not(.hide)");
		if ($(delete_ele).size() > 0) {
			window.model.deleteFriend(window.view.deleteFriend, {
				userId : window.userId,
				friendId : window.li_selectedId
			});
		} else if ($(update_ele).size() > 0) {
			var data = {
				userId : window.userId,
				friendId : window.li_selectedId,
				remark : $(update_ele).val()
			}
			window.model.updateRemark(window.view.showNewRemark, data);
		} else {
			console.log("contact.js Function:commintUpdateOrDelete Event");
		}
		window.hideDialog();
	};
	window.getMoreMessage = function() {

		if (window.parentType == "users.html") {
			window.page.userId = window.userId;
			window.page.friendId = window.getWindow_friendId;
			if (window.page.friendId == null)
				return;
			window.model.getFriendMsg(window.view.showContactMessage,
					window.page);
		} else if (window.parentType == "topic.html") {

		}
		window.page.pageSize++;
	}
})();