package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BacteriologyInterpretation {
    NEGATIVE(1, "Negative"),
    POSITIVE(2, "Positive");

    private final int id;
    private final String name;
    private static final Map<Integer, BacteriologyInterpretation> BY_ID = new HashMap<>();

    static {
        for (BacteriologyInterpretation interpretation : BacteriologyInterpretation.values()) {
            BY_ID.put(interpretation.id, interpretation);
        }
    }

    BacteriologyInterpretation(int id, String name) {
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
    public static BacteriologyInterpretation fromId(int id) {
        BacteriologyInterpretation interpretation = BY_ID.get(id);
        if (interpretation == null) {
            throw new IllegalArgumentException("No BacteriologyInterpretation with id " + id + " found");
        }
        return interpretation;
    }
}
