classes = Notification.java NotificationSink.java NotificationSinkInterface.java SinkServer.java NotificationSource.java SourceClient.java SquareSource.java SquareSink.java

default:
		javac $(classes)
		rmic NotificationSink 
		rmic NotificationSource 

clean: 
	rm *.class