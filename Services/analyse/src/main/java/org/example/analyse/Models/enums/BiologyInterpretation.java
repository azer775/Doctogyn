package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BiologyInterpretation {
    NORMAL(1, "Normal"),
    LOW(2, "Low"),
    HIGH(3, "High");

    private final int id;
    private final String name;
    private static final Map<Integer, BiologyInterpretation> BY_ID = new HashMap<>();

    static {
        for (BiologyInterpretation interpretation : BiologyInterpretation.values()) {
            BY_ID.put(interpretation.id, interpretation);
        }
    }

    BiologyInterpretation(int id, String name) {
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
    public static BiologyInterpretation fromId(int id) {
        BiologyInterpretation interpretation = BY_ID.get(id);
        if (interpretation == null) {
            throw new IllegalArgumentException("No BiologyInterpretation with id " + id + " found");
        }
        return interpretation;
    }
}
