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
					console.log("init profession false");
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
		updateUserMsg : function(data) {
			$.post("/Topic/registerController/updateuserMsg", data, function(
					data) {
				if (data.status == "SUCCESS") {
					console.log("update success!!");
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
				html += "<div>" + list[i] + "</div>";
			}
			$("#profession").html(html);
		},
		toEditModel : function() {
			$('#alias').attr("contenteditable", 'true').parent().css(
					"border-color", '#836FFF');
			$('#sex').attr("contenteditable", 'true').parent().css(
					"border-color", '#836FFF');
			$('#emial').attr("contenteditable", 'true').parent().css(
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
			$('#alias').attr("contenteditable", 'false').parent().css(
					"border-color", '#CCC');
			$('#sex').attr("contenteditable", 'false').parent().css(
					"border-color", '#CCC');
			$('#emial').attr("contenteditable", 'false').parent().css(
					"border-color", '#CCC');
			$("#add_profession_btn").remove();
			$(".newsType img").parent("[class='hide']").parent().hide();
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
	var userMsg = window.sessionStorage;
	var userId = userMsg.getItem("userId");
	if (userId != null) {
		window.model.initNewsTypeByUserId(window.view.showNewsType, false,
				userId);
		window.model.initProfession(window.view.showProfession, userId);
	}

	$("#edit").click(function() {
		$(this).attr("disabled", "disabled");
		$(this).addClass('disabled');
		window.view.toEditModel();
		window.model.initNewsType(window.view.showNewsType, true);
	});
	$("#save").click(function() {
		$("#edit").removeAttr("disabled");
		$("#edit").removeClass('disabled');
		console.log(window.tool.getValues());
		window.view.toNormalModel();
	});
})();

/** *******************tool******************** */
(function() {
	window.tool = {
		getValues : function() {
			var profession = [];
			var interest = [];
			$("#profession div").map(function() {
				profession.push($(this).html());
			});
			$(".newsType img").parent().not("[class='hide']").parent().map(
					function() {
						interest.push($(this).attr("data-type"));
					});
			var result = {
				alias : $("#alias").html(),
				sex : $("#sex").html(),
				emial : $("#emial").html(),
				profession : profession.splice(0, profession.length - 1).join(
						","),
				interest : interest.join(",")
			};
			return result;
		}
	}
})();
