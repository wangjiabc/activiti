
$('#model').click(function(){
   $('#content').load('pages/reModel.html');
   $('#content').attr('src','pages/reModel.html');
 });

$('#procdef').click(function(){
   $('#content').load('pages/reProcdef.html');
   $('#content').attr('src','pages/reProcdef.html');
 });

$('#attachMent').click(function() {
	$('#content').load('pages/attachMent.html');
	$('#content').attr('src', 'pages/attachMent.html');
});

$('#historicProcess').click(function() {
	$('#content').load('pages/historicProcess.html');
	$('#content').attr('src', 'pages/historicProcess.html');
});

// 定义通用方法

function setSuccess(message) {
	if (!message) {
		$("#operate-success").text("Well, it works!");
	} else {
		$("#operate-success").text(message);
	}
	//$("#operate-success").show();
	$("#operate-success").css("visibility", "visible");
	window.setTimeout(function() {
		//$("#operate-success").hide();
		$("#operate-success").css("visibility", "hidden");
	}, 2000);
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
    }

