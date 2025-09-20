package org.example.analyse.Models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.example.analyse.Models.enums.SpermNorm;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SpermAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    LocalDate date;
    int abstinence;
    double ph;
    double volume;
    double concentration;
    double progressivemobility;
    double totalmotility;
    double totalcount;
    double roundcells;
    double leukocytes;
    double morphology;
    SpermNorm norm;
    double vitality;
    double tms;
    long consultationId;

    public String toHtmlRow() {
        StringBuilder html = new StringBuilder();
        html.append("<tr>");

        html.append("<td>").append(this.getDate() != null ? this.getDate() : "N/A").append("</td>");
        html.append("<td>").append(this.getAbstinence()).append("</td>");
        html.append("<td>").append(this.getPh()).append("</td>");
        html.append("<td>").append(this.getVolume()).append("</td>");
        html.append("<td>").append(this.getConcentration()).append("</td>");
        html.append("<td>").append(this.getProgressivemobility()).append("</td>");
        html.append("<td>").append(this.getTotalmotility()).append("</td>");
        html.append("<td>").append(this.getTotalcount()).append("</td>");
        html.append("<td>").append(this.getRoundcells()).append("</td>");
        html.append("<td>").append(this.getLeukocytes()).append("</td>");
        html.append("<td>").append(this.getMorphology()).append("</td>");
        html.append("<td>").append(this.getNorm() != null ? this.getNorm() : "N/A").append("</td>");
        html.append("<td>").append(this.getVitality()).append("</td>");
        html.append("<td>").append(this.getTms()).append("</td>");

        html.append("</tr>");

        return html.toString();
    }
}
