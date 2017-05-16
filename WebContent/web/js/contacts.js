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

/** **********************************model*********************************** */
(function() {
	window.model = {
		initAllFriendWindow : function(data) {
			$.post("/Topic/friendController/initAllWindowStatus", data,
					function(res) {
						if (res.status == "SUCCESS") {
							console.log("init friend window over");
						}
					})
		},
		initAllTopicWindow : function(data) {
			$.post("/Topic/topicController/initAllWindowStatus", data,
					function(res) {
						if (res.status == "SUCCESS") {
							console.log("init topic window over");
						}
					})
		},
		getFriendList : function(cb_showUsers, data) {
			$.post("/Topic/userController/getFriend", data, function(res) {
				if (res.status == "SUCCESS") {
					cb_showUsers(res.list);
				}
			});
		},
		updateFriendWindowAndMissMsg : function(data) {
			$
					.post(
							"/Topic/friendController/updateOpenWindowAndMissMsg",
							data,
							function(res) {
								if (res.status == "SUCCESS") {
									console
											.log("updateFriendWindowAndMissMsg success");
								}
							});
		},
		closeOrOpenFriendWindow : function(data) {
			$.post("/Topic/friendController/updateWindowStatus", data,
					function(res) {
						if (res.status == "SUCCESS") {
							console.log("closeOrOpenFriendWindow success");
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
					console.log("delete friend success");
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
		},
		getTopicList : function(cb_showTopic, data) {
			$.post("/Topic/topicController/getTopicListByUserId", data,
					function(res) {
						if (res.status == "SUCCESS") {
							cb_showTopic(res.list);
						}
					})
		},
		deleteTopic : function(cb_deletTopic, data) {
			$.post("/Topic/topicController/deleteTopicAboutUser", data,
					function(res) {
						if (res.status == "SUCCESS") {
							cb_deletTopic(data.topicId);
						}
					})
		},
		updateTopicWindowAndMissMsg : function(data) {
			$.post("/Topic/topicController/updateMeetWindowAndMissMsg", data,
					function(res) {
						if (res.status == "SUCCESS") {
							console.log("updateMeetWindowAndMissMsg success");
						}
					})
		},
		updateTopicWindowStatus : function(data) {
			$.post("/Topic/topicController/updateWindowStatus", data, function(
					res) {
				if (res.status == "SUCCESS") {
					console.log("updateWindowStatus success");
				}
			})
		},
		getMeetMsg : function(cb_showMeetMsg, data) {
			$.post("/Topic/messageController/getTopicMsg", data, function(res) {
				if (res.status == "SUCCESS") {
					cb_showMeetMsg(res.list);
				}
			})
		},
		getMeetMissMsg : function(cb_showMeetMsg, data) {
			$.post("/Topic/messageController/getTopicMissMsg", data, function(
					res) {
				if (res.status == "SUCCESS") {
					cb_showMeetMsg(res.list);
				}
			})
		},
		getTopicMsg : function(cb_showTopicMsg, data) {
			$.post("/Topic/topicController/getTopicMsg", data, function(res) {
				if (res.status == "SUCCESS") {
					cb_showTopicMsg(res);
				}
			})
		}
	};
})();

/** **********************************view*********************************** */
(function() {
	window.view = {
		changeToTopicModel : function() {
			$(".list").html("");
			$(".users").addClass("hide");
			$(".topic").removeClass("hide");
			$(".recommend").removeClass("hide");
			$(".currentTopic").removeClass("visibility_hidden");
			$(".currentUser").removeClass("visibility_hidden");
			$("#addFriend").removeClass("visibility_hidden");
			$(".summary").find("span").html("");
			$("#addFriend").attr("disabled", "true");
			$("#addFriend").css("cursor", "not-allowed");
			$("#userInputMsg").val("");
			$("#userInputMsg").attr("readonly","readonly");
			$("#userInputMsg").focus();
			$("#userInputMsg").css("cursor","not-allowed");
		},
		changeToUsersModel : function() {
			$(".list").html("");
			$(".users").removeClass("hide");
			$(".topic").addClass("hide");
			$(".recommend").addClass("hide");
			$(".currentTopic").addClass("visibility_hidden");
			$(".currentUser").removeClass("visibility_hidden");
			$("#addFriend").addClass("visibility_hidden");
			$(".summary").find("span").html("");
			$("#addFriend").hide();
			$("#userInputMsg").val("");
			$("#userInputMsg").attr("readonly","readonly");
			$("#userInputMsg").focus();
			$("#userInputMsg").css("cursor","not-allowed");
		},
		showFriend : function(list) {
			var html = "";
			var len = list.length;
			var delete_flag = "../img/icon_delete.png";
			var update_flag = "../img/icon_update.png";
			var newMsg_flag = "../img/icon_newmsg.png";
			
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
				html += "<li  onclick='clickFriend(this)' onmouseover='friendOver(this)' onmouseout='friendOut(this)' data-selectedId='"
						+ friendId
						+ "'><div><img onerror='imgError(this)' src='"
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
						+ "' onclick='openDialog(this)' /></div></div></div><div data-missnum='"
						+ friendMissMsg
						+ "' alt='new message' title='new message' class='"
						+ showNewMsgClass
						+ "'><img src='"
						+ newMsg_flag
						+ "' /></div></li>	";
			}
			$(".usersList").html(html);
		},
		showNewRemark : function(remark) {
//			$(".friendRemark").find('data').html(remark);
			$(".usersList").find("[data-selectedid='" + window.li_selectedId + "']").find(".friendRemark").html(remark);
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
							+ "</span></p></div><div class='headImg'><img onerror='imgError(this)' title='' src='"
							+ userPhoto + "' /></div></div></li>";
				} else {
					html += "<li><div  class='left'><div class='headImg'><img onerror='imgError(this)' title='' src='"
							+ userPhoto
							+ "' /></div><div class='content'><p><time>"
							+ convTime
							+ "</time></p><p><span>"
							+ convContent
							+ "</span></p></div></div></li>";
				}

			}
			var old_docHeight = $(".statement").height();
			if (window.page.pageSize == 1)
				$(".statement").html(html);
			else
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

			$("#addFriend").removeAttr("disabled");
			$("#addFriend").css('cursor', 'pointer');
		},
		showMeetMsg : function(list) {
			var len = list.length;
			var i = len;
			var html = "";
			while (i--) {
				var userCome = list[i].meetUserId;
				var convContent = list[i].meetContent;
				var convTime = list[i].meetTime;
				var userPhoto = list[i].userPhoto;

				if (userCome == window.userId) {
					html += "<li ><div class='right'><div class='content'><p><time>"
							+ convTime
							+ "</time></p><p><span>"
							+ convContent
							+ "</span></p></div><div class='headImg'><img onerror='imgError(this)' onclick='showUserMsg(this)' data-userid='"
							+ userCome
							+ "' title='' src='"
							+ userPhoto
							+ "' /></div></div></li>";
				} else {
					html += "<li><div  class='left'><div class='headImg'><img onerror='imgError(this)' onclick='showUserMsg(this)' data-userid='"
							+ userCome
							+ "'  title='' src='"
							+ userPhoto
							+ "' /></div><div class='content'><p><time>"
							+ convTime
							+ "</time></p><p><span>"
							+ convContent
							+ "</span></p></div></div></li>";
				}

			}
			var old_docHeight = $(".statement").height();
			if (window.page.pageSize == 1)
				$(".statement").html(html);
			else
				$(".statement").prepend(html);
			var boxHeight = $(".statement").parent().height();
			var moreBoxHeight = $(".statement").parent().children("div").eq(0)
					.height();
			var docHeight = $(".statement").height();
			$(".statement").parent().scrollTop(
					docHeight + moreBoxHeight - boxHeight - old_docHeight);
		},
		showTopic : function(list) {
			var html = "";
			var len = list.length;
			var delete_flag = "../img/icon_delete.png";
			var group_flag = "../img/icon_groupchat.png";
			var newMsg_flag = "../img/icon_newmsg.png";
			for ( var i = 0; i < len; i++) {
				var topicId = list[i].topicId;
				var topicTitle = list[i].topicTitle;
				var topicDescriptione = list[i].topicDescriptione;
				var topicMissNum = list[i].topicMissNum;
				var showNewMsgClass = topicMissNum == 0 ? "newmsg hide"
						: "newmsg";

				html += "<li  onclick='clickTopic(this)' onmouseover='topicOver(this)' onmouseout='topicOut(this)' data-selectedId='"
						+ topicId
						+ "'><div><img src='"
						+ group_flag
						+ "' /></div><div><div><span class='friendRemark'>"
						+ topicTitle
						+ "</span></div><div><span>"
						+ topicDescriptione
						+ "</span> </div><div><div><img src='"
						+ delete_flag
						+ "' data-topicId='"
						+ topicId
						+ "' alt='delete' title='delete' onclick='openDialog(this)' /></div></div></div><div data-missnum='"
						+ topicMissNum
						+ "' alt='new message' title='new message' class='"
						+ showNewMsgClass
						+ "'><img src='"
						+ newMsg_flag
						+ "' /></div></li>	";
			}
			$(".topicList").html(html);
		},
		deleteTopic : function(topicId) {
			$("li[data-selectedId='" + topicId + "']").remove();
		},
		showTopicMsg : function(data) {
			$("#topicId").html(data.topicId);
			$("#topicTitle").html(data.topicTitle);
			$("#topicDescription").html(data.topicDescriptione);
			$("#topicCreateTime").html(data.topicCreateTime);
			$("#endTime").html(data.topicEndTime);
			$("#topicDegree").html(data.topicDegree);
			$("#topicUrl").html(
					"<a target='_blank' href='" + data.topicUrl + "'>"
							+ data.topicUrl + "</a>");
		}
	};
})();

