# learn-microservice

# run axonserver

java-jar axonserver.jar

# run kafka

cd C:\kafka_2.13-3.3.1\bin\windows

zookeeper-server-start.bat C:\kafka_2.13-3.3.1\config\zookeeper.properties

kafka-server-start.bat C:\kafka_2.13-3.3.1\config\server.properties

# create 1 topic name on port 9092

kafka-topics.bat --create --topic microservice --bootstrap-server localhost:9092

# show list of existing topic

kafka-topics.bat --list --bootstrap-server localhost:9092

# create producer and send message

kafka-console-producer.bat --bootstrap-server localhost:9092 --topic microservice

# check message by consumer

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic microservice

