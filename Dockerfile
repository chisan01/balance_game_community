FROM tomcat:9.0.1-jre8-alpine

ADD ./build/libs/balance_game_community-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]