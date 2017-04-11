(function() {
	window.onresize = function() {
		throttle(window.onload)
	};

	function throttle(method, content) {
		clearTimeout(method.tId)
		method.tId = setTimeout(function() {
			method.call(content)
		}, 100);
	}
	;

	window.onload = function() {
		html = document.getElementsByTagName("html")[0];
		html.style.fontSize = window.innerWidth / 100 + "px";
	};
})();

/** ***************************model**************************** */
(function() {
	window.model = {
		initNewsType : function(cb_showNewsType) {
			$.get("/Topic/newsTypeController/getNewsType", function(msg) {
				var status = msg.status;
				if (status == "SUCCESS") {
					var list = msg.list;
					cb_showNewsType(list);
				} else {
					console.log("init newsType false");
				}
			});
		},
		getNewsByType : function(cb_showNews, newsMsg) {
			$.post("/Topic/newsController/getNewsByTypeName", newsMsg,
					function(msg) {
						if (msg.status == "SUCCESS") {
							cb_showNews(msg.list);
						}
					});
		},
		createTopic : function(topicMsg) {
			$.post("/Topic/topicController/insertOneTopic",topicMsg,function(msg){
				if(msg.status == "SUCCESS"){
					console.log("create topic success");
				}
			})
		}
	}
})();
/** ***************************view**************************** */
(function() {
	window.view = {
		showNewsType : function(datalist) {
			var len = datalist.length;
			var html = "";
			for ( var i = 0; i < len; i++) {
				var typeId = datalist[i].typeId;
				var typeAlias = datalist[i].typeAlias;
				var typeName = datalist[i].typeName;
				typeAlias = typeAlias.split("").join("&nbsp");
				html += "<li onclick='addClickEventToNewsType(this)' data-id='"
						+ typeId + "' data-name='" + typeName + "'>"
						+ typeAlias + "</li>";
			}
			$("#newsTypeList").html(html);
		},
		toSelectModel : function(that) {
			$("[class='newsTypeHover']").removeClass("newsTypeHover");
			$(that).addClass("newsTypeHover");
		},
		showNews : function(list) {
			var html = "";
			var len = list.length;
			for ( var i = 0; i < len; i++) {
				var photoNum = list[i].miniimg.length;
				if (photoNum != 3) {
					html += window.tool.makeSimpleNewsHtml(list[i]);
				} else {
					html += window.tool.makeComplxNewsHtml(list[i]);
				}
				;
			}
			;
			if (window.newsMsg.pageSize == 0) {
				$(".newslist").html(html);
			} else {
				$(".newslist").append(html);
			}

		}
	}
})();

/** ***************************control**************************** */
(function() {
	window.newsMsg = {
		typeName : "ent",
		pageSize : 0,
		pageNum : 10
	};
	window.oldMousePoint = -1;
	window.newsTypeId = 2;
	window.topicUrl = "";

	window.model.initNewsType(window.view.showNewsType);
	window.model.getNewsByType(window.view.showNews, window.newsMsg);
})();

/** ***************************event**************************** */
(function() {
	window.addClickEventToNewsType = function(that) {
		window.view.toSelectModel(that);
		window.newsMsg.pageSize = 0;
		window.newsTypeId = $(that).attr("data-id");
		window.newsMsg.typeName = $(that).attr("data-name");
		window.model.getNewsByType(window.view.showNews, window.newsMsg);
		$("#box_scroll").prop('scrollTop',0);
	};
	window.openDialog = function(that) {
		$(".createTopic").removeClass('hide');
		window.topicUrl = $(that).attr("data-href");
	};
	window.closeDialog = function() {
		$(".createTopic").addClass('hide');
		window.topicUrl = "";
	};

	window.createTopic = function(that) {
		var topicMsg = window.tool.getTopicMsg(that);
		var key = window.validator.checkTopicMsg(that, topicMsg);
		if(!key)return;
		window.model.createTopic(topicMsg);
		$(".createTopic").addClass('hide');
	};
	window.newsScroll = function(that) {
		var scrollTop = that.scrollTop;
		var scrollHeight = that.scrollHeight;
		var divHeight = $(that).height();
		if (scrollTop + divHeight >= scrollHeight) {
			window.newsMsg.pageSize++;
			window.model.getNewsByType(window.view.showNews, window.newsMsg);
		}
	}
})();

/** ***************************tool**************************** */
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
				url : window.topicUrl,
				degree : userMsg.getItem("userDegree"),
				typeId : window.newsTypeId
			};
			return topicMsg;
		},
		makeSimpleNewsHtml : function(data) {
			var flag = "../img/logo.png";
			var title = data.topic;
			var description = data.brief;
			var source = data.date + " " + "来源：" + data.source;
			var photoUrl = data.lbimg[0].src;
			var topicUrl = "https://mini.eastday.com/a/" + data.url;
			var html = "<li class='model simple'><div><img src='"
					+ photoUrl
					+ "' /></div><div><header class='header'> <a target='_blank' href='"
					+ topicUrl
					+ "'>"
					+ title
					+ "</a></header><section><span>"
					+ description
					+ "</span></section><footer class='footer'><div><span>"
					+ source
					+ "</span></div><div><img src='"
					+ flag
					+ "' title='create topic' class='pointer' onclick='openDialog(this)' data-href='"
					+ topicUrl + "'/></div></footer></div></li>";
			return html;
		},
		makeComplxNewsHtml : function(data) {
			var flag = "../img/logo.png";
			var title = data.topic;
			var photoUrl = data.miniimg;
			var source = data.date + " " + "来源：" + data.source;
			var topicUrl = "https://mini.eastday.com/a/" + data.url;
			var html = "<li class='model complex'><header class='header'> <a target='_blank' href='"
					+ topicUrl
					+ "'>"
					+ title
					+ "</a></header><section><img src='"
					+ photoUrl[0].src
					+ "' /> <img src='"
					+ photoUrl[1].src
					+ "' /><img src='"
					+ photoUrl[2].src
					+ "' /></section> <footer class='footer'><div><span>"
					+ source
					+ "</span></div><div><img src='"
					+ flag
					+ "' title='create topic' class='pointer' onclick='openDialog(this)' data-href='"
					+ topicUrl + "'/></div></footer></li>";
			return html;
		}
	};
	window.validator = {
		checkTopicMsg : function(that, topicMsg) {
			var aside_box = $(that).parent().parent();
			for ( var key in topicMsg) {
				var topicKey = "#topic_" + key.toLowerCase();
				if (!$(topicKey))
					continue;
				if (topicMsg[key].length < 1) {
					$(aside_box).find(topicKey).parent().addClass("error");
					return false;
				} else {
					$(aside_box).find(topicKey).parent().removeClass("error");
				}
			}
			var stillTime = topicMsg["stillTime"];
			var reg_stillTime = /^[0-9]{1,2}$/;
			if(!reg_stillTime.test(stillTime)){
				$(aside_box).find("#topic_stilltime").parent().addClass("error");
				return false;
			} else {
				$(aside_box).find("#topic_stilltime").parent().removeClass("error");
			}
			return true;
		}
	}
})();