/** **********************************control*********************************** */
(function() {
	var userMsg = window.sessionStorage;
	window.userId = userMsg.getItem("userId");

	window.page = {
		pageSize : 0,
		pageNum : 5
	};
	window.getWindow_friendId = null;
	window.getWindow_topicId = null;

	// 做删除和更新时使用的id
	window.li_selectedId = null;
	// 发送消息时使用的id
	window.recipientid = null;
	window.recipienttype = null;

	var parent_window = window.parent.window.location.href;
	var strs = parent_window.split("/");
	window.parentType = strs[strs.length - 1];

	if (window.parentType == "users.html") {
		window.recipienttype = "persion";
		window.view.changeToUsersModel();
		window.model.initAllFriendWindow({
			userId : window.userId
		});
		window.model.getFriendList(window.view.showFriend, {
			userId : window.userId
		});
	} else if (window.parentType == "topic.html") {
		window.recipienttype = "group";
		window.view.changeToTopicModel();
		window.model.initAllTopicWindow({
			userId : window.userId
		});
		window.model.getTopicList(window.view.showTopic, {
			userId : window.userId
		});
	}
})();

/** **********************************event*********************************** */
(function() {
	window.imgError = function(that){
		that.src = "../img/icon_unkown_people.png";
	};
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
	};
	window.friendOver = function(that) {
		$(that).css("background", "#A55540");
		$(that).children("div").eq(1).children('div').eq(2).show();
	};

	window.friendOut = function(that) {
		$(that).css("background", "lightgoldenrodyellow");
		$(that).children("div").eq(1).children('div').eq(2).hide();
	};

	window.clickFriend = function(that) {
		$("#userInputMsg").removeAttr("readonly");
		$("#userInputMsg").css("cursor","auto");
		
		window.page.pageSize = 0;
		$(".statement").html("");

		var friendId = $(that).attr("data-selectedId");
		window.recipientid = friendId;
		var target = $(that).find(".newmsg");
//		var missNum = $(target).attr("data-missnum");
		$(that).parent().find(".selected_li").removeClass("selected_li");
		$(that).addClass("selected_li");

		window.model.getUserMsg(window.view.showUserMsg, {
			userId : friendId
		});

		if (window.getWindow_friendId != null)
			window.model.closeOrOpenFriendWindow({
				userId : window.userId,
				friendId : window.getWindow_friendId,
				status : 0
			// 0为窗口关闭状态 1为窗口打开状态
			});

		window.model.updateFriendWindowAndMissMsg({
			userId : window.userId,
			friendId : friendId
		});

			window.page.userId = window.userId;
			window.page.friendId = friendId;
			if (window.page.friendId == null)
				return;
			window.model.getFriendMsg(window.view.showContactMessage,
					window.page);

		window.getWindow_friendId = friendId;
		$(target).attr("data-missnum", '0');
		$(target).addClass("hide");
		$("#userInputMsg").focus();
		$("#userInputMsg").val("");
	};
	window.clickTopic = function(that) {
		$("#userInputMsg").removeAttr("readonly");
		$("#userInputMsg").css("cursor","auto");
		
		window.page.pageSize = 0;
		$(".statement").html("");

		var topicId = $(that).attr("data-selectedId");
		window.recipientid = topicId;
		var target = $(that).find(".newmsg");
//		var missNum = $(target).attr("data-missnum");
		$(that).parent().find(".selected_li").removeClass("selected_li");
		$(that).addClass("selected_li");

		window.model.getTopicMsg(window.view.showTopicMsg, {
			topicId : topicId
		});
		if (window.getWindow_topicId != null) {
			window.model.updateTopicWindowStatus({
				userId : window.userId,
				topicId : window.getWindow_topicId,
				status : 0
			});
		}
		window.model.updateTopicWindowAndMissMsg({
			userId : window.userId,
			topicId : topicId
		});

		window.page.topicId = topicId;
		if (window.page.topicId == null)
			return;
		window.model.getMeetMsg(window.view.showMeetMsg, window.page);

		window.getWindow_topicId = topicId;
		$(target).attr("data-missnum", '0');
		$(target).addClass("hide");
		$("#userInputMsg").focus();
		$("#userInputMsg").val("");
	};

	window.commintUpdateOrDelete = function(that) {
		var delete_ele = $(that).parent().parent().find("span:not(.hide)");
		var update_ele = $(that).parent().parent().find("input:not(.hide)");
		if ($(delete_ele).length > 0) {
			if (window.parentType == "users.html") {
				window.model.deleteFriend(window.view.deleteFriend, {
					userId : window.userId,
					friendId : window.li_selectedId
				});
			} else {
				window.model.deleteTopic(window.view.deleteFriend, {
					userId : window.userId,
					topicId : window.li_selectedId
				});
			}

		} else if ($(update_ele).length > 0) {
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
			window.page.topicId = window.getWindow_topicId;
			if (window.page.topicId == null)
				return;
			window.model.getMeetMsg(window.view.showMeetMsg, window.page);
		}
		window.page.pageSize++;
	};
	window.showUserMsg = function(that) {
		var userId = $(that).attr("data-userid");
		window.selected_user_id = userId;

		window.model.getUserMsg(window.view.showUserMsg, {
			userId : userId
		});
	};

	window.topicOver = window.friendOver;
	window.topicOut = window.friendOut;

	window.showUserMsg = function(that) {
		var userId = $(that).attr("data-userid");
		window.model.getUserMsg(window.view.showUserMsg, {
			userId : userId
		});
	};
})();

