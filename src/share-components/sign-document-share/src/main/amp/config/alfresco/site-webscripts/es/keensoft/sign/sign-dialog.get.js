function main() {
	
	var base64NodeContentResponse = jsonConnection("/keensoft/sign/base64-node-content?nodeRef=" + args.nodeRef);
	if(base64NodeContentResponse == null) {
		model.jsonError = true;
		return;
	}
	
	model.base64NodeContent = base64NodeContentResponse.base64NodeContent;
	model.mimeType = args.mimeType;
	model.nodeRef = args.nodeRef;
	model.jsonError = false;
}

main();

function jsonConnection(url) {
	
	var connector = remote.connect("alfresco"),
		result = connector.get(url);

	if (result.status == 200) {		
		return eval('(' + result + ')')
	} else {
		return null;
	}
}