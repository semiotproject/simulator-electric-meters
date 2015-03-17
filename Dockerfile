FROM ubuntu

WORKDIR /root

# Java
RUN apt-get update
RUN apt-get install -y wget binutils java-common unzip

RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN wget https://db.tt/dFU3BqFP -O /root/oracle-java8-installer_8u5-1~webupd8~3_all.deb
RUN dpkg -i oracle-java8-installer_8u5-1~webupd8~3_all.deb

# Utils
RUN apt-get update
RUN apt-get install -y maven git

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle/jre

RUN git clone https://github.com/semiotproject/simulator-electric-meters.git  ./simulator/

RUN chmod 777 ./simulator/run.sh

CMD ./simulator/run.sh


