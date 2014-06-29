$(function () {
    $("#process-dd button").click(function(){
        $("#process-dd button").removeClass('btn-primary');
        $(this).addClass('btn-primary');
        triggerCollect();
 });
  
});
function triggerCollect(){

        var temp = $("#process-dd button.btn-primary").text();
        var timeGrouping = getProcessType(temp);

        reloadIFrame({timeGroup:timeGrouping});
};

function getProcessType(value)
{
	var process = "bpel";

	if(value == "Human Task Related Stats")
	{
		process = "human";
	}

	return process;
};

function reloadIFrame(param){
    var params = param || {};
    var t = param.timeGroup || "";
    $("iframe").each(function(){
        
        var currentUrl = $(this).attr('src');
        if(currentUrl.indexOf('?')){
            var absUrl = currentUrl.split('?');
            var absUrlNew = absUrl[0].split('/');
            currentUrl = absUrlNew[0];
        }

        if(t=="human")
        {
        	currentUrl=currentUrl+"/human_info.jag?stat=Task&task=All&inst=All";
        }
        else
        {
        	currentUrl=currentUrl+"/bpel_info.jag?package=All&inst=All";
        }

        var newUrl = currentUrl;

        $(this).attr('src',newUrl);
    });
};

$(document).ready(function(){

    //If no user action, reload page to prevent session timeout.
    var wintimeout;
    function setWinTimeout() {
        wintimeout = window.setTimeout("location.reload(true)",1740000); //setting timeout for 29 minutes. Actual timeout is 30 minutes.
    }
    $('body').click(function() {
        window.clearTimeout(wintimeout);
        setWinTimeout();
    });
    setWinTimeout();
    
});



