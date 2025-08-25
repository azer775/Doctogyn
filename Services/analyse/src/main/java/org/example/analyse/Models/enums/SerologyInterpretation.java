package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum SerologyInterpretation {
    NEGATIVE(1, "Negative"),
    POSITIVE(2, "Positive"),
    EQUIVOQUAL(3, "Equivoqual");

    private final int id;
    private final String name;
    private static final Map<Integer, SerologyInterpretation> BY_ID = new HashMap<>();

    static {
        for (SerologyInterpretation interpretation : SerologyInterpretation.values()) {
            BY_ID.put(interpretation.id, interpretation);
        }
    }

    SerologyInterpretation(int id, String name) {
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
    public static SerologyInterpretation fromId(int id) {
        SerologyInterpretation interpretation = BY_ID.get(id);
        if (interpretation == null) {
            throw new IllegalArgumentException("No SerologyInterpretation with id " + id + " found");
        }
        return interpretation;
    }
}
