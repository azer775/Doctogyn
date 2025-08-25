package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BacteriologyType {
    HPV_TEST(-1, "lblHpvTest"),
    SEMEN_CULTURE(-2, "lblSemenCulture"),
    SEMEN_CULTURE_CHLAMYDIA(-3, "lblSemenCultureChlamydia"),
    SEMEN_CULTURE_MYCOPLASMA(-4, "lblSemenCultureMycoplasma"),
    URINE_CULTURE(-5, "cyto-bactériologiquedesurines"),
    VAGINAL_SWAB(-6, "lblVaginalSwab"),
    VAGINAL_SWAB_CHLAMYDIA(-7, "lblVaginalSwabChlamydia"),
    VAGINAL_SWAB_MYCOPLASMA(-8, "lblVaginalSwabMycoplasma");

    private final int id;
    private final String name;
    private static final Map<Integer, BacteriologyType> BY_ID = new HashMap<>();

    static {
        for (BacteriologyType type : BacteriologyType.values()) {
            BY_ID.put(type.id, type);
        }
    }

    BacteriologyType(int id, String name) {
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
    public static BacteriologyType fromId(int id) {
        BacteriologyType type = BY_ID.get(id);
        if (type == null) {
            throw new IllegalArgumentException("No BacteriologyType with id " + id + " found");
        }
        return type;
    }
}
