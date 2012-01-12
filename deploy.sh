mvn clean package -Dmaven.test.skip=true
scp target/omedia.war 166.111.137.72:omedia.war
