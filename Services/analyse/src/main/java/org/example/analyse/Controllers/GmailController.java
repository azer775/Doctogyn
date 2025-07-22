package org.example.analyse.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.analyse.Models.EmailAttachment;
import org.example.analyse.Models.EmailMessage;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gmail")
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8090"},allowCredentials = "true")
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

    @GetMapping("/attachments")
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
    }
    @GetMapping("/emails1")
    public List<EmailMessage> getEmails1(OAuth2AuthenticationToken authentication) throws IOException {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );
        if (client == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authorized Gmail account");
        }
        return gmailService.fetchEmails(client.getAccessToken().getTokenValue());
    }
    @GetMapping("/user/authenticated")
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }
    @GetMapping("/user/profile")
    public Map<String, Object> getUserProfile(Authentication authentication) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        return oauthUser.getAttributes();
    }
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
    @PostMapping("/emails/{emailId}/attachments/{attachmentId}/process")
    public ResponseEntity<String> processAttachment(
            @PathVariable String emailId,
            @PathVariable String attachmentId,
            OAuth2AuthenticationToken authentication,
            @RequestBody EmailAttachment emailAttachment) throws IOException {

        // 1. Authentication check
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                "google",
                authentication.getName()
        );
        if (client == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. Get attachment data
        gmailService.loadFileData(
                client.getAccessToken().getTokenValue(),
                emailId,
                emailAttachment
        );

        // 3. Process with FastAPI using the improved file upload logic
        byte[] fileBytes = GmailService.decodeUrlSafeBase64(emailAttachment.getFileData());
        Files.write(Paths.get("test_output.pdf"), fileBytes);

        String result = fastApiClient.send(
                fileBytes,
                emailAttachment.getFileName(),
                emailAttachment.getMimeType()
        );

        return ResponseEntity.ok(result);
    }

    @PostMapping(value="testsending", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String testsending(@RequestBody MultipartFile file){
        System.out.println(file.getContentType());
        return this.fastApiClient.sendFileToFastAPI(file);
    }
    @PostMapping(value="testsendingmany", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String sendingmany(@RequestBody List<MultipartFile> files){
        return this.fastApiClient.sendMany(files);
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

}
