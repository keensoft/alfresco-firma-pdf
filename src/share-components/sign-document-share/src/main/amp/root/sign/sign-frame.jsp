<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
	<script type="text/javascript" src="<%=request.getContextPath()%>/sign/miniapplet.js"></script>
</head>

<body>	
	<script type="text/javascript">
	
	    MiniApplet.cargarMiniApplet('<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()%>/sign');
	    
	    <% if (request.getParameter("mimeType").equals("pdf")) { %>
			
			var params = "signaturePage=1\nsignaturePositionOnPageLowerLeftX=120\nsignaturePositionOnPageLowerLeftY=50\nsignaturePositionOnPageUpperRightX=220\nsignaturePositionOnPageUpperRightY=150\n";
			
			function doSign(dataToSign, signedData, signerRole) {
				signedData.value = MiniApplet.sign(dataToSign.value, "SHA256withRSA", "PAdES", params, null, null);			
			}
			
		<% } else { %>
		
		    var params="mode=explicit";
		
			function doSign(dataToSign, signedData, signerRole) {
				signedData.value = MiniApplet.sign(dataToSign.value, "SHA256withRSA", "CAdES", params, null, null);			
			}

		<% } %>
		
	</script>		
</body>
</html>