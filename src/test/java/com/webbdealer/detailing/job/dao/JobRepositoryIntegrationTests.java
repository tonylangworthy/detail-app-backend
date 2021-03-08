package com.webbdealer.detailing.job.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class JobRepositoryIntegrationTests {

    @Autowired
    private JobRepository jobRepository;

    @Test
    public void findPendingJobs_Test() {
        List<Job> jobs = jobRepository.findPendingJobs(1L);

        System.out.println("job list size: " + jobs.size());
    }
}
