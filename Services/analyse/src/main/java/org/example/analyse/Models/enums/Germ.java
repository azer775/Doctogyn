package org.example.analyse.Models.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Germ {
    NO_GERM(0, "No germ"),
    CANDIDA_GLABRATA(1, "Candida glabrata"),
    STREPTOCOCCUS_AGALACTIAE(2, "Streptococcus agalactiae"),
    UREAPLASMA_UREALYTICUM(3, "Uréaplasma Uréalyticum"),
    STAPHYLOCOCCUS_SAPROPHYTICUS(4, "Staphylococcus saprophyticus"),
    CANDIDA_ALBICANS(5, "Candida Albicans"),
    ESCHERICHIA_COLI(6, "Escherichia Coli"),
    GARDNERELLA_VAGINALIS(7, "Gardnerella vaginalis"),
    CLUE_CELLS(8, "Clue cells"),
    CANDIDA_SPP(9, "Candida spp"),
    STAPHYLOCOCCUS_AUREUS(10, "Staphylococcus aureus"),
    MYCOPLASMA_HOMINIS(11, "Mycoplasma hominis"),
    CANDIDA_NON_ALBICANS(12, "Candida non albicans"),
    KLEBSIELLA_PNEUMONIAE(13, "Klebsiella pneumoniae"),
    TRICHOMONAS_VAGINALIS(15, "Trichomonas vaginalis"),
    STREPTO_B(16, "Strepto B"),
    ENTEROCOCCUS_FAECALIS(17, "Enterococcus faecalis"),
    STAPHYLOCOQUE_COAGULASE_NEGATIVE(18, "Staphylocoque coagulase negative"),
    ENTEROCOCCUS_SPP(19, "Enterococcus spp"),
    ENTEROBACTER_CLOACAE(20, "Enterobacter cloacae");

    private final int id;
    private final String name;
    private static final Map<Integer, Germ> BY_ID = new HashMap<>();

    static {
        for (Germ germ : Germ.values()) {
            BY_ID.put(germ.id, germ);
        }
    }

    Germ(int id, String name) {
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
    public static Germ fromId(int id) {
        Germ germ = BY_ID.get(id);
        if (germ == null) {
            throw new IllegalArgumentException("No Germ with id " + id + " found");
        }
        return germ;
    }

}
