##Readme

####Intro

The project has been developed with Eclipse IDE and requires Node.js (https://nodejs.org/en/), a Geoserver instance (http://geoserver.org/) and bower (https://bower.io/).

Another crucial component is Gulp, that can be installed following the detailed guidelines at http://gulpjs.com/.

Repository can be cloned and opened from Eclipse a new Maven project. Once this step is completed it can be necessary to run a “mvn build” from command line or from the IDE. To install the client dependency is sufficient ro run “bower install” from the root of the project.

####Configuration

GeoServer must be configured to accept Cross origin requests. To achieve this it is necessary to add the following filter in the web.xml file of the geoserver webapp.

<filter>
  <filter-name>CorsFilter</filter-name>
<filter-class>org.eclipse.jetty.servlets.CrossOriginFilter</filter-class>

</filter>
<filter-mapping>
  <filter-name>CorsFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>

The url pattern may be customized based on the developed voluntees.

Properties of the project are defined in src/main/resources/application.properties. Here it is possible to change database setting and geoserver link.


####How to run

Server application can be easily run with the GisProjectApplication.java in src/main/java. It is the embedded Spring Boot application and will run a tomcat on the port 8080, if not otherwise specified.

There is also the possibility to add the project to a separate server instance. With maven developer can build the related war, with “mvn build” command.

The client application is managed by gulp. Developer need to move to the project folder and run the command “gulp serve”. This will run the client application on the port 3000, accessible via web.
Gulp support a deploy and build system, runnable with “gulp deploy” and “gulp build”. In case of deploy developer needs to properly configure his production environment in /gulp/deploy.js file.

#### Non expert users guide

- Install postgres with the postgis extension under the default port 5432 and default user-pwd. Create "gis_db" database.
- Copy /dist/GisProject-0.0.1.war under your /tomcat/webapps folder and run your server
- Make sure your GeoServer instance is running on the 8081 port
- Open /dist/index.html to gain access to the application

