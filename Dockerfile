FROM ubuntu:latest

ENV TZ=Europe/Rome
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# Install Tomcat && project dependencies
RUN apt-get update && apt-get upgrade -y

RUN apt-get install wget vim openjdk-11-jdk-headless mysql-client -y

ENV JRE_HOME /usr/lib/jvm/java-11-openjdk-amd64/

# Install Tomcat
RUN cd /opt/ && wget https://dlcdn.apache.org/tomcat/tomcat-10/v10.0.22/bin/apache-tomcat-10.0.22.tar.gz && \
	tar -xf apache-tomcat-10.0.22.tar.gz && mv apache-tomcat-10.0.22 tomcat && \ 
	cd tomcat && chmod u+x bin/*.sh 

# Install J-Connector
RUN cd /opt/ && wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.28.tar.gz && \
	tar -xf mysql-connector-java-8.0.28.tar.gz && \
	cp mysql-connector-java-8.0.28/mysql-connector-java-8.0.28.jar /opt/tomcat/lib/. && \
	rm -r mysql-connector-java-8.0.2* 

ENV HOME /opt/tomcat/

WORKDIR /opt/tomcat/bin/

# Debug: Set users
RUN head -n -1 /opt/tomcat/conf/tomcat-users.xml > /opt/tomcat/conf/tmp.xml && \
	mv /opt/tomcat/conf/tmp.xml /opt/tomcat/conf/tomcat-users.xml && \
        echo '<role rolename="manager-gui"/> <user username="hoppin" password="hoppin" roles="manager-gui"/> </tomcat-users>' >> /opt/tomcat/conf/tomcat-users.xml 

# Set image directory path
RUN mkdir /opt/images 

RUN head -n -4 /opt/tomcat/conf/server.xml > /opt/tomcat/conf/tmp.xml && \
	mv /opt/tomcat/conf/tmp.xml /opt/tomcat/conf/server.xml && \
	echo '<Context docBase="/opt/images/" path="/images/"/> </Host> </Engine> </Service> </Server> ' >> /opt/tomcat/conf/server.xml


RUN /opt/tomcat/bin/configtest.sh

RUN cd /opt/tomcat/webapps && wget https://github.com/Kurushh/ProgettoProg3/blob/main/hoppin.war && cd

COPY config.properties /opt/.
COPY hoppin.war /opt/tomcat/webapps/.

# Debug only:
#RUN  echo '<Context privileged="true" antiResourceLocking="false" docBase="${catalina.home}/webapps/manager"> <Valve className="org.apache.catalina.valves.RemoteAddrValve" allow=".*" /> </Context>' >> /opt/tomcat/conf/Catalina/localhost/manager.xml

# Default command
CMD ["bash"]
