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
//区分google和Firefox
(function() {
	var browerType = navigator.userAgent;
	if (browerType.indexOf("Firefox") != -1) {
		$(".headImg").css("margin-top", "-30%");
	} else if (browerType.indexOf("Chrome") != -1) {
		$(".headImg").css("margin-top", "0px");
	}
})();

(function() {
	function toEditModel() {
		$('#alias').attr("contenteditable", 'true').parent().css("border-color",
				'#836FFF');
		$('#sex').attr("contenteditable", 'true').parent()
				.css("border-color", '#836FFF');
		$('#emial').attr("contenteditable", 'true').parent().css("border-color",
				'#836FFF');
		var div_add_button = $("<div id='profession' onclick='addProfessionEvent(this)' style='background:#836FFF;color:white;cursor:pointer'>+</div>");
		$("#profession").html(div_add_button);
		$("table").keydown(function(event){
			var key = event.keyCode;
			console.log(key)
			if(key == 13)
				return false;
		})
		initNewsType();
	}

	$("#edit").click(function() {
		$(this).attr("disabled", "disabled");
		$(this).addClass('disabled');
		toEditModel();
	});
	$("#save").click(function() {
		$("#edit").removeAttr("disabled");
		$("#edit").removeClass('disabled');
	});

})();
(function() {
	function showNewsType(list) {
		var flag = "../img/logo.png";
		var html = "";
		var len = list.length;
		for ( var i = 0; i < len; i++) {
			var typeName = list[i].typeName;
			var typeAlias = list[i].typeAlias;
			html += "<div onclick='clickNewsTypeEvent(this)' data-type='"
					+ typeName + "'><div><span>" + typeAlias
					+ "</span></div><div class='hide'><img src='" + flag
					+ "' /></div></div>";
		}
		$(".newsType").html(html);
	}
	window.addProfessionEvent = function(that) {
		$("<div contenteditable='true'></div>").insertBefore($(that));
	}
	window.clickNewsTypeEvent = function(that) {
		$(that).find("img").parent().toggleClass('hide');
	}
	window.initNewsType = function() {
		$.get("/Topic/newsTypeController/getNewsType", function(data) {
			var status = data.status;
			if (status == "SUCCESS") {
				var list = data.list;
				showNewsType(list);
			} else {
				console.log("init profession false");
			}
		});
	}
	
})();