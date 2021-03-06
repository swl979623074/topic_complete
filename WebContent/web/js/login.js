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
	;

	window.onload = function() {
		html = document.getElementsByTagName("html")[0];
		html.style.fontSize = window.innerWidth / 100 + "px";
	};
})();

(function() {
	function userLogin() {
		var userName = $("#userName").val();
		var passwd = $("#passwd").val();
		if (userName == null || userName == '' || passwd == null
				|| passwd == '') {
			alert("userName and passwd cannot be null");
		} else {
			$.post("/Topic/loginController/login", {
				userName : userName,
				passwd : passwd
			}, function(result) {
				console.log(result);
				var status = result.status;
				if ("SUCCESS" == status) {
					window.sessionStorage.setItem("userId", result.userId);
					window.sessionStorage.setItem("userAccount",
							result.userAccount);
					window.sessionStorage
							.setItem("userAlias", result.userAlias);
					window.sessionStorage
					.setItem("userDegree", result.userDegree);
					window.location.href = './../html/home.html';
				} else {
					alert("login false");
					$("#userName").val("");
					$("#passwd").val("");
				}
			})
		}
	};
	$("#signIn").click(userLogin);
	$("#register").click(function(){
		window.location.href = './../html/register.html';
	})
	$(document).keydown(function(e){
		if(e.keyCode == 13){
			userLogin();
		}
	});
})();