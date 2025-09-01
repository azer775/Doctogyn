package org.example.analyse.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.analyse.Models.EmailAttachment;
import org.example.analyse.Models.EmailMessage;
import org.example.analyse.Services.FastApi;
import org.example.analyse.Services.FastApiClient;
import org.example.analyse.Services.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/gmail")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8090"},allowCredentials = "true")
@RequiredArgsConstructor
public class GmailController {
    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    @Autowired
    private GmailService gmailService;
    @Autowired
    private FastApiClient fastApiClient;
    @Autowired
    private FastApi fastApi;
    /*used*/
    @GetMapping("/authorize")
    public ResponseEntity<String> authorizeGmail(Authentication authentication) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("google")
                .principal(authentication)
                .build();

        OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

        if (authorizedClient == null) {
            return ResponseEntity.badRequest().body("Authorization failed");
        }

        return ResponseEntity.ok("Gmail authorized successfully");
    }
    /*used*/
    /*@GetMapping("/attachments")
    public ResponseEntity<?> getAttachments(OAuth2AuthenticationToken authentication) {
        try {
            OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                    "google",
                    authentication.getName()
            );
            System.out.println(authentication.getPrincipal());
            if (client == null) {
                return ResponseEntity.badRequest().body("No authorized Gmail account");
            }

            List<String> attachments = gmailService.fetchAttachments(
                    client.getAccessToken().getTokenValue()
            );
            System.out.println(attachments);
            return ResponseEntity.ok(attachments);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to fetch attachments");
        }
    }*/
    /*used*/
    @GetMapping("/user/authenticated")
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
    /*used*/
    @GetMapping("/user/profile")
    public Map<String, Object> getUserProfile(Authentication authentication) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        return oauthUser.getAttributes();
    }
    /*used*/
    @GetMapping("/emails")
    public ResponseEntity<List<EmailMessage>> getEmails(OAuth2AuthenticationToken authentication) throws IOException {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );
        System.out.println(authentication.getName());

        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<EmailMessage> emails = gmailService.fetchEmails(client.getAccessToken().getTokenValue());

        // Add CORS headers explicitly (as backup to your global config)
        return ResponseEntity.ok(emails);
    }
    @GetMapping("/emails/{emailId}/attachments/{attachmentId}")
    public ResponseEntity<EmailAttachment> loadAttachmentData(@PathVariable String emailId, @PathVariable String attachmentId, OAuth2AuthenticationToken authentication) throws IOException {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );
        EmailAttachment attachment = new EmailAttachment();
        attachment.setAttachmentId(attachmentId);
        gmailService.loadFileData(client.getAccessToken().getTokenValue(), emailId, attachment);
        if(attachment.getFileData()!=null) {
            return ResponseEntity.ok(attachment);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/emails/attachments/process")
    public ResponseEntity<String> processmany(
            OAuth2AuthenticationToken authentication,
            @RequestBody List<EmailAttachment> emailAttachments) throws IOException {

        // 1. Authentication check
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );
        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        for(EmailAttachment emailAttachment: emailAttachments){
            gmailService.loadFileData(
                    client.getAccessToken().getTokenValue(),
                    emailAttachment.getEmailId(),
                    emailAttachment
            );
        }
        // 2. Get attachment data


        // 3. Process with FastAPI using the improved file upload logic
        // byte[] fileBytes = GmailService.decodeUrlSafeBase64(emailAttachment.getFileData());
        // Files.write(Paths.get("test_output.pdf"), fileBytes);

        String result = fastApiClient.sendMany1(
                emailAttachments
        );

        return ResponseEntity.ok(result);
    }
    @PostMapping("/aa/attachments")
    public ResponseEntity<List<EmailAttachment>> getAttachments(
            OAuth2AuthenticationToken authentication,
            @RequestBody List<EmailAttachment> attachments) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );
        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            List<EmailAttachment> processedAttachments = gmailService.loadFileDataBatch(
                    client.getAccessToken().getTokenValue(),
                    attachments
            );
            return ResponseEntity.ok(processedAttachments);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/process-attachment")
    public ResponseEntity<?> processAttachment(@RequestBody List<EmailAttachment> attachments) {
        try {
            String response = gmailService.processAttachments(attachments);
            return ResponseEntity.ok()
                    .header("Content-Type", "text/plain")
                    .body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "text/plain")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "text/plain")
                    .body("Error processing attachments: " + e.getMessage());
        }
    }

}
