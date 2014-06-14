$(function () {
    $("#server-dd").change(function(){
	    var selectedServer = $("#server-dd option:selected").text();
		$("#service-dd").find('option').remove();
		$("#operation-dd").find('option').remove();
		if(selectedServer==''){
			triggerCollect();
		}
		else{
			populateServicesCombo(selectedServer);		
		}
	});
    $("#service-dd").change(function(){
	    var selectedServer = $("#server-dd option:selected").text();
        	var selectedService = $("#service-dd option:selected").text();
		$("#operation-dd").find('option').remove();
		if(selectedService==''){
        		triggerCollect();
		}
		else{
			populateOperationsCombo(selectedServer,selectedService);		
		}
	});
	$("#operation-dd").change(function(){
		triggerCollect();	
	});
    //$("#service-dd").ufd({log:true});
    //$("#operation-dd").ufd({log:true});
    $("#clearSelectionBtn").click(function(){
        $("#server-dd option:first-child").attr("selected", "selected");
	$("#service-dd").find('option').remove();
        $("#operation-dd").find('option').remove();
	triggerCollect();
        $("#service-dd").find('option').remove();
        $("#operation-dd").find('option').remove();
	triggerCollect();
    });
    $("#process-dd button").click(function(){
        $("#process-dd button").removeClass('btn-primary');
        $(this).addClass('btn-primary');
        triggerCollect();
    });
     $("#packageName-dd").change(function(){
	    var selectedServer = $("#packageName-dd option:selected").text();
		$("#packageName-dd").find('option').remove();
		
		if(selectedServer==''){
			triggerCollect();
		}
		else{

			var options = "<option value='__default__'></option>";
			//for(var i=0;i<result.length;i++){
				//var data = result[i];
				//for(var key in data){
					options = options + "<option>packageId1</option>";
					options = options + "<option>packageId2</option>";

				//}
			//}
            
            $("#packageName-dd").append(options);
		    triggerCollect();//$("#service-dd").ufd({log:true,addEmphasis: true});

			//populateServicesCombo(selectedServer);		
		}
	});
 
});
function triggerCollect(){
        var selectedServer = $("#packageName-dd").find('option:selected').text();
        var selectedService = $("#service-dd").find('option:selected').text();
        var selectedOperation = $("#operation-dd").find('option:selected').text();
        var temp = $("#process-dd button.btn-primary").text();
        var timeGrouping = getProcessType(temp);

        reloadIFrame({server:selectedServer,
            service:selectedService,operation:selectedOperation,timeGroup:timeGrouping});
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
    var server = param.server || "";
    var service = param.service || "";
    var operation = param.operation || "";
    var t = param.timeGroup || "";
    $("iframe").each(function(){
        //var id = $(this).attr('id');
        var currentUrl = $(this).attr('src');
        if(currentUrl.indexOf('?')){
            var absUrl = currentUrl.split('?');
            var absUrlNew = absUrl[0].split('/');
            currentUrl = absUrlNew[0];
        }

        if(t=="human")
        {
        	currentUrl=currentUrl+"/human_info.jag?stat=Task&task=All";
        }
        else
        {
        	currentUrl=currentUrl+"/bpel_info.jag?package=All&inst=All";
        }

        var newUrl = currentUrl;

        $(this).attr('src',newUrl);
    });
};
function populateCombo(id,data){
	
}
$(document).ready(function(){
	$.ajax({
       		url:'populate_combos_ajaxprocessor.jag',
		dataType:'json', 
		success:function(result){
			
			var options = "<option value='__default__'></option>";
			options = options + "<option>"+"All"+"</option>"
			options = options + "<option>"+"ClaimsApprovalTask"+"</option>"
			for(var i=0;i<result.length;i++){
				var data = result[i];
				for(var key in data){
					options = options + "<option>"+data[key]+"</option>"
				}
			}
            $("#server-dd").find('option').remove();
            $("#server-dd").append(options);
		    //$("#server-dd").ufd({log:true,addEmphasis: true});
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
    /*$.getJSON("populate_combos_ajaxprocessor.jag?server=10.150.3.174:9443",
        function(data){
          alert(data);
        });*/
});
function populateServicesCombo(server){
     $.ajax({
       		url:'populate_combos_ajaxprocessor.jag?server='+server+'',
		dataType:'json',
		success:function(result){

			var options = "<option value='__default__'></option>";
			for(var i=0;i<result.length;i++){
				var data = result[i];
				for(var key in data){
					options = options + "<option>"+data[key]+"</option>"
				}
			}
            
            $("#service-dd").append(options);
		    triggerCollect();//$("#service-dd").ufd({log:true,addEmphasis: true});
  	    }
	

	});
	
};
function populateOperationsCombo(server,service){

     $.ajax({
       		url:'populate_combos_ajaxprocessor.jag?server='+server+'&service='+service+'',
		    dataType:'json',
		success:function(result){

			var options = "<option value='__default__'></option>";
			for(var i=0;i<result.length;i++){
				var data = result[i];
				for(var key in data){
					if(data[key]!==null){
						options = options + "<option>"+data[key]+"</option>";
					}
				}
			}
            
            $("#operation-dd").append(options);
		triggerCollect();    //$("#operation-dd").ufd({log:true,addEmphasis: true});
  	    }

	 });
	
};


