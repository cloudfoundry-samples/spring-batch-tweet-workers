# Spring Batch Sample Application on Cloud Foundry

This is a sample project that shows how to run a Spring Batch job as a standalone app on Cloud Foundry.


## Sample Application

This application runs a Spring Batch job every 10 seconds (via @Scheduled) that processes a comma-separated file of tweet information.  The job searches the tweet text for a given list of terms and writes the matching tweet information to an in-memory database if running locally or a MySQL database if running in Cloud Foundry.

### Configuration

#### Tweet Data
Generating the list of tweets is an exercise left to the reader, though you might check out the [twitter-rabbit-socks sample](https://github.com/cloudfoundry-samples/twitter-rabbit-socks-sample/tree/master/twitter2rabbit) for inspiration.

Tweets should be added in a file called src/main/resources/data/twitter/input/tweets.  Each line should be entered in the following form.

username, "text", number of retweets.

For example,

"jencompgeek",  "Cloud Foundry is the world's first open source PAAS",  51

You can modify the search terms in the TweetProcessor bean definition in context.xml:

    <bean id="tweetProcessor" class="org.cloudfoundry.workers.twitter.batch.TwitterFilterProcessor">
      <constructor-arg>
			<list>
				<value>Cloud Foundry</value>
				<value>PAAS</value>
			</list>
	  </constructor-arg>
    </bean>

## Compiling and Pushing the Application to Cloud Foundry

The application is configured with the Maven appassembler plugin, which will create a distribution directory and a start script that can be pushed to Cloud Foundry using the `vmc push` command:

    > mvn package
    > vmc push tweet-worker --path=target/appassembler
    Would you like to deploy from the current directory? [Yn]: y
    Detected a Standalone Application, is this correct? [Yn]: y
    1: java
    2: java7
    3: node
    4: node06
    5: ruby18
    6: ruby19
    Select Runtime [java]: 1
    Selected java
    Start Command: bin/demo
    Application Deployed URL [None]: vertx-socks-sample.cloudfoundry.com
    Memory reservation (128M, 256M, 512M, 1G, 2G) [64M]: 256M
    How many instances? [1]:
    Bind existing services to 'tweet-worker'? [yN]: n
    Create services to bind to 'tweet-worker'? [yN]: y
	1: mongodb
	2: mysql
	3: postgresql
	4: rabbitmq
	5: redis
	What kind of service?: 2
	Specify the name of the service [mysql-771fd]: tweet-db
	Create another? [yN]: n
    Would you like to save this configuration? [yN]: y
    Manifest written to manifest.yml.
    Creating Application: OK
    Uploading Application:
        Checking for available resources: OK
        Processing resources: OK
        Packing application: OK
        Uploading (43K): OK
    Push Status: OK
    Staging Application 'tweet-worker': OK
    Starting Application 'tweet-worker': OK

The important responses the `vmc push` prompts are these:

* Choose `Standalone Application`
* Choose `java` runtime
* Set the `Start Command` as shown
* Set the `Memory reservation` to at least `256M`

Now we can run 'vmc logs' to see if we are processing tweets:

    > vmc logs tweet-worker
    INFO: Running nightly tweet recorder
	Oct 17, 2012 4:56:06 PM org.cloudfoundry.workers.twitter.batch.NightlyTweetRecorder runNightlyTweetRecorder
	INFO: Exit status: COMPLETED[]

And we can use 'vmc tunnel' to inspect the TWEETS database with the mysql client:

    > vmc tunnel tweet-db
    1: none
	2: mysql
	3: mysqldump
	Which client would you like to start?> 2

	Opening tunnel on port 10000... OK
	Waiting for local tunnel to become available... OK
	Reading table information for completion of table and column names
	You can turn off this feature to get a quicker startup with -A

	Welcome to the MySQL monitor.  Commands end with ; or \g.
	Your MySQL connection id is 1816275
	Server version: 5.1.62rel13.3 Percona Server with XtraDB (GPL), Release rel13.3, Revision 435

	Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.

	Oracle is a registered trademark of Oracle Corporation and/or its
	affiliates. Other names may be trademarks of their respective
	owners.

	Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

	mysql> select * from TWEETS;
	+----------+----------+------------+---------------------------------------------------------------------------------------------------------------------------------------------+
	| TWEET_ID | RETWEETS | USER_NAME  | TWEET_TEXT                                                                                                                                  |
	+----------+----------+------------+---------------------------------------------------------------------------------------------------------------------------------------------+
	|        1 |       51 | jencompgeek| Cloud Foundry is the world's first open source PAAS

