package org.example.analyse.Services;

import org.example.analyse.Models.EmailAttachment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;

@Service
public class FastApiClient {
    private final String fastApiUrl = "http://localhost:8000";
    private final RestTemplate restTemplate;

    public FastApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.
                connectTimeout(Duration.ofSeconds(60)).build();
    }

    public String sendToFastAPI(byte[] fileBytes, String filename, String mimeType) {
        try {
            // 1. Wrap file bytes in a Resource with a real filename
            ByteArrayResource fileAsResource = new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };

            // 2. Create HttpHeaders for this file part
            HttpHeaders partHeaders = new HttpHeaders();
            partHeaders.setContentDisposition(ContentDisposition
                    .builder("multipart/form-data")
                    .name("file")
                    .filename(filename)
                    .build());
            partHeaders.setContentType(MediaType.parseMediaType(mimeType));

            // 3. Wrap the file Resource and headers into HttpEntity
            HttpEntity<Resource> fileEntity = new HttpEntity<>(fileAsResource, partHeaders);

            // 4. Create the multipart request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            // 5. Set headers for the full request
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // 6. Send request
            ResponseEntity<String> response = restTemplate.postForEntity(
                    fastApiUrl + "/pdf/extract-blood-data-with-embedding",
                    requestEntity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Failed to communicate with FastAPI service", e);
        }
    }

    public String sendFileToFastAPI(MultipartFile file) {
        try {
            // Step 1: Create headers for the file part
            HttpHeaders fileHeaders = new HttpHeaders();
            fileHeaders.setContentType(MediaType.parseMediaType(file.getContentType())); // use actual content type
            fileHeaders.setContentDisposition(ContentDisposition
                    .builder("form-data")
                    .name("file") // must match FastAPI endpoint parameter
                    .filename(file.getOriginalFilename())
                    .build()
            );

            // Step 2: Wrap the file bytes and headers into an HttpEntity
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileHeaders);

            // Step 3: Build the multipart request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            // Step 4: Set the overall request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Step 5: Send the request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:8000/pdf/extract-blood-data-with-embedding",
                    requestEntity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error sending file to FastAPI", e);
        }
    }

    public String send(byte[] fileBytes, String filename, String mimeType) {
        try {
            // Step 1: Create headers for the file part
            HttpHeaders fileHeaders = new HttpHeaders();
            fileHeaders.setContentType(MediaType.parseMediaType(mimeType)); // use actual content type
            fileHeaders.setContentDisposition(ContentDisposition
                    .builder("form-data")
                    .name("file") // must match FastAPI endpoint parameter
                    .filename(filename)
                    .build()
            );

            // Step 2: Wrap the file bytes and headers into an HttpEntity
            HttpEntity<byte[]> fileEntity = new HttpEntity<>(fileBytes, fileHeaders);

            // Step 3: Build the multipart request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileEntity);

            // Step 4: Set the overall request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            // Step 5: Send the request
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:8000/pdf/extract-blood-data-with-embedding",
                    requestEntity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error sending file to FastAPI", e);
        }

    }

    public String sendMany(List<MultipartFile> attachments) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            for (MultipartFile attachment : attachments) {
                // Step 1: Create headers for this file part
                HttpHeaders fileHeaders = new HttpHeaders();
                fileHeaders.setContentType(MediaType.parseMediaType(attachment.getContentType()));
                fileHeaders.setContentDisposition(ContentDisposition
                        .builder("form-data")
                        .name("files") // This must match FastAPI parameter name: files: List[UploadFile]
                        .filename(attachment.getOriginalFilename())
                        .build());
                System.out.println(attachment.getOriginalFilename());
                // Step 2: Create HttpEntity for the file
                HttpEntity<byte[]> fileEntity = new HttpEntity<>(attachment.getBytes(), fileHeaders);

                // Step 3: Add to multipart body
                body.add("files", fileEntity);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:8000/pdf/extract-blood-data-with-embedding",
                    requestEntity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error sending files to FastAPI", e);
        }
    }

    public String sendMany1(List<EmailAttachment> attachments) {
        try {
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            for (EmailAttachment attachment : attachments) {
                // Step 1: Create headers for this file part
                HttpHeaders fileHeaders = new HttpHeaders();
                fileHeaders.setContentType(MediaType.parseMediaType(attachment.getMimeType()));
                fileHeaders.setContentDisposition(ContentDisposition
                        .builder("form-data")
                        .name("files") // This must match FastAPI parameter name: files: List[UploadFile]
                        .filename(attachment.getFileName())
                        .build());
                System.out.println(attachment.getFileName());
                // Step 2: Create HttpEntity for the file
                HttpEntity<byte[]> fileEntity = new HttpEntity<>(GmailService.decodeUrlSafeBase64(attachment.getFileData()), fileHeaders);

                // Step 3: Add to multipart body
                body.add("files", fileEntity);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "http://localhost:8000/pdf/extract-blood-data-with-embedding",
                    requestEntity,
                    String.class
            );

            return response.getBody();

        } catch (Exception e) {
            throw new RuntimeException("Error sending files to FastAPI", e);
        }
    }
}
