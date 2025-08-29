package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class BajajApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BajajApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 App started...");

        RestTemplate restTemplate = new RestTemplate();

        // Step 1: Call generateWebhook API
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, String> body = new HashMap<>();
        body.put("name", "John Doe"); // <-- replace with your details
        body.put("regNo", "REG12347"); // <-- replace with your reg no
        body.put("email", "john@example.com"); // <-- replace with your email

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        // ✅ Extract webhook & token after response
        String webhookUrl = (String) response.getBody().get("webhook");
        String accessToken = (String) response.getBody().get("accessToken");

        // Step 2: Your SQL Query (for Q2, since regNo ends with even digit)
        String finalQuery = "SELECT e1.EMP_ID, " +
                "e1.FIRST_NAME, " +
                "e1.LAST_NAME, " +
                "d.DEPARTMENT_NAME, " +
                "COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
                "FROM EMPLOYEE e1 " +
                "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT " +
                "AND e2.DOB > e1.DOB " +
                "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                "ORDER BY e1.EMP_ID DESC;";

        // ✅ Debug logs AFTER variables are declared
        System.out.println("🔗 Webhook URL = " + webhookUrl);
        System.out.println("🔑 Access Token = " + accessToken);
        System.out.println("📄 Final Query = " + finalQuery);

        // Step 3: Submit final query
        Map<String, String> answer = new HashMap<>();
        answer.put("finalQuery", finalQuery);

        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setContentType(MediaType.APPLICATION_JSON);
        authHeaders.setBearerAuth(accessToken);

        HttpEntity<Map<String, String>> answerRequest = new HttpEntity<>(answer, authHeaders);

        try {
            ResponseEntity<String> submitResponse =
                    restTemplate.postForEntity(webhookUrl, answerRequest, String.class);
            System.out.println("📤 Submission Response: " + submitResponse.getBody());
        } catch (Exception ex) {
            System.out.println("❌ Error while submitting query:");
            ex.printStackTrace();
        }
    }
}
