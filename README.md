alfresco-firma
==================

> ** IMPORTANT NOTICE **

> New electronic signature addon by using client electronic certificates is available at [alfresco-esign-cert](https://github.com/keensoft/alfresco-esign-cert)

> **alfresco-firma-pdf** only supports applet, but **alfresco-esign-cert** supports also local application

> **alfresco-esign-cert** allows additionally client signature by using Google Chrome, iOS and Android

Provides an Alfresco Share action for signing PDF files (PAdES-BES format) and any other file (CAdES-BES format detached) via java applet (@firma miniApplet, opensource at https://github.com/ctt-gob-es/clienteafirma).

This module uses a software digital certificate or a cryptographic hardware supported by a smart card.

The plugin is licensed under the [LGPL v3.0](http://www.gnu.org/licenses/lgpl-3.0.html). The current version is 0.9.0, which is compatible with Alfresco 4.2.c-f and 5.0.a-d.

![alfresco-firma-pdf-ks](https://cloud.githubusercontent.com/assets/1818300/5228336/022cfab6-7709-11e4-9df8-cb641a92a763.png)

Downloading the ready-to-deploy-plugin
--------------------------------------
The binary distribution is made of two amp files:

* [repo AMP](https://github.com/keensoft/alfresco-firma-pdf/releases/download/0.9.0/sign-document-0.9.0.amp)
* [share AMP](https://github.com/keensoft/alfresco-firma-pdf/releases/download/0.9.0/sign-document-share-0.9.0.amp)

You can install them by using standard [Alfresco deployment tools](http://docs.alfresco.com/community/tasks/dev-extensions-tutorials-simple-module-install-amp.html)

Building the artifacts
----------------------
If you are new to Alfresco and the Alfresco Maven SDK, you should start by reading [Jeff Potts' tutorial on the subject](http://ecmarchitect.com/alfresco-developer-series-tutorials/maven-sdk/tutorial/tutorial.html).

You can build the artifacts from source code using maven
```$ mvn clean package```

Signing the applet
------------------
You can download plain applet from http://forja-ctt.administracionelectronica.gob.es/web/clienteafirma

Oracle [jarsigner](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/jarsigner.html) can be used to perform a signature on [miniapplet-full_1_3.jar](https://github.com/keensoft/alfresco-firma-pdf/blob/master/src/share-components/sign-document-share/src/main/amp/root/sign/miniapplet-full_1_3.jar). To deploy this change, just replace current JAR for your signed JAR and rebuild the artifacts.

Running under SSL
-----------------
Signature window is built on an IFRAME, so when running Alfresco under SSL, following JavaScript console error may appear:

```Refused to display 'https://alfresco.keensoft.es/share/sign/sign-frame.jsp?mimeType=pdf' in a frame because it set 'X-Frame-Options' to 'DENY'.```

If so, check your web server configuration in order to set appropiate **X-Frame-Options** header value.

For instance, Apache HTTP default configuration for SSL includes...

```Header always set X-Frame-Options DENY```

... and it should be set to **SAMEORIGIN** instead

```Header always set X-Frame-Options SAMEORIGIN```

alfresco-firma (Spanish version)
================================
Plugin para Alfresco que permite usar este como portafirmas gracias a la inclusión de una nueva acción para firmar documentos PDF (PAdES-BES) y para firmar el resto de documentos (CAdES-BES detached).

Presentación
------------
[Portada](http://keensoft.github.io/alfresco-firma-pdf/)

Documentación
-------------
Disponible en el directorio [docs](https://github.com/keensoft/alfresco-firma-pdf/tree/master/docs):
* Manual de instalación.
* Manual de usuario.
* Alfresco como portafirmas mediante el módulo de firma pdf.

Licencia
--------
Este desarrollo esta bajo licencia [LGPL v3.0](http://www.gnu.org/licenses/lgpl-3.0.html).
