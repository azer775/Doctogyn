package org.example.analyse.Models.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class AnalyseReportDTO {
    boolean bacteriology;
    boolean serology;
    boolean bloodGroup;
    boolean spermAnalysis;
    boolean biology;
    boolean radiology;

    public boolean isBacteriology() {
        return bacteriology;
    }

    public void setBacteriology(boolean bacteriology) {
        this.bacteriology = bacteriology;
    }

    public boolean isSerology() {
        return serology;
    }

    public void setSerology(boolean serology) {
        this.serology = serology;
    }

    public boolean isBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(boolean bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public boolean isSpermAnalysis() {
        return spermAnalysis;
    }

    public void setSpermAnalysis(boolean spermAnalysis) {
        this.spermAnalysis = spermAnalysis;
    }

    public boolean isBiology() {
        return biology;
    }

    public void setBiology(boolean biology) {
        this.biology = biology;
    }

    public boolean isRadiology() {
        return radiology;
    }

    public void setRadiology(boolean radiology) {
        this.radiology = radiology;
    }
}
