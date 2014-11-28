alfresco-firma
==================
Provides an Alfresco Share action for signing PDF files (PAdES-BES format) and any other file (CAdES-BES format detached) via java applet (@firma miniApplet, opensource at http://svn-ctt.administracionelectronica.gob.es/svn/clienteafirma/).

This module uses a software digital certificate or a cryptographic hardware supported by a smart card.

The plugin is licensed under the [LGPL v3.0](http://www.gnu.org/licenses/lgpl-3.0.html). The current version is 0.8.0, which is compatible with Alfresco 4.2.c/f and 5.0.a/b.

![alfresco-firma-pdf-ks](https://cloud.githubusercontent.com/assets/1818300/5228336/022cfab6-7709-11e4-9df8-cb641a92a763.png)

Downloading the ready-to-deploy-plugin
--------------------------------------
The binary distribution is made of two amp files:

* [repo AMP](https://github.com/keensoft/alfresco-firma-pdf/blob/master/dist/sign-document-0.8.0.amp?raw=true)
* [share AMP](https://github.com/keensoft/alfresco-firma-pdf/blob/master/dist/sign-document-share-0.8.0.amp?raw=true)

You can install them by using standard [Alfresco deployment tools](http://docs.alfresco.com/community/tasks/dev-extensions-tutorials-simple-module-install-amp.html)

Building the artifacts
----------------------
If you are new to Alfresco and the Alfresco Maven SDK, you should start by reading [Jeff Potts' tutorial on the subject](http://ecmarchitect.com/alfresco-developer-series-tutorials/maven-sdk/tutorial/tutorial.html).

You can build the artifacts from source code using maven
```$ mvn clean package```

alfresco-firma
==================
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
