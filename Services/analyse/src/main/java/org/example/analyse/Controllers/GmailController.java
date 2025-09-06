package org.example.analyse.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.analyse.Models.EmailAttachment;
import org.example.analyse.Models.EmailMessage;
import org.example.analyse.Models.dtos.ExtractionResponse;
import org.example.analyse.Services.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
            ExtractionResponse response = gmailService.processAttachments(attachments);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .header("Content-Type", "application/json")
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body("Error processing attachments: " + e.getMessage());
        }
    }

}
