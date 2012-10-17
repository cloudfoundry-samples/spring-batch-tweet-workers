package org.cloudfoundry.workers.twitter.batch;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.cloudfoundry.runtime.env.CloudEnvironment;

public class Demo {
	public static void main(String[] args) {
		if(new CloudEnvironment().isCloudFoundry()) {
		  //activate cloud profile
		  System.setProperty("spring.profiles.active","cloud");
	    }
		new ClassPathXmlApplicationContext("context.xml", Demo.class);
	}
}
