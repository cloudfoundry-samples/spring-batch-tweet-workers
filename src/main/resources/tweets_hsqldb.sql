CREATE TABLE TWEETS  (
	TWEET_ID BIGINT  NOT NULL IDENTITY,  
	RETWEETS BIGINT ,  
	USER_NAME VARCHAR(100) NOT NULL, 
	TWEET_TEXT VARCHAR(256) NOT NULL,
	PRIMARY KEY (TWEET_ID)
);

