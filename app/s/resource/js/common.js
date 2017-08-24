function openWindowUrl(url,title,model,width,height){
	var iframUrl='<iframe name="mainFrame" src='+url+'  width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>';
	if($("#modelDiv").length>0){
		$("#modelDiv").children("iframe").attr("src",url);
	}else{
		var div=$("<div id='modelDiv'>"+iframUrl+"</div>");
		div.appendTo(parent.document.body);
		parent.$('#modelDiv').window({    
			title:title||"",
		    width:width||600,    
		    height:height||400,    
		    modal:true   
		});  
	}
}