package br.com.estudosspringbatch.adapter.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import br.com.estudosspringbatch.job.FileJobService;

@Configuration
@EnableScheduling
public class JobScheduler {
	
	FileJobService fileJobService;
    
	@Autowired
	public JobScheduler(FileJobService fileJobService) {
		this.fileJobService = fileJobService;
	}
	@Scheduled(cron = "${colossus.configuration.jobs.scheduler.cron.processFiles}")
    public void processFiles() {
		fileJobService.doJob();
	
	}
}
