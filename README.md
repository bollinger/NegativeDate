#Negative Date exception.
Build this project with sbt version 1.4.0 (project/build.properties)

Deploy it to tomcat 9.0.39.

Receive exception.

```
2020-10-12 11:41:35.932 SEVERE oacs.HostConfig Error deploying web application archive [/home/peter/apache-tomcat-9.0.39/webapps/Negative.war]
java.lang.IllegalStateException: Error starting child
	at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:720)
	at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:690)
	at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:706)
	at org.apache.catalina.startup.HostConfig.deployWAR(HostConfig.java:978)
	at org.apache.catalina.startup.HostConfig$DeployWar.run(HostConfig.java:1848)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at org.apache.tomcat.util.threads.InlineExecutorService.execute(InlineExecutorService.java:75)
	at java.base/java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:118)
	at org.apache.catalina.startup.HostConfig.deployWARs(HostConfig.java:773)
	at org.apache.catalina.startup.HostConfig.deployApps(HostConfig.java:427)
	at org.apache.catalina.startup.HostConfig.check(HostConfig.java:1620)
	at org.apache.catalina.startup.HostConfig.lifecycleEvent(HostConfig.java:305)
	at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
	at org.apache.catalina.core.ContainerBase.backgroundProcess(ContainerBase.java:1151)
	at org.apache.catalina.core.ContainerBase$ContainerBackgroundProcessor.processChildren(ContainerBase.java:1353)
	at org.apache.catalina.core.ContainerBase$ContainerBackgroundProcessor.processChildren(ContainerBase.java:1357)
	at org.apache.catalina.core.ContainerBase$ContainerBackgroundProcessor.run(ContainerBase.java:1335)
	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
	at java.base/java.util.concurrent.FutureTask.runAndReset(FutureTask.java:305)
	at java.base/java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:305)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
	at java.base/java.lang.Thread.run(Thread.java:834)
Caused by: org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Catalina].StandardHost[localhost].StandardContext[/Negative]]
	at org.apache.catalina.util.LifecycleBase.handleSubClassException(LifecycleBase.java:440)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:198)
	at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:717)
	... 24 more
Caused by: java.lang.IllegalArgumentException: Negative time
	at java.base/java.io.File.setLastModified(File.java:1441)
	at org.apache.catalina.startup.ExpandWar.expand(ExpandWar.java:169)
	at org.apache.catalina.startup.ContextConfig.fixDocBase(ContextConfig.java:820)
	at org.apache.catalina.startup.ContextConfig.beforeStart(ContextConfig.java:958)
	at org.apache.catalina.startup.ContextConfig.lifecycleEvent(ContextConfig.java:305)
	at org.apache.catalina.util.LifecycleBase.fireLifecycleEvent(LifecycleBase.java:123)
	at org.apache.catalina.util.LifecycleBase.setStateInternal(LifecycleBase.java:423)
	at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:182)
	... 25 more

```


## Update
This looks to be related to 

https://reproducible-builds.org/docs/source-date-epoch/

and
https://github.com/sbt/sbt/pull/5344/commits/1d0a41520071c2fcf694d6b68e4b5e7721f7c321

which I think incorrectly uses
```
orElse(0L)
``` 
passing in 0 as the timestamp for jar files.


###A workaround
before starting sbt
```
export SOURCE_DATE_EPOCH=$(git log -1 --pretty=%ct)
```

