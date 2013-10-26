(function() {
	YAHOO.Bubbling.fire("registerAction", {
		actionName : "onActionSign",
		fn : function sign_action(file) {
			this.widgets.waitDialog = Alfresco.util.PopupManager.displayMessage({
				text : this.msg("document.loading"),
				spanClass : "wait",
				displayTime : 0
			});
			this.widgets.signDialog = new Alfresco.module.SimpleDialog("signDialog").setOptions({
				width : "50em",
				templateUrl : Alfresco.constants.URL_SERVICECONTEXT + "keensoft/sign/sign-dialog?nodeRef=" + file.nodeRef,
				actionUrl : Alfresco.constants.PROXY_URI + "keensoft/sign/save-sign",
				destroyOnHide : true,
				onSuccess : {
					fn : function signDialog_successCallback(response) {
						Alfresco.util.PopupManager.displayMessage({
							text : this.msg("message.sign-action.success"),
							displayTime : 3
						});
						setTimeout(function(){window.location.href=Alfresco.constants.URL_PAGECONTEXT + "site/" +  Alfresco.constants.SITE + "/document-details?nodeRef=" + file.nodeRef},3000);
					},
					scope : this
				},
				onFailure : {
					fn : function signDialog_failCallback(response) {
						Alfresco.util.PopupManager.displayMessage({
							text : this.msg("message.sign-action.failure"),
							displayTime : 3
						});						
						setTimeout(function(){window.location.href=Alfresco.constants.URL_PAGECONTEXT + "site/" +  Alfresco.constants.SITE + "/document-details?nodeRef=" + file.nodeRef},3000);
					},
					scope : this
				},
				doBeforeDialogShow : {
					fn : function beforeSign_dialogShow(response) {
						this.widgets.waitDialog.destroy();
					},
					scope : this
				}
			});

			this.widgets.signDialog.show();
		}
	});
})();