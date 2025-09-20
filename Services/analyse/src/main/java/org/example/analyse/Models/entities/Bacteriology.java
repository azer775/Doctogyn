package org.example.analyse.Models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.example.analyse.Models.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Data
@Setter
@ToString
public class Bacteriology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    LocalDate date;
    BacteriologyType type;
    List<Germ> germs = new ArrayList<>();
    BacteriologyInterpretation interpretation;
    String comment;
    long consultationId;

    public String toHtmlRow() {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");
        html.append("<td>").append(this.getDate() != null ? this.getDate() : "N/A").append("</td>");
        html.append("<td>").append(this.getType() != null ? this.getType() : "N/A").append("</td>");
        html.append("<td>")
                .append(this.getGerms() != null && !this.getGerms().isEmpty()
                        ? String.join(", ", this.getGerms().toString())
                        : "N/A")
                .append("</td>");
        html.append("<td>").append(this.getInterpretation() != null ? this.getInterpretation() : "N/A").append("</td>");
        html.append("<td>").append(this.getComment() != null ? this.getComment() : "N/A").append("</td>");

        html.append("</tr>");

        return html.toString();
    }

}
