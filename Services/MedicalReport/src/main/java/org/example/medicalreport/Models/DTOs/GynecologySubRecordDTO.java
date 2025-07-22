package org.example.medicalreport.Models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.medicalreport.Models.enums.HormoneStatus;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class GynecologySubRecordDTO {
    private Long id;
    private String work;
    private String civilState;
    private HormoneStatus hormoneStatus;
    private LocalDate menopause;
    private Boolean dysmenorrhea;
    private Boolean menorrhagia;
    private Boolean metrorrhagia;
    private int periodMax;
    private int periodMin;
    private LocalDate date;
    private LocalDate background;
    private Long medicalRecordId;
    @Builder
    public GynecologySubRecordDTO(Long id, String work, String civilState, HormoneStatus hormoneStatus,
                                  LocalDate menopause, Boolean dysmenorrhea, Boolean menorrhagia,
                                  Boolean metrorrhagia, int periodMax, int periodMin,
                                  LocalDate date, LocalDate background, Long medicalRecordId) {
        this.id = id;
        this.work = work;
        this.civilState = civilState;
        this.hormoneStatus = hormoneStatus;
        this.menopause = menopause;
        this.dysmenorrhea = dysmenorrhea;
        this.menorrhagia = menorrhagia;
        this.metrorrhagia = metrorrhagia;
        this.periodMax = periodMax;
        this.periodMin = periodMin;
        this.date = date;
        this.background = background;
        this.medicalRecordId = medicalRecordId;
    }
}
