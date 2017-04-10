package br.com.estudosspringbatch.job;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.estudosspringbatch.component.JobData;
import br.com.estudosspringbatch.component.JobLock;

@Service
public class JobRunner {

    private JobDescriptor jobDescriptor;
    private JobLock jobLock;
    private JobData jobData;
    private JobLauncher jobLauncher;

    @Autowired
    public JobRunner(JobDescriptor jobDescriptor, JobLauncher jobLauncher, JobData jobData, JobLock jobLock) {
        this.jobDescriptor = jobDescriptor;
        this.jobLauncher = jobLauncher;
        this.jobData = jobData;
        this.jobLock = jobLock;
    }

	public void executeProcessFileJob(String tenantName, String inputFile) {

        JobParameters param = new JobParametersBuilder()
                .addString("jobId", String.valueOf(System.currentTimeMillis()))
                .addString("tenantName", tenantName)
                .addString("inputFile", inputFile)
                .toJobParameters();
        Job job = jobDescriptor.processFileJob();

        executeJob(job, param);
    }

    private BatchStatus executeJob(Job job, JobParameters param) {
        String jobId = param.getString("jobId", "UNKNOWN");
        BatchStatus status = null;
        try {
            jobLock.acquireLock(jobId);
            JobExecution execution = jobLauncher.run(job, param);
            status = execution.getStatus();
        } catch (JobExecutionAlreadyRunningException
                | JobRestartException
                | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            e.printStackTrace();
        } finally {
            jobData.clear(jobId);
            jobLock.releaseLock();
        }
        return status;
    }

}
