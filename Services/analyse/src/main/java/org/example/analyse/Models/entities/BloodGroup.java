package org.example.analyse.Models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.analyse.Models.enums.BloodType;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class BloodGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    LocalDate date;
    BloodType type;
    String comment;
    long consultationId;
    public  String toHtmlRow() {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");

        html.append("<td>").append(this.getDate() != null ? this.getDate() : "N/A").append("</td>");
        html.append("<td>").append(this.getType() != null ? this.getType() : "N/A").append("</td>");
        html.append("<td>").append(this.getComment() != null ? this.getComment() : "N/A").append("</td>");

        html.append("</tr>");

        return html.toString();
    }
}
