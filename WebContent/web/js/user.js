(function() {
	window.onresize = function() {
		throttle(window.onload);
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
// 区分google和Firefox
(function() {
	var browerType = navigator.userAgent;
	if (browerType.indexOf("Firefox") != -1) {
		$(".headImg").css("margin-top", "-30%");
	} else if (browerType.indexOf("Chrome") != -1) {
		$(".headImg").css("margin-top", "0px");
	}
})();

/** *******************model******************** */
(function() {
	window.model = {
		initNewsType : function(cb_showView, editable) {
			$.get("/Topic/newsTypeController/getNewsType", function(data) {
				var status = data.status;
				if (status == "SUCCESS") {
					var list = data.list;
					cb_showView(list, editable);
				} else {
					console.log("init profession false");
				}
			});
		},
		initNewsTypeByUserId : function(cb_showView, editable, userId) {
			$.post("/Topic/newsTypeController/getNewsTypeByUserId", {
				userId : userId
			}, function(data) {
				var status = data.status;
				if (status == "SUCCESS") {
					var list = data.list;
					cb_showView(list, editable);
				} else {
					console.log("init newsType false");
				}
			});
		},
		initProfession : function(cb_showProfession, userId) {
			$.post("/Topic/professionController/getProfession", {
				userId : userId
			}, function(data) {
				var status = data.status;
				if (status == "SUCCESS") {
					var list = data.list;
					cb_showProfession(list);
				} else {
					console.log("init profession false");
				}
			});
		},
		updateUserMsg : function(msg) {
			$.post("/Topic/registerController/updateuserMsg", msg, function(
					data) {
				if (data.status == "SUCCESS") {
					console.log("update success!!");
				}
			});
		},
		initUserMsg : function(cb_showUserMsg, userId) {
			$.post("/Topic/userController/getUserMsg", {
				userId : userId
			}, function(data) {
				if (data.status == "SUCCESS") {
					cb_showUserMsg(data.userMsg);
				}
			});
		}
	};

})();

/** *******************view******************** */
(function() {
	window.view = {
		showNewsType : function(list, editable) {
			var flag = "../img/logo.png";
			var html = "";
			var len = list.length;
			var envent = "onclick='clickNewsTypeEvent(this)'";
			if (editable == false) {
				envent = "";
			}
			for ( var i = 0; i < len; i++) {
				var typeId = list[i].typeId;
				var typeAlias = list[i].typeAlias;
				html += "<div " + envent + " data-type='" + typeId
						+ "'><div><span>" + typeAlias
						+ "</span></div><div class='hide'><img src='" + flag
						+ "' /></div></div>";
			}
			$(".newsType").html(html);
		},
		showProfession : function(list) {
			var html = "";
			var len = list.length;
			for ( var i = 0; i < len; i++) {
				if (list[i] != "")
					html += "<div>" + list[i] + "</div>";
			}
			$("#profession").html(html);
		},
		toEditModel : function() {
			$('#alias').attr("contenteditable", 'true').parent().css(
					"border-color", '#836FFF');
			$('#sex').attr("contenteditable", 'true').parent().css(
					"border-color", '#836FFF');
			$('#email').attr("contenteditable", 'true').parent().css(
					"border-color", '#836FFF');
			var div_add_button = $("<div id='add_profession_btn' onclick='addProfessionEvent(this)' style='background:#836FFF;color:white;cursor:pointer'>+</div>");
			$("#profession").html(div_add_button);
			$("table").keydown(function(event) {
				var key = event.keyCode;
				console.log(key)
				if (key == 13)
					return false;
			});
		},
		toNormalModel : function() {
			$('#alias').parent().css("border-color", '#CCC');
			$('#sex').parent().css("border-color", '#CCC');
			$('#email').parent().css("border-color", '#CCC');
			$("[contenteditable]").attr("contenteditable", 'false');
			$("#add_profession_btn").remove();
			$(".newsType img").parent("[class='hide']").parent().hide();
		},
		showUserMsg : function(data) {
			$("#account").html(data.userAccount);
			$("#degree").html(data.userDegree);
			$("#alias").html(data.userAlias);
			$("#sex").html(data.userSex);
			$("#email").html(data.userEmail);
			$("#createtime").html(data.userCreatetime);
			$("#userHeaderImg").attr("src",data.userphoto);
		}
	};
	window.addProfessionEvent = function(that) {
		$("<div contenteditable='true'></div>").insertBefore($(that));
	};
	window.clickNewsTypeEvent = function(that) {
		$(that).find("img").parent().toggleClass('hide');
	};
})();

/** *******************control******************** */
(function() {
	$("#save").attr("disabled", "disabled");
	$("#save").addClass('disabled');

	var userMsg = window.sessionStorage;
	var userId = userMsg.getItem("userId");
	if (userId != null) {
		window.model.initUserMsg(window.view.showUserMsg, userId);
		window.model.initProfession(window.view.showProfession, userId);
		window.model.initNewsTypeByUserId(window.view.showNewsType, false,
				userId);
	}

	$("#edit").click(function() {
		$(this).attr("disabled", "disabled");
		$(this).addClass('disabled');
		$("#save").removeAttr("disabled");
		$("#save").removeClass('disabled');
		window.view.toEditModel();
		window.model.initNewsType(window.view.showNewsType, true);
	});
	$("#save").click(function() {
		if (!window.validator.checkSex())
			return;
		if (!window.validator.checkEmail())
			return;

		$(this).attr("disabled", "disabled");
		$(this).addClass('disabled');
		$("#edit").removeAttr("disabled");
		$("#edit").removeClass('disabled');
		window.view.toNormalModel();
		var msg = window.tool.getValues();
		msg.userId = userId;
		console.log(msg);
		window.model.updateUserMsg(msg);
	});
})();

/** *******************tool******************** */
(function() {
	window.tool = {
		getValues : function() {
			var profession = [];
			var interest = [];
			$("#profession div").map(function() {
				var data = $(this).html();
				if (data != "")
					profession.push(data);
			});
			$(".newsType img").parent().not("[class='hide']").parent().map(
					function() {
						interest.push($(this).attr("data-type"));
					});
			var result = {
				userAlias : $("#alias").html(),
				userSex : $("#sex").html().substring(0,1),
				userEmail : $("#email").html(),
				userProfession : profession.join(","),
				userInterest : interest.join(",")
			};
			return result;
		}
	};
	window.validator = {
		checkSex : function() {
			var val = $("#sex").html();
			if(val.indexOf("男") == -1 && val.indexOf("女") == -1){
				$("#sex").parent().css("border-color", 'red');
				return false;
			} else {
				$("#sex").parent().css("border-color", '#836FFF');
				return true;
			}
		},
		checkEmail : function() {
			var val = $("#email").html();
			var reg_email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
			var key_email = reg_email.test(val);
			if (key_email == false) {
				$("#email").parent().css("border-color", 'red');
				return false;
			} else {
				$("#email").parent().css("border-color", '#836FFF');
				return true;
			}
		}
	}
})();

/*************************头像上传*******************/
(function(){
	var userMsg = window.sessionStorage;
	var userAccount = userMsg.getItem("userAccount");
	console.log(userMsg);
	var key = "";
	var uploadBtn = $("#uploadBtn");
	$(uploadBtn).click(function() {
		$("#file"+key).click();
	});
	$("#file"+key).change(function(){
		uploadImg(key);
	});
	function uploadImg(key){
		if(!checkFile()){
			alert("请上传jpg、png、jpeg,且不超过5M！");
			return;
		}
		var key = "";
		$.ajaxFileUpload({
			url : '/Topic/imgUploadController/fileUpload?'+userAccount,
			type : 'post',
			secureuri : false, //一般设置为false
			fileElementId : 'file'+key, // 上传文件的id、name属性名
			dataType : 'text', //返回值类型，一般设置为json、application/json
			data:{userId:"fsadfasdf"},
			success : function(data, status) {
				var strs = data.match( /{(\S*)}/ig);
				var jsonStr = JSON.parse(strs[0]);
				var binatyImg = jsonStr.binaryImg;
				$("#userHeaderImg").attr("src",binatyImg);
			},
			complete:function(){
				var _obj = $("#file"+key);
				_obj.replaceWith("<input type='file' id='file"+key+"' name='file"+key+"' style='display:none;'>");
				
				$(uploadBtn).click(function() {
					$("#file"+key).click();
				});
				
				$("#file"+key).change(function(){
					uploadImg(key);
				});
			},
			error : function(data, status, e) {
				console.log(e);
			}
		});
	};
	function checkFile(){
		var key = true;
		var size = $("#file")[0].files[0].size;
		var mid = size / 1024;
		if(mid >= 5000){
			key = false;
		}
		var type = $("#file")[0].files[0].name;
		var reg = /(jpg|png|jpeg)$/ig;
		var rs = reg.test(type);
		if(rs == false){
			key = false;
		}
		return key;
	}
})();


