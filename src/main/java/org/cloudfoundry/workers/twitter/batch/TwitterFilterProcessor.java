package org.cloudfoundry.workers.twitter.batch;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

public class TwitterFilterProcessor implements ItemProcessor<Tweet, Tweet> {

	private List<String> filters;

	public TwitterFilterProcessor(List<String> filters) {
		this.filters = filters;
	}

	@Override
	public Tweet process(Tweet item) throws Exception {
		for (String filter : filters) {
			if (item.getText().contains(filter)) {
				return item;
			}
		}
		return null;
	}

}
