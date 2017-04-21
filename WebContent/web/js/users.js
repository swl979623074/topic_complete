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

function showDialog() {
	$(".userMessage").html(" ");
	$(".addUserDialog").removeClass('hide');
}

function hideDialog() {
	$(".addUserDialog").addClass('hide');
}

/** ****************************model***************************** */
(function() {
	window.model = {
		getUserMsg : function(cb_showUserMsg, data) {
			$.post("/Topic/userController/getUserMsgByAccount", data, function(
					res) {
				if (res.status == "SUCCESS") {
					cb_showUserMsg(res.userMsg);
				}else{
					cb_showUserMsg("FAlSE");
				}

			});
		},
		addFrined : function(cb_closeDialog, data) {
			$.post("/Topic/userController/addFriend", data, function(res) {
				cb_closeDialog(res.status);
			});
		}
	}
})();
/** ****************************view***************************** */
(function() {
	window.view = {
		showUserMsg : function(data) {
			if(data == "FAlSE"){
				$(".userMessage").html("<p style='width:100%;text-align:center !important;color:red;font-szie:3rem;!important'>Can Not Find The User</p>");
				return;
			}
			
			var userAlias = data.userAlias;
			var userPhoto = data.userphoto;
			var userSex = data.userSex;
			var userDegree = data.userDegree;
			var userCreatetime = data.userCreatetime;
			var userAccount = data.userAccount;
			var userProfession = data.userProfession;
			var html = "<li><div><img src='"
					+ userPhoto
					+ "' alt='head photo' /></div><div><div><span>"
					+ userAlias
					+ "</span>&nbsp<span>"
					+ userSex
					+ "</span></div><div><label>账号:</label><span>"
					+ userAccount
					+ "</span></div><div><label>等级:</label><span>"
					+ userDegree
					+ "级</span></div></div></li><li><label>创建时间：</label><span>"
					+ userCreatetime
					+ "</span></li><li><label>Ta的职业:</label><span>"
					+ userProfession
					+ "</span></li><li><button onclick='addFriend(this)' data-friendaccount='"
					+ userAccount + "' title='添加为好友'>添加</button></li>";
			$(".userMessage").html(html);

		},
		closeDialog : function(data) {
			if (data == "SUCCESS") {
				$(".addUserDialog").addClass('hide');
			} else {
				$(".userMessage").html("<p style='width:100%;text-align:center !important;color:red;font-szie:3rem;!important'>Add Friend False</p>");
			}
		}
	}

})();

/** ****************************control***************************** */
(function() {
	window.addSerachUserEvent = function() {
		var userAccount = $('.userAccount').val();
		if (userAccount.length < 1) {
			alert("user account can not be null");
			return;
		}
		window.model.getUserMsg(window.view.showUserMsg, {
			userAccount : userAccount
		});
	}
	window.addFriend = function(that) {
		var data = {
				userId:	window.sessionStorage.getItem("userId"),
				firendAccount:$(that).attr("data-friendaccount")
		}
		window.model.addFrined(window.view.closeDialog,data);
	}
})();
