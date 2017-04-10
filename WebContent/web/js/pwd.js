(function() {
	window.onresize = function() {
		throttle(window.onload);
	};

	function throttle(method, content) {
		clearTimeout(method.tId);
		method.tId = setTimeout(function() {
			method.call(content);
		}, 100);
	}

	window.onload = function() {
		html = document.getElementsByTagName("html")[0];
		html.style.fontSize = window.innerWidth / 100 + "px";
	};
})();

(function() {
	var validator = {
		checkNewPwd : function() {
			var newPwd = $("#newPwd").val();
			var reg_passwd = /^[A-Za-z0-9]{6,20}$/;
			var key_passwd = reg_passwd.test(newPwd);
			if (!key_passwd) {
				$("#newPwd").next().addClass("error");
				return false;
			} else {
				$("#newPwd").next().removeClass("error");
				return true;
			}
		},
		checkSame : function() {
			var newPwd = $("#newPwd").val();
			var reNewPwd = $("#reNewPwd").val();
			if (reNewPwd != newPwd) {
				$("#reNewPwd").next().addClass("error");
				return false;
			} else {
				$("#reNewPwd").next().removeClass("error");
				return true;
			}
		},
		showResult : function(rs) {
			if (rs == 'SUCCESS') {
				$("#prompt").removeClass("hide error").css("color", "blue")
						.html("密码修改成功");
			} else {
				$("#prompt").removeClass("hide").addClass("error").css("color", "red").html(
						"密码修改失败");
			}
		}
	};
	var tool = {
		getValues : function() {
			var userMsg = window.sessionStorage;
			var userId = userMsg.getItem("userId");
			var data = {
				userId : userId,
				oldPwd : $("#oldPwd").val(),
				newPwd : $("#newPwd").val(),
				reNewPwd : $("#reNewPwd").val()
			};
			return data;
		}
	};

	var model = {
		updateUserPwd : function(cb_show, msg) {
			$.post("/Topic/userController/updateUserPwd", msg, function(data) {
				cb_show(data.status);
			});
		}
	};

	function control() {
		if (!validator.checkNewPwd())
			return;
		if (!validator.checkSame())
			return;
		var msg = tool.getValues();
		model.updateUserPwd(validator.showResult, msg);
	};

	$("#btn_commit").click(function() {
		control();
	});
	document.onkeydown = function(e) {
		var key = e.keyCode;
		if (key == 13){
			control();
		}
			
	};
	
})();