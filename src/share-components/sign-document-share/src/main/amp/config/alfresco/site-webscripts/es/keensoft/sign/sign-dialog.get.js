function main() {
	var urlCheckPdf = "/keensoft/sign/check-pdf?nodeRef=";	
	var urlBase64NodeContent = "/keensoft/sign/base64-node-content?nodeRef=";
	
	model.nodeRef = args.nodeRef;
	
	var checkResponse = jsonConnection(urlCheckPdf + args.nodeRef);
	if(checkResponse == null) {
		model.jsonError = true;
		return;
	}
		
	if(checkResponse.code == "IS_PDF") {
		var base64NodeContentResponse = jsonConnection(urlBase64NodeContent + args.nodeRef);
		if(base64NodeContentResponse == null) {
			model.jsonError = true;
			return;
		}
		
		model.base64NodeContent = base64NodeContentResponse.base64NodeContent;
		model.jsonResponse = checkResponse;	
		model.jsonError = false;
	} else if (checkResponse.code == "IS_NOT_PDF") {		
		model.jsonResponse = checkResponse;	
		model.jsonError = false;
	}	
}

main();

function getBase64Content(nodeRef) {
	model.base64NodeContent = jsonConnection(urlConvertToPdfa + args.nodeRef)
}

function jsonConnection(url) {
	logger.log("Opening connection to (alfresco): " + url)
	var connector = remote.connect("alfresco"),
		result = connector.get(url);

	logger.log("Connection result: " + result);
	if (result.status == 200) {		
		return eval('(' + result + ')')
	} else {
		return null;
	}
}