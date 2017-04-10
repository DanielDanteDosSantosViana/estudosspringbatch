package br.com.estudosspringbatch.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileJobService {
	
	@Autowired
	JobRunner jobRunner;
	
	@Autowired
	public FileJobService() {
	}
	public void doJob() {
		jobRunner.executeProcessFileJob("TIMPOSPAGO", "sample-data.csv");
	}

	
}
