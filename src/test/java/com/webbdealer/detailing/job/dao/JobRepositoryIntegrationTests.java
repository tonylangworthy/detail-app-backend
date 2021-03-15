package com.webbdealer.detailing.job.dao;

import com.webbdealer.detailing.job.JobService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class JobRepositoryIntegrationTests {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;

    @Test
    public void findPendingJobs_Test() {
        List<Job> jobs = jobRepository.findPendingJobs(1L);

        System.out.println("job list size: " + jobs.size());
    }

    @Test
    public void jobTotalTime_Test() {
        jobService.jobTotalTime(8L);


    }
}
