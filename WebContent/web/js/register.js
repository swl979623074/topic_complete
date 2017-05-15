(function() {
	window.onresize = function() {
		throttle(window.onload);
	};

	function throttle(method, content) {
		clearTimeout(method.tId)
		method.tId = setTimeout(function() {
			method.call(content);
		}, 100);
	}
	;

	window.onload = function() {
		html = document.getElementsByTagName("html")[0];
		html.style.fontSize = window.innerWidth / 100 + "px";
	};
})();

(function() {
	var flag = "../img/icon_confrim.png";
	$.get("/Topic/newsTypeController/getNewsType", function(data) {
		var status = data.status;
		if (status == "SUCCESS") {
			var html = "";
			var list = data.list;
			var len = list.length;
			for ( var i = 0; i < len; i++) {
				var typeName = list[i].typeName;
				var typeAlias = list[i].typeAlias;
				html += "<div onclick='clickProfession(this)' data-type='"
						+ typeName + "'><div><span>" + typeAlias
						+ "</span></div><div class='hide'><img src='" + flag
						+ "' /></div></div>";
			}
			$(".newsType").html(html);
		} else {
			console.log("init profession false");
		}
	});

})();
(function() {
	window.clickProfession = function(that) {
		$(that).find("img").parent().toggleClass('hide');
	}
})();
// 添加注册事件
(function() {
	function isNullOrUndefined(str) {
		if (undefined == str || null == str) {
			return true;
		} else {
			return false;
		}
	}
	function checkUserAlias(userAlias) {
		var reg_account = /^[A-Za-z0-9]{1,5}$/;
		var key = reg_account.test(userAlias);
		return key;
	}
	;
	function checkUserAccount(userAccount) {
		var reg_account = /^[A-Za-z0-9]{8,16}$/;
		var key = reg_account.test(userAccount);
		// key = !isNullOrUndefined(userAccount);
		return key;
	}
	;
	function checkUserPasswd(userPasswd, reUserPasswd) {
		var arr = [];
		var reg_passwd = /^[A-Za-z0-9]{6,20}$/;
		var key_passwd = reg_passwd.test(userPasswd);
		var key_same = userPasswd == reUserPasswd ? true : false;
		var key_passwd_null = !isNullOrUndefined(userPasswd);
		var key_rePasswd_null = !isNullOrUndefined(reUserPasswd);
		arr.push(key_passwd);
		arr.push(key_same);
		arr.push(key_passwd_null);
		arr.push(key_rePasswd_null);
		return arr;
	}
	;
	function checkUserEmial(userEmial) {
		var reg_emial = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		var key_emial = reg_emial.test(userEmial);
		return key_emial;
	}
	;
	function getSex() {
		var sex = $("input[name = 'sex']:checked").val();
		return sex;
	}
	function getProfession() {
		var strProfession = $("#userProfession").val();
		console.log("userProfession", strProfession);
		var str = strProfession.replace(/，/ig, ",");
		return str.split(",");
	}
	;
	function getInterest() {
		var interest = [];
		var hideScope = $(".newsType>div div:nth-child(2):not([class *= hide])");
		hideScope.map(function(index) {
			var type = $(hideScope[index]).parent().attr("data-type");
			interest.push(type);
		})
		return interest;
	}
	;
	function reSet() {
		$(".baseMessage li span").removeClass("error");
	}
	function userRegister() {
		reSet();

		var key = true;

		var userAlias = $("#userAlias").val();
		var userAccount = $("#userAccount").val();
		var userPasswd = $("#userPasswd").val();
		var reUserPasswd = $("#reUserPasswd").val();
		var userEmial = $("#userEmial").val();

		key = checkUserAlias(userAlias);
		if (key == false) {
			$("#userAlias").next().addClass("error");
			return;
		}

		key = checkUserAccount(userAccount);
		if (key == false) {
			$("#userAccount").next().addClass("error");
			return;
		}

		var keyArr = checkUserPasswd(userPasswd, reUserPasswd);
		if (keyArr[0] == 0 || keyArr[2] == 0) {
			$("#userPasswd").next().addClass("error");
			return;
		} else if (keyArr[1] == 0 || keyArr[3] == 0) {
			$("#reUserPasswd").next().addClass("error");
			return;
		}

		key = checkUserEmial(userEmial);
		if (key == false) {
			$("#userEmial").next().addClass("error");
			return;
		}

		key = $("input[name = 'summary']").is(":checked");
		if (key == false) {
			alert("请同意使用协议");
			return;
		}
		var userData = {
			userAlias : userAlias,
			userAccount : userAccount,
			userPasswd : userPasswd,
			userEmial : userEmial,
			userSex : getSex() == "boy" ? "男" : "女",
			userProfession : getProfession().toString(),
			userInterest : getInterest().toString()
		}

		$.post("/Topic/registerController/registeUser", userData,
				function(data) {
					if (data.status == 'SUCCESS') {
						alert("注册成功，跳转到登陆界面！！");
						window.location.href = "./../html/login.html";
					} else {
						alert("Error: " + data.msg);
					}
				})
	}

	$(document).keydown(function(e) {
		if (13 == e.keyCode) {
			userRegister();
		}
	});
	$("#btn_register").click(function() {
		userRegister();
	});
})();