package es.keensoft.alfresco.sign.webscript.bean;

public class SaveSignRequest {
	private String nodeRef;
	private String signerRole;
	private String dataToSign;
	private String signedData;
	private String signerData;

	public String getNodeRef() {
		return nodeRef;
	}

	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}

	public String getSignerRole() {
		return signerRole;
	}

	public void setSignerRole(String signerRole) {
		this.signerRole = signerRole;
	}

	public String getDataToSign() {
		return dataToSign;
	}

	public void setDataToSign(String dataToSign) {
		this.dataToSign = dataToSign;
	}

	public String getSignedData() {
		return signedData;
	}

	public void setSignedData(String signedData) {
		this.signedData = signedData;
	}

	public String getSignerData() {
		return signerData;
	}

	public void setSignerData(String signerData) {
		this.signerData = signerData;
	}

}
