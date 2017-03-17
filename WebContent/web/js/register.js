(function(){
	window.onresize = function() {
		throttle(window.onload);
	};

	function throttle(method, content) {
		clearTimeout(method.tId)
		method.tId = setTimeout(function() {
			method.call(content);
		}, 100);
	};
	
	window.onload = function() {
	    html = document.getElementsByTagName("html")[0];
	    html.style.fontSize = window.innerWidth / 100 + "px";
	};
})();

(function(){
	function isNullOrUndefined(str){
		if(undefined == str || null == str){
			return true;
		}else{
			return false;
		}
	}
	function checkUserAlias(userAlias){
		var key = true;
		if(userAlias.length > 5 || isNullOrUndefined(userAlias)){
			key = false;
		};
		return key;
	};
	function checkUserAccount(userAccount){
		var reg_account = /^[A-Za-z0-9]{8,16}$/;
		var key = reg_account.test(userAccount);
		key = !isNullOrUndefined(userAccount);
		return key;
	};
	function checkUserPasswd(userPasswd,reUserPasswd){
		var arr = null;
		var reg_passwd = /^[A-Za-z0-9]{6,20}$/;
		var key_passwd = reg_passwd.test(userPasswd);
		var key_same = userPasswd == reUserPasswd ? true:false;
		var key_passwd_null = !isNullOrUndefined(userPasswd);
		var key_rePasswd_null = !isNullOrUndefined(reUserPasswd);
		arr.push(key_passwd);
		arr.push(key_same);
		arr.push(key_passwd_null);
		arr.push(key_rePasswd_null);
		return arr;
	};
	function checkUserEmial(userEmial){
		var reg_emial = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
		var key_emial = reg.emial.test(userEmial);
		return key_emial;
	};
	function getProfession(strProfession){
		var str = strProfession.replace("，",",");
		return str.split(",");
	};
	function getInterest(){};
	
	function userRegister(){
		var userAlias = $("#userAlias").val();
		var userAccount = $("#userAccount").val();
		var userPasswd = $("#userPasswd").val();
		var reUserPasswd = $("#reUserPasswd").val();
		
	}
	
	$(document).keydown(function(e){
		console.log(e.keyCode);
		if(13 == e.keyCode){
//			var key = checkUserAccount()
//			alert(key);
			
			var userAlias = $("#userAlias").val();
			var rs = getProfession(userAlias);
			alert(rs.length);
		}
	})
})();