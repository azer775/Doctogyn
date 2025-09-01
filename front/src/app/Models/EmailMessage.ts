import { EmailAttachment } from "./EmailAttachment";

export class EmailMessage {
    id!: string;
    subject!: string;
    from!: string;
    date!: string;
    body!: string;
    attachments!: EmailAttachment[];
  }