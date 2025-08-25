package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum RadiologyType {
    BREAST_ULTRASOUND(-1, "Breast Ultrasound"),
    MAMMOGRAPHY(-2, "Mammography"),
    TESTICULAR_ULTRASOUND(-3, "Testicular Ultrasound"),
    DOPPLER_ULTRASOUND_LOWER_LIMBS(-4, "Doppler Ultrasound Lower Limbs"),
    PITUITARY_MRI(-5, "Pituitary MRI"),
    PELVIC_SCAN(-6, "Pelvic Scan"),
    PELVIC_MRI(-7, "Pelvic MRI"),
    ABDOMINAL_ULTRASOUND(-8, "Abdominal Ultrasound"),
    ECHOCARDIOGRAPHY(-9, "Echocardiography"),
    SOFT_PART_ULTRASOUND(-10, "Soft Part Ultrasound"),
    RENAL_ULTRASOUND(-11, "Renal Ultrasound"),
    BRAIN_MRI(-12, "Brain MRI"),
    CHEST_MRI(-13, "Chest MRI"),
    CARDIAC_MRI(-14, "Cardiac MRI"),
    ABDOMINAL_MRI(-15, "Abdominal MRI"),
    BONE_MRI(-16, "Bone MRI"),
    BRAIN_SCAN(-17, "Brain Scan"),
    CHEST_SCAN(-18, "Chest Scan"),
    ABDOMINAL_SCAN(-19, "Abdominal Scan"),
    BONE_SCAN(-20, "Bone Scan"),
    LUNG_SCINTIGRAPHY(-21, "Lung Scintigraphy");

    private final int id;
    private final String name;
    private static final Map<Integer, RadiologyType> BY_ID = new HashMap<>();

    static {
        for (RadiologyType type : RadiologyType.values()) {
            BY_ID.put(type.id, type);
        }
    }

    RadiologyType(int id, String name) {
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
    public static RadiologyType fromId(int id) {
        RadiologyType type = BY_ID.get(id);
        if (type == null) {
            throw new IllegalArgumentException("No RadiologyType with id " + id + " found");
        }
        return type;
    }
}
