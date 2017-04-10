package br.com.estudosspringbatch.component;

import org.springframework.stereotype.Component;

@Component
public class JobLock {

    private String jobId;

    public synchronized void acquireLock(String jobId) {
        if (this.jobId != null) {
            throw new IllegalStateException("jobData is locked by jobId: " + this.jobId);
        }
        this.jobId = jobId;
    }

    public synchronized void releaseLock() {
        this.jobId = null;
    }

    public String jobId() {
        return jobId;
    }
}