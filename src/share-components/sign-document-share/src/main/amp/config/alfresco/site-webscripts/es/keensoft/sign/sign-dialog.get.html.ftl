<div id="signDialog" class="">
   <div class="hd">${msg("window.title")}</div>
   <div class="bd">		
     	<#if !jsonError>	     		
		    <div id="info" class="yui-gd" style="padding:30px;text-align:justify;">
			${msg("document.ready")}
				<div style="text-align:center;">
					<img src="/share/sign/icon.gif" >
				</div>
			</div>							  			
			<div id="sign-component" style="width:100%;"></div>	 
								
			<form id="signDialog-form" action="" method="POST">
				<input type="hidden" id="dataToSign" name="dataToSign" value="${base64NodeContent}" />
				<input type="hidden" id="signedData" name="signedData" value="" />
				<input type="hidden" id="signerData" name="signerData" value="" />
				<input type="hidden" id="signerRole" name="signerRole" value="" />
				<input type="hidden" id="mimeType" name="mimeType" value="${mimeType}" />
				<input type="hidden" id="nodeRef" name="nodeRef" value="${nodeRef}" />
									 
	         	<div class="bdft" style="display:none;">
		         	<input type="button" id="signDialog-ok" value="${msg("button.ok")}" />
		         	<input type="button" id="signDialog-cancel" value="${msg("button.cancel")}" />
		         </div>
		    </form>
	      	<script type="text/javascript">//<![CDATA[
	      		
	      		var loadingSignComponentInterval;
	      		var loadingFrameInterval = window.setInterval(checkZIndex, 500);
	      					      		
	      		function checkZIndex() {
	      					      			
	      			var zIndex = YAHOO.util.Dom.getStyle(this.signDialog.parentElement, "z-index");
	      			if(zIndex > 0) {
	      			
	      				window.clearInterval(loadingFrameInterval);		  		      				
	      				YAHOO.util.Dom.get("sign-component").innerHTML="<iframe scrolling='no' frameborder='0' allowTransparency='true' id='sign-frame' src='/share/sign/sign-frame.jsp?mimeType=${mimeType}' style='overflow:hidden;width:100%;border:none;height:0px;' />";			      				
	      				loadingSignComponentInterval = window.setInterval(doSign, 500);
	      				
	      			}		  		
	      		};	
	      		
	      		function doSign() {
	      			var miniApplet = YAHOO.util.Dom.get("sign-frame").contentWindow.document.getElementById('miniApplet');			      			
	      			if(miniApplet != null) {
	      			
	      				window.clearInterval(loadingSignComponentInterval);
	      				var signFrame = YAHOO.util.Dom.get("sign-frame");
	      				var signForm = YAHOO.util.Dom.get("signDialog-form");
	      				signFrame.contentWindow.doSign(signForm.dataToSign, signForm.signedData, signForm.signerRole);
	      				show_signed();
	      				
	      				var submitButton = YAHOO.util.Dom.get("signDialog-ok");
	      				submitButton.click();
	      				
	      			}						      					      			
	      		};
	      		
	      		function show_signed() {
	      			YAHOO.util.Dom.get("info").innerHTML="${msg("signed")}";			      			
	      		}		 
			//]]></script>	
			      				      	
  	    <#else>
  	      	<div class="yui-gd" style="padding:30px;text-align:justify;">
  				${msg("error.unknow")}
  			</div>
			<form id="signDialog-form" action="" method="POST">
	         	<div class="bdft">
	         		<input type="button" id="signDialog-cancel" value="${msg("button.ok")}" />
	         	</div>
	      	</form>      	
        </#if>
 	</div>
</div>