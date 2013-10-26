package es.keensoft.alfresco.sign.webscript;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.version.VersionModel;
import org.alfresco.service.cmr.coci.CheckOutCheckInService;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.version.VersionService;
import org.alfresco.service.cmr.version.VersionType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.I18NUtil;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import com.google.gson.Gson;

import es.keensoft.alfresco.sign.webscript.bean.BasicResponse;
import es.keensoft.alfresco.sign.webscript.bean.SaveSignRequest;


public class SaveSign extends AbstractWebScript {
	private static Log log = LogFactory.getLog(SaveSign.class);

	private static final String RETURN_CODE_OK = "OK";	
	
	private CheckOutCheckInService checkOutCheckInService;
	private VersionService versionService;
	private ContentService contentService;
	
	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
		Gson gson = new Gson();
		SaveSignRequest request;
		BasicResponse response = new BasicResponse();
		
		try {
			String jsonText = req.getContent().getContent();
			request = gson.fromJson(jsonText, SaveSignRequest.class);
			
			if(StringUtils.isBlank(request.getSignedData()))
				throw new WebScriptException("Signed data is empty or null.");
			
			log.debug("dataToSign:" + request.getDataToSign());
			log.debug("signedData" + request.getSignedData());
			log.debug("signerData" + request.getSignerData());
			
			storeSign(new NodeRef(request.getNodeRef()), request.getSignedData(), request.getSignerData());
			response.setCode(RETURN_CODE_OK);
		} catch (WebScriptException we) {
			log.error(ExceptionUtils.getFullStackTrace(we));
			throw we;
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new WebScriptException(e.getMessage(), e);
		}

		res.getWriter().write(gson.toJson(response));
	}

	private NodeRef storeSign(NodeRef nodeRef, String signedData, String signerData) throws IOException {
		NodeRef chkout;
		NodeRef chkin;
		
		try {
			versionService.ensureVersioningEnabled(nodeRef, null);
			log.debug("Versioning enabled for node.");		
			
			chkout = checkOutCheckInService.checkout(nodeRef);
			log.debug("Node checked out. Version: " + versionService.getCurrentVersion(nodeRef).getVersionLabel());
			
			ContentWriter writer = contentService.getWriter(chkout, ContentModel.PROP_CONTENT, true);
			OutputStream contentOutputStream = writer.getContentOutputStream();
			IOUtils.write(Base64.decodeBase64(signedData), contentOutputStream);
			contentOutputStream.close();
			log.debug("Node content updated.");
			
			chkin = checkOutCheckInService.checkin(chkout, getVersionProperties(signerData));
			log.debug("Node checked in. Version: " + versionService.getCurrentVersion(nodeRef).getVersionLabel());		
		} finally {
			if(checkOutCheckInService.isCheckedOut(nodeRef))
				checkOutCheckInService.cancelCheckout(nodeRef);
		}
	
		return chkin;
	}

	private Map<String, Serializable> getVersionProperties(String signerData) {
		Map<String, Serializable> versionProperties = new HashMap<String, Serializable>();
		versionProperties.put(VersionModel.PROP_VERSION_TYPE, VersionType.MAJOR);		
		versionProperties.put(VersionModel.PROP_DESCRIPTION, I18NUtil.getMessage("version.description") + signerData); 		
		return versionProperties;
	}

	public CheckOutCheckInService getCheckOutCheckInService() {
		return checkOutCheckInService;
	}

	public void setCheckOutCheckInService(CheckOutCheckInService checkOutCheckInService) {
		this.checkOutCheckInService = checkOutCheckInService;
	}

	public VersionService getVersionService() {
		return versionService;
	}

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

	public ContentService getContentService() {
		return contentService;
	}

	public void setContentService(ContentService contentService) {
		this.contentService = contentService;
	}

}
