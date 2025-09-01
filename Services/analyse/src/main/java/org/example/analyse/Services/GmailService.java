package org.example.analyse.Services;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import org.example.analyse.Models.EmailAttachment;
import org.example.analyse.Models.EmailMessage;
import org.example.analyse.Models.dtos.ExtractionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GmailService {
    private static final String APPLICATION_NAME = "doctogyn";
    private static final long MAX_RESULTS = 6; // Limit results for pagination
    private static final Logger logger = LoggerFactory.getLogger(GmailService.class);
    @Autowired
    private FastApi fastApi;

    private Gmail buildGmailClient(String accessToken) {
        HttpRequestInitializer requestInitializer = request ->
                request.getHeaders().setAuthorization("Bearer " + accessToken);

        return new Gmail.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<EmailMessage> fetchEmails(String accessToken) throws IOException {
        Gmail gmail = buildGmailClient(accessToken);

        // Fetch list of emails with pagination support
        ListMessagesResponse messagesResponse = gmail.users().messages().list("me")
                .setMaxResults(MAX_RESULTS) // Adjust as needed
                .setQ("has:attachment") // Only emails with attachments
                .execute();

        List<EmailMessage> emails = new ArrayList<>();

        if (messagesResponse.getMessages() != null) {
            for (Message msg : messagesResponse.getMessages()) {
                // Get full message details
                Message message = gmail.users().messages().get("me", msg.getId())
                        .setFormat("full")
                        .execute();

                EmailMessage email = new EmailMessage();
                email.setId(message.getId());

                // Extract headers
                for (MessagePartHeader header : message.getPayload().getHeaders()) {
                    switch (header.getName()) {
                        case "Subject":
                            email.setSubject(header.getValue());
                            break;
                        case "From":
                            email.setFrom(header.getValue());
                            break;
                        case "Date":
                            email.setDate(header.getValue());
                            break;
                    }
                }

                // Process email body
                email.setBody(getMessageBody(message.getPayload()));

                // Process attachments
                email.setAttachments(getAttachmentsId(gmail, message));

                emails.add(email);

            }
        }

        return emails;
    }

    private String getMessageBody(MessagePart payload) {
        if (payload.getBody() != null && payload.getBody().getData() != null) {
            return new String(payload.getBody().decodeData(), StandardCharsets.UTF_8);
        }

        if (payload.getParts() != null) {
            for (MessagePart part : payload.getParts()) {
                if ("text/plain".equals(part.getMimeType()) &&
                        part.getBody() != null &&
                        part.getBody().getData() != null) {
                    return new String(part.getBody().decodeData(), StandardCharsets.UTF_8);
                }
            }
        }

        return "No text content found";
    }

    private List<EmailAttachment> getAttachmentsId(Gmail gmail, Message message) throws IOException {
        List<EmailAttachment> attachments = new ArrayList<>();
        MessagePart payload = message.getPayload();

        if (payload.getParts() != null) {
            for (MessagePart part : payload.getParts()) {
                if (part.getFilename() != null && !part.getFilename().isEmpty()) {
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setFileName(part.getFilename());
                    attachment.setMimeType(part.getMimeType());

                    System.out.println("mymeType" + attachment.getMimeType());
                    if (part.getBody().getAttachmentId() != null) {
                        attachment.setAttachmentId(part.getBody().getAttachmentId());
                        attachment.setFileSize(part.getBody().getSize());
                    }

                    attachments.add(attachment);
                }
            }
        }
        return attachments;
    }
    public List<EmailAttachment> loadFileDataBatch(String accessToken, List<EmailAttachment> attachments) throws IOException {
        Gmail gmail = buildGmailClient(accessToken);
        return attachments.stream()
                .map(attachment -> {
                    try {
                        if (attachment.getFileData() == null && attachment.getAttachmentId() != null) {
                            MessagePartBody attachmentBody = gmail.users().messages().attachments()
                                    .get("me", attachment.getEmailId(), attachment.getAttachmentId())
                                    .execute();
                            String rawFileData = attachmentBody.getData();
                            if (rawFileData == null || rawFileData.isEmpty()) {
                                logger.error("No file data received for attachment ID: {}", attachment.getAttachmentId());
                                throw new IllegalStateException("Empty file data from Gmail API");
                            }
                            // Normalize URL-safe Base64 to standard Base64
                            String standardBase64 = rawFileData
                                    .replace('-', '+')
                                    .replace('_', '/');
                            // Add padding if needed
                            int padding = 4 - (standardBase64.length() % 4);
                            if (padding < 4 && padding > 0) {
                                standardBase64 += "=".repeat(padding);
                            }
                            attachment.setFileData(standardBase64);
                            logger.info("Loaded file data for attachment {} (size: {} bytes, data length: {})",
                                    attachment.getAttachmentId(), attachment.getFileSize(), standardBase64.length());
                        }
                        return attachment;
                    } catch (IOException e) {
                        logger.error("Error loading attachment data", e);
                        throw new RuntimeException("Error loading attachment data: " + e.getMessage());
                    }
                })
                .collect(Collectors.toList());
    }

    public ExtractionResponse processAttachments(List<EmailAttachment> attachments) throws IOException {
        List<MultipartFile> files = attachments.stream()
                .map(attachment -> {
                    validateAttachment(attachment);
                    byte[] fileBytes = Base64.getDecoder().decode(attachment.getFileData());
                    return createMultipartFile(fileBytes, attachment);
                })
                .collect(Collectors.toList());

        return fastApi.uploadFile(files);
    }

    private void validateAttachment(EmailAttachment attachment) {
        if (attachment.getFileData() == null || attachment.getFileData().isEmpty()) {
            throw new IllegalArgumentException("No file data provided");
        }
        if (!attachment.getFileData().matches("^[A-Za-z0-9+/=]+$")) {
            throw new IllegalArgumentException("Invalid Base64 data");
        }
    }

    private MultipartFile createMultipartFile(byte[] fileBytes, EmailAttachment attachment) {
        return new MultipartFile() {
            @Override
            public String getName() {
                return attachment.getFileName();
            }

            @Override
            public String getOriginalFilename() {
                return attachment.getFileName();
            }

            @Override
            public String getContentType() {
                return attachment.getMimeType();
            }

            @Override
            public boolean isEmpty() {
                return fileBytes.length == 0;
            }

            @Override
            public long getSize() {
                return fileBytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileBytes;
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(fileBytes);
            }

            @Override
            public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
                try (java.io.FileOutputStream fos = new java.io.FileOutputStream(dest)) {
                    fos.write(fileBytes);
                }
            }
        };
    }

}
