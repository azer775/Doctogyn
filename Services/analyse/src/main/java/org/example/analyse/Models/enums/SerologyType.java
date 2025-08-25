package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum SerologyType {
    ANTI_B2GP1_ANTIBODIES(-1, "Anti-B2GP1 Antibodies"),
    ANTICARDIOLIPIN_ANTIBODIES(-2, "Anticardiolipin Antibodies"),
    ANTINUCLEAR_ANTIBODY(-3, "Antinuclear Antibody (1/)"),
    ANTIPHOSPHOLIPID_ANTIBODIES(-4, "Antiphospholipid Antibodies"),
    CANCER_ANTIGEN_125(-5, "Cancer Antigen 125 (U/mL)"),
    CHLAMYDIA_TRACHOMATIS_SEROLOGY(-6, "Chlamydia Trachomatis Serology"),
    COVID_19_ANTIBODIES(-7, "COVID-19 Antibodies"),
    CYTOMEGALOVIRUS_IGG(-8, "Cytomegalovirus IgG"),
    CYTOMEGALOVIRUS_IGG_AVIDITY(-9, "Cytomegalovirus IgG Avidity"),
    CYTOMEGALOVIRUS_IGM(-10, "Cytomegalovirus IgM"),
    FACTOR_5_LEIDEN_MUTATION_SCREEN(-11, "Factor 5 Leiden Mutation Screen"),
    HEPATITIS_B_ANTIBODY(-12, "Hepatitis B Antibody"),
    HEPATITIS_B_SURFACE_ANTIGEN(-13, "Hepatitis B Surface Antigen"),
    HEPATITIS_C_ANTIBODY(-14, "Hepatitis C Antibody"),
    HERPES_SIMPLEX_SEROLOGY(-15, "Herpes Simplex Serology"),
    HIV_ANTIBODY(-16, "HIV Antibody"),
    IRREGULAR_ANTIBODIES_TEST(-17, "Irregular Antibodies Test (1/)"),
    LUPUS_ANTICOAGULANT(-18, "Lupus Anticoagulant (GPL or MPL)"),
    MYCOPLASMA_SEROLOGY(-19, "Mycoplasma Serology"),
    NEISSERIA_GONORRHEA_SEROLOGY(-20, "Neisseria Gonorrhea Serology"),
    PARVOVIRUS_B19_SEROLOGY_IGG(-21, "Parvovirus B19 Serology IgG"),
    PARVOVIRUS_B19_SEROLOGY_IGM(-22, "Parvovirus B19 Serology IgM"),
    RUBELLA_SEROLOGY_AVIDITY(-23, "Rubella Serology Avidity"),
    RUBELLA_SEROLOGY_IGG(-24, "Rubella Serology IgG"),
    RUBELLA_SEROLOGY_IGM(-25, "Rubella Serology IgM"),
    SYPHILIS_SEROLOGY(-26, "Syphilis Serology"),
    TOXOPLASMA_PCR(-27, "Toxoplasma PCR"),
    TOXOPLASMA_SEROLOGY_AVIDITY(-28, "Toxoplasma Serology Avidity"),
    TOXOPLASMA_SEROLOGY_IGG(-29, "Toxoplasma Serology IgG"),
    TOXOPLASMA_SEROLOGY_IGM(-30, "Toxoplasma Serology IgM"),
    TRICHOMONAS_VAGINALIS_SEROLOGY(-31, "Trichomonas Vaginalis Serology"),
    CA_19_9(-32, "CA 19-9 (mU/mL or μg/mL)"),
    ACE(-33, "ACE (ng/mL)"),
    HCG(-34, "HCG"),
    AC_DNA_NATIF(-35, "AC DNA Natif");

    private final int id;
    private final String name;
    private static final Map<Integer, SerologyType> BY_ID = new HashMap<>();

    static {
        for (SerologyType type : SerologyType.values()) {
            BY_ID.put(type.id, type);
        }
    }

    SerologyType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonValue
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonCreator
    public static SerologyType fromId(int id) {
        SerologyType type = BY_ID.get(id);
        if (type == null) {
            throw new IllegalArgumentException("No SerologyType with id " + id + " found");
        }
        return type;
    }
}
