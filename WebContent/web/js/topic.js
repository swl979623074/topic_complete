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


/** ****************************model***************************** */
(function() {
	window.model = {
		addTopic : function(topicMsg) {
			$.post("/Topic/topicController/insertOneTopic", topicMsg, function(
					msg) {
				if (msg.status == "SUCCESS") {
					console.log("create topic success");
				}
			});
		},
		getNewsType : function(cb_showNewsList) {
			$.get("/Topic/newsTypeController/getNewsType", function(msg) {
				var status = msg.status;
				if (status == "SUCCESS") {
					var list = msg.list;
					cb_showNewsList(list);
				} else {
					console.log("init newsType false");
				}
			});
		},
		createTopic : function(topicMsg) {
			$.post("/Topic/topicController/insertOneTopic", topicMsg, function(
					msg) {
				if (msg.status == "SUCCESS") {
					console.log("create topic success");
				}
			})
		}
	}
})();
/** ****************************view***************************** */
(function() {
	window.view = {
		showNewsType : function(datalist) {
			var len = datalist.length;
			var html = "";
			for ( var i = 0; i < len; i++) {
				var typeId = datalist[i].typeId;
				var typeAlias = datalist[i].typeAlias;
				html += "<option onclick='selectClick(this)' data-typeid='"
						+ typeId + "'>" + typeAlias + "</option>";
			}
			$(".newsTypeList").html(html);
			$(".newsTypeList").children("option").eq(0).attr("selected","");
		}
	}
})();
/** ****************************control***************************** */
(function() {
	window.model.getNewsType(window.view.showNewsType);
})();
/** ****************************event***************************** */
(function() {
	window.selectFocus = function(that) {
		$(that).attr("size", 5);
	};
	window.selectClick = function(that) {
		$(that).parent().removeAttr("size");
		$(that).parent().blur();
		$(that).parent().children("[selected='selected']").removeAttr(
				"selected");
		$(that).attr("selected", "");
	};
	window.createTopic = function(that) {
		var topicMsg = window.tool.getTopicMsg(that);
		var key = window.validator.checkTopicMsg(that, topicMsg);
		if (!key)
			return;
		window.model.createTopic(topicMsg);
		$(".createTopic").addClass('hide');
	};
	window.showDialog = function() {
		$(".createTopic").removeClass('hide');
	}
	window.hideDialog = function() {
		$(".createTopic").addClass('hide');
	}
	
})();
/** ****************************tool***************************** */
(function() {
	window.tool = {
		getTopicMsg : function(that) {
			var aside_box = $(that).parent().parent();
			var userMsg = window.sessionStorage;
			var topicMsg = {
				userId : userMsg.getItem("userId"),
				title : $(aside_box).find("#topic_title").val(),
				description : $(aside_box).find("#topic_description").val(),
				stillTime : $(aside_box).find("#topic_stilltime").val(),
				url : $(aside_box).find("#topic_url").val(),
				degree : userMsg.getItem("userDegree"),
				typeId : $("option[selected]").attr("data-typeid")
			};
			return topicMsg;
		}
	};
	window.validator = {
		checkTopicMsg : function(that, topicMsg) {
			var aside_box = $(that).parent().parent();
			var title = topicMsg["title"];
			if (title.length > 20 || title.length < 1) {
				$(aside_box).find("#topic_title").parent().addClass("error");
				return false;
			} else {
				$(aside_box).find("#topic_title").parent().removeClass("error");
			}

			var description = topicMsg["description"];
			if (description.length > 200 || description.length < 1) {
				$(aside_box).find("#topic_stilltime").parent()
						.addClass("error");
				return false;
			} else {
				$(aside_box).find("#topic_description").parent().removeClass(
						"error");
			}

			var stillTime = topicMsg["stillTime"];
			var reg_stillTime = /^[0-9]{1,2}$/;
			if (!reg_stillTime.test(stillTime)) {
				$(aside_box).find("#topic_stilltime").parent()
						.addClass("error");
				return false;
			} else {
				$(aside_box).find("#topic_stilltime").parent().removeClass(
						"error");
			}
			return true;
		}
	}
})();