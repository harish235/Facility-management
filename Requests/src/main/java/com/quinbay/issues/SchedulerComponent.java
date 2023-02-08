package com.quinbay.issues;

import com.quinbay.issues.Implementation.IssueImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SchedulerComponent {

    @Autowired
    IssueImpl issueImpl;

    @Scheduled(fixedRate = 2000000) public void scheduleTask()
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss.SSS");

        String strDate = dateFormat.format(new Date());

        System.out.println(
                "Fixed rate Scheduler: Task running at - "
                        + strDate);

        issueImpl.checkForOverdues();
        issueImpl.checkForIncomplete();
    }
}
