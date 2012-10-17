package org.cloudfoundry.workers.twitter.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class TwitterFieldSetMapper implements FieldSetMapper<Tweet> {

	@Override
	public Tweet mapFieldSet(FieldSet fs) throws BindException {
		if (fs == null) {
			return null;
		}
		return new Tweet(fs.readString("user"), fs.readString("text"), fs.readInt("retweets"));
	}

}
