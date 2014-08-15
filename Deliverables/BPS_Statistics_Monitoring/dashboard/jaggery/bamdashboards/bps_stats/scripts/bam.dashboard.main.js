$(function () {
    $("#process-dd button").click(function(){
        $("#process-dd button").removeClass('btn-primary');
        $(this).addClass('btn-primary');
        triggerCollect();
 });

$("#tenant-dd").change(function(){

        var selectedTenant = $("#tenant-dd option:selected").text();
        triggerCollect();

    });

 $("#tenantClearBtn").click(function(){

    document.getElementById("tenant-dd").value = 'All';  
   
    triggerCollect();
    });
  
});
function triggerCollect(){

        var temp = $("#process-dd button.btn-primary").text();
        var timeGrouping = getProcessType(temp);

        var selectedTenant = $("#tenant-dd option:selected").text();


        reloadIFrame({timeGroup:timeGrouping,tenant:selectedTenant});
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
    var tenantId = param.tenant || "";

    $("iframe").each(function(){
        
        var currentUrl = $(this).attr('src');
        if(currentUrl.indexOf('?')){
            var absUrl = currentUrl.split('?');
            var absUrlNew = absUrl[0].split('/');
            currentUrl = absUrlNew[0];
        }

        if(t=="human")
        {
        	currentUrl=currentUrl+"/human_info.jag?stat=Task&task=All&inst=All&tenant="+tenantId;
        }
        else
        {
        	currentUrl=currentUrl+"/bpel_info.jag?package=All&inst=All&tenant="+tenantId;
        }

        var newUrl = currentUrl;

        $(this).attr('src',newUrl);
    });
};

$(document).ready(function(){

    $.ajax({
            url:'populate_tenants_ajaxprocessor.jag',
        dataType:'json', 
        success:function(result){
            
            var options = "<option value='All'>All</option>";
            for(var i=0;i<result.length;i++){
                var data = result[i];
                for(var key in data){
                    options = options + "<option>"+data[key]+"</option>"
                }
            }
            $("#tenant-dd").find('option').remove();
            $("#tenant-dd").append(options);
            
        }
        
    });


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