// 初始化window为0 groups、friend

/** *********************************WebSocket************************** */
(function() {
	var userMsg = window.sessionStorage;
	var userId = userMsg.getItem("userId");

	var websocket = null;
	if ("WebSocket" in window) {
		//websocket = new WebSocket("ws://localhost:8080/Topic/mywebsocket");
		websocket = new WebSocket("ws://112.74.44.49:8080/Topic/mywebsocket");
		// websocket = new
		// WebSocket("ws://192.168.191.4:8080/Topic/mywebsocket");
	} else {
		coonsole.log("Current Brower Not Support WebSocket");
	}
	;
	websocket.oneror = function() {
		console.log("error in websocket");
	};
	websocket.onopen = function() {
		console.log("open websocket");
		var obj = {
			type : "onOpen",
			userid : userId
		}
		websocket.send(JSON.stringify(obj));
	};
	websocket.onmessage = function(event) {
		showMeetMsg(JSON.parse(event.data));
	};
	websocket.onclose = function() {
		var obj = {
			type : "onClose",
			userid : userId
		}
		websocket.send(JSON.stringify(obj));
	};
	window.onbeforeunload = function() {
		var obj = {
			type : "onClose",
			userid : '2'
		};
		websocket.send(JSON.stringify(obj));
		websocket.close();
	};
	window.clearMessage = function(){
		$("#userInputMsg").val("");
	};
	window.sendMessage = function() {
		var msg = $("#userInputMsg").val();
		var length = msg.replace(/(^\s*)|(\s*$)/g, "").length;
		if(length <= 0){
			return;
		}
		var data = {
			type : "onMessage",
			senderid : userId,
			recipientid : window.recipientid,
			recipienttype : window.recipienttype,
			msg : msg.replace(/\n/g, "<br />")
		};
		websocket.send(JSON.stringify(data));
		$("#userInputMsg").val("");
	};
	document.onkeydown = function(event){
		var key = event.keyCode;
		if(event.shiftKey && key == 13){
			window.sendMessage();
			$("#userInputMsg").val("");
		}
	}
	function showMeetMsg(data) {
		var recipientid = data.recipientid;
		var recipienttype = data.recipienttype;
		var userCome = data.senderid;
		var convContent = data.msg;
		var convTime = data.time;
		var userPhoto = data.userPhoto;
		var html = "";
		if (userCome == window.userId || userCome == recipientid) {
			html += "<li ><div class='right'><div class='content'><p><time>"
					+ convTime
					+ "</time></p><p><span>"
					+ convContent
					+ "</span></p></div><div class='headImg'><img onerror='imgError(this)' title='' src='"
					+ userPhoto + "' /></div></div></li>";
		} else if ((recipientid == userId && userCome == window.recipientid
				&& recipienttype == window.recipienttype) || (recipientid == window.recipientid)) {
			html += "<li><div  class='left'><div class='headImg'><img onerror='imgError(this)' title='' src='"
					+ userPhoto
					+ "' /></div><div class='content'><p><time>"
					+ convTime
					+ "</time></p><p><span>"
					+ convContent
					+ "</span></p></div></div></li>";
		}else if (recipientid == userId) {
			if (recipienttype == 'persion') {
				var ele = $(".usersList").find("[data-selectedid='" + userCome + "']").find('.newmsg');
				$(ele).removeClass("hide");
				$(ele).attr("data-missnum", '1');
			} else if (recipienttype == 'group') {
				var ele = $(".topicList").find("[data-selectedid='" + userCome + "']").find('.newmsg');
				$(ele).removeClass("hide");
				$(ele).attr("data-missnum", '1');
			}
		}
		$(".statement").append(html);
		var boxHeight = $(".statement").parent().height();
		var moreBoxHeight = $(".statement").parent().children("div").eq(0)
				.height();
		var docHeight = $(".statement").height();
		$(".statement").parent().scrollTop(
				docHeight + moreBoxHeight - boxHeight);
	}
})();
