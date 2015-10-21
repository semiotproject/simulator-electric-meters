FROM ubuntu
MAINTAINER Daniil Garayzuev <garayzuev@gmail.com>
WORKDIR /root

ENV APP_DIR=/simulator-electric-meters
ENV APP_JAR=simulator-electric-meters-1.0-SNAPSHOT-jar-with-dependencies.jar
ENV APP_CONF=/semiot-platform/simulator-electric-meters/

# Java
RUN apt-get update && apt-get install -y wget binutils java-common unzip && echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && wget https://db.tt/dFU3BqFP -O /root/oracle-java8-installer_8u5-1~webupd8~3_all.deb && dpkg -i oracle-java8-installer_8u5-1~webupd8~3_all.deb

# Utils
RUN apt-get update && apt-get install -y maven git

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle/jre

#
# No cache workaround.
#
ADD build.number /root/build.number

RUN git clone https://github.com/semiotproject/simulator-electric-meters.git  $APP_DIR

RUN mkdir -p $APP_CONF

ADD configEnergy.properties  /semiot-platform/simulator-electric-meters/

WORKDIR $APP_DIR

RUN mvn clean package -DskipTests=true

CMD java -jar target/$APP_JAR
