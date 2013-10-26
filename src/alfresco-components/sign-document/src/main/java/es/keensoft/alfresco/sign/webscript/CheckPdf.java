package es.keensoft.alfresco.sign.webscript;

import java.io.IOException;

import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.repository.ContentData;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.webscripts.AbstractWebScript;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.WebScriptResponse;

import com.google.gson.Gson;

import es.keensoft.alfresco.sign.webscript.bean.BasicResponse;

public class CheckPdf extends AbstractWebScript {
	private static Log log = LogFactory.getLog(CheckPdf.class);

	private static final String PDF_MIMETYPE = "application/pdf";
	public static final String RESPONSE_CODE_IS_PDF = "IS_PDF";
	public static final String RESPONSE_CODE_IS_NOT_PDF = "IS_NOT_PDF";
	
	private NodeService nodeService;	

	@Override
	public void execute(WebScriptRequest req, WebScriptResponse res) throws IOException {
		Gson gson = new Gson();
		BasicResponse response = new BasicResponse();

		try {
			String docNodeRefStr = req.getParameter("nodeRef");
			NodeRef nodeRef = new NodeRef(docNodeRefStr);

			if (isPDF(nodeRef)) {
				response.setCode(RESPONSE_CODE_IS_PDF);
			} else {
				response.setCode(RESPONSE_CODE_IS_NOT_PDF);
			}
			
			response.setNodeRef(nodeRef.getId());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new WebScriptException(e.getMessage(), e);
		}

		res.getWriter().write(gson.toJson(response));
	}

	private boolean isPDF(NodeRef nodeRef) {
		String mimetype;

		ContentData contentData = (ContentData) nodeService.getProperty(nodeRef, ContentModel.PROP_CONTENT);
		mimetype = contentData.getMimetype();
		log.debug("Content mimetype: " + mimetype);

		return PDF_MIMETYPE.equals(mimetype);
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}
}
