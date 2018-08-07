package com.herokuapp.erpmesbackend.erpmesbackend.erpmesbackend.reports;

import com.herokuapp.erpmesbackend.erpmesbackend.finance.CurrentReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReadCurrentReportTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void checkIfResponseContainsCurrentReport() {
        ResponseEntity<CurrentReport> forEntity = restTemplate.getForEntity("/current-report",
                CurrentReport.class);
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(forEntity.getBody().getStartDate().getMonth())
                .isEqualTo(LocalDate.now().getMonth());
    }
}