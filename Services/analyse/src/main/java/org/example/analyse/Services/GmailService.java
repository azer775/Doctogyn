package org.example.analyse.Services;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.*;
import org.example.analyse.Models.EmailAttachment;
import org.example.analyse.Models.EmailMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class GmailService {
    private static final String APPLICATION_NAME = "doctogyn";
    private static final long MAX_RESULTS = 6; // Limit results for pagination

    public List<String> fetchAttachments(String accessToken) throws IOException {
        Gmail gmail = buildGmailClient(accessToken);
        ListMessagesResponse messagesResponse = gmail.users().messages().list("me")
                .setMaxResults(MAX_RESULTS)
                .execute();

        List<String> attachments = new ArrayList<>();

        if (messagesResponse.getMessages() != null) {
            for (Message msg : messagesResponse.getMessages()) {
                Message message = gmail.users().messages().get("me", msg.getId())
                        .setFormat("full")
                        .execute();
                processMessageParts(message, attachments);
            }
        }

        return attachments;
    }

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

    private void processMessageParts(Message message, List<String> attachments) {
        if (message.getPayload() == null || message.getPayload().getParts() == null) {
            return;
        }

        for (MessagePart part : message.getPayload().getParts()) {
            if (part.getFilename() != null && !part.getFilename().isEmpty()) {
                attachments.add(part.getFilename());
            }
        }
    }


    public List<EmailMessage> fetchEmails(String accessToken) throws IOException {
        Gmail gmail = buildGmailClient(accessToken);

        // Fetch list of emails with pagination support
        ListMessagesResponse messagesResponse = gmail.users().messages().list("me")
                .setMaxResults(MAX_RESULTS) // Adjust as needed
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
                //email.setAttachments(getAttachments(gmail, message));
                email.setAttachments(getAttachmentsId(gmail, message));
                if (!email.getAttachments().isEmpty()) {
                    emails.add(email);
                }
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

    private List<EmailAttachment> getAttachments(Gmail gmail, Message message) throws IOException {
        List<EmailAttachment> attachments = new ArrayList<>();
        MessagePart payload = message.getPayload();

        if (payload.getParts() != null) {
            for (MessagePart part : payload.getParts()) {
                if (part.getFilename() != null && !part.getFilename().isEmpty()) {
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setFileName(part.getFilename());
                    attachment.setMimeType(part.getMimeType());

                    if (part.getBody().getAttachmentId() != null) {
                        MessagePartBody attachmentBody = gmail.users().messages().attachments()
                                .get("me", message.getId(), part.getBody().getAttachmentId())
                                .execute();

                        // Convert URL-safe Base64 to standard Base64
                        String base64Data = attachmentBody.getData()
                                .replace('-', '+')
                                .replace('_', '/');

                        // Ensure proper padding
                        int padLength = 4 - (base64Data.length() % 4);
                        if (padLength < 4) {
                            base64Data += "====".substring(0, padLength);
                        }

                        attachment.setFileData(base64Data);
                        attachment.setFileSize(attachmentBody.getSize());
                    }

                    attachments.add(attachment);
                }
            }
        }
        return attachments;
    }

    public EmailAttachment loadFileData(String accessToken, String messageId, EmailAttachment emailAttachment) throws IOException {
        Gmail gmail = buildGmailClient(accessToken);
        if (emailAttachment.getFileData() == null && emailAttachment.getAttachmentId() != null) {
            MessagePartBody attachmentBody = gmail.users().messages().attachments()
                    .get("me", messageId, emailAttachment.getAttachmentId())
                    .execute();
            emailAttachment.setFileData(attachmentBody.getData());
            System.out.println("file data service" + emailAttachment.getFileData());
            return emailAttachment;
        }
        return emailAttachment;
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

    public static byte[] decodeUrlSafeBase64(String base64Data) {
        if (base64Data == null || base64Data.isEmpty()) {
            throw new IllegalArgumentException("Base64 input is empty");
        }

        String standardBase64 = base64Data
                .replace('-', '+')
                .replace('_', '/');

        int padding = 4 - (standardBase64.length() % 4);
        if (padding < 4) {
            standardBase64 += "=".repeat(padding);
        }

        return Base64.getDecoder().decode(standardBase64);
    }
}
