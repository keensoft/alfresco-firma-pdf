package es.keensoft.alfresco.behaviour;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.ContentServicePolicies;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.site.SiteModel;
import org.alfresco.repo.version.VersionModel;
import org.alfresco.service.cmr.repository.AssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeRef.Status;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.version.VersionService;
import org.alfresco.service.cmr.version.VersionType;
import org.alfresco.service.namespace.QName;

import es.keensoft.alfresco.model.SignModel;

public class CustomBehaviour implements NodeServicePolicies.OnDeleteAssociationPolicy, ContentServicePolicies.OnContentUpdatePolicy {
	
	private static final String DOCUMENT_LIBRARY_COMPONENT_ID = "documentLibrary";
	
	private PolicyComponent policyComponent;
	private NodeService nodeService;
	private VersionService versionService;
	
	public void init() {
		policyComponent.bindAssociationBehaviour(NodeServicePolicies.OnDeleteAssociationPolicy.QNAME, 
				SignModel.ASPECT_SIGNATURE, new JavaBehaviour(this, "onDeleteAssociation", 
				NotificationFrequency.TRANSACTION_COMMIT));
		policyComponent.bindClassBehaviour(ContentServicePolicies.OnContentUpdatePolicy.QNAME, 
				ContentModel.TYPE_CONTENT, new JavaBehaviour(this, "onContentUpdate", 
				NotificationFrequency.TRANSACTION_COMMIT));
	}
	
	@Override
	public void onDeleteAssociation(AssociationRef nodeAssocRef) {
		
		// Delete SIGNED aspect on SIGNATURE deletion
		if (nodeService.exists(nodeAssocRef.getTargetRef())) {
		    nodeService.removeAspect(nodeAssocRef.getTargetRef(), SignModel.ASPECT_SIGNED);
		}
		
	}
	
	// Required for correct versioning sequence in Alfresco 4.2.c
	// Create initial version on upload new content
	@Override
	public void onContentUpdate(NodeRef nodeRef, boolean newContent) {
		
		Status nodeStatus = nodeService.getNodeStatus(nodeRef);
		if (nodeStatus != null && !nodeStatus.isDeleted()) {
			
            // Exclude Thumbnails, Comments...
			if (nodeService.getType(nodeService.getPrimaryParent(nodeRef).getParentRef()).isMatch(ContentModel.TYPE_FOLDER)) {
		
				// Only apply to contents on Document Library
				NodeRef grandpaRef = nodeService.getPrimaryParent(nodeRef).getParentRef();
				while (nodeService.getProperty(grandpaRef, SiteModel.PROP_COMPONENT_ID) == null || 
					  !nodeService.getProperty(grandpaRef, SiteModel.PROP_COMPONENT_ID).equals(DOCUMENT_LIBRARY_COMPONENT_ID)) {
					grandpaRef = nodeService.getPrimaryParent(grandpaRef).getParentRef();
					if (grandpaRef == null) break;
				}
				
				if (grandpaRef != null) {
					
				    // Only for new documents
				    if (!versionService.isVersioned(nodeRef)) {
				    
						// Create initial version
					    Map<String, Serializable> versionProperties = new HashMap<String, Serializable>();
					    versionProperties.put(VersionModel.PROP_VERSION_TYPE, VersionType.MAJOR);
					    
						if (!nodeService.hasAspect(nodeRef, ContentModel.ASPECT_VERSIONABLE)) {
							Map<QName, Serializable> versionProps = new HashMap<QName, Serializable>();
							versionProps.put(ContentModel.PROP_AUTO_VERSION_PROPS, new Boolean(false));
							nodeService.addAspect(nodeRef, ContentModel.ASPECT_VERSIONABLE, versionProps);
						}
						
						versionService.createVersion(nodeRef, versionProperties);
						
				    }
					    
				}
				
            }
		}
	}
	

	public PolicyComponent getPolicyComponent() {
		return policyComponent;
	}

	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}

	public NodeService getNodeService() {
		return nodeService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	public VersionService getVersionService() {
		return versionService;
	}

	public void setVersionService(VersionService versionService) {
		this.versionService = versionService;
	}

}
