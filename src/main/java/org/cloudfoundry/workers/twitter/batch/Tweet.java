package org.cloudfoundry.workers.twitter.batch;

public class Tweet {

	private String user;
	private String text;
	private int retweets;

	public Tweet(String user, String text, int retweets) {
		this.user = user;
		this.text = text;
		this.retweets = retweets;
	}

	public String getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public int getRetweets() {
		return retweets;
	}

}
