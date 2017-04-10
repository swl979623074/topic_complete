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
			$.get("/Topic/newsTypeController/getNewsType", function(data) {
				var status = data.status;
				if (status == "SUCCESS") {
					var list = data.list;
					cb_showNewsType(list);
				} else {
					console.log("init newsType false");
				}
			});
		},
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
				typeAlias = typeAlias.split("").join("&nbsp");
				html += "<li onclick='addClickEventToNewsType(this)' data-type='"
						+ typeId + "'>" + typeAlias + "</li>"
			}
			$("#newsTypeList").html(html);
		},
		toSelectModel : function(that) {
			$("[class='newsTypeHover']").removeClass("newsTypeHover");
			$(that).addClass("newsTypeHover");
		}
	}
})();

/** ***************************control**************************** */
(function() {
	window.model.initNewsType(window.view.showNewsType);
})();

/** ***************************event**************************** */
(function() {
	window.addClickEventToNewsType = function(that) {
		window.view.toSelectModel(that);
		console.log($(that).attr("data-type"));
	};
	window.openDialog = function(that) {
		$(".createTopic").removeClass('hide');
	};
	window.closeDialog = function() {
		$(".createTopic").addClass('hide');
	};

	window.createTopic = function() {

	};
	console.log($(".toutiao").html())
})();

/** ***************************tool**************************** */
(function() {
	window.tool = {
		getValues : function() {

		}
	}
})();