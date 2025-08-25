package org.example.analyse.Models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum BloodType {
    A_POSITIVE(1, "A positive"),
    A_NEGATIVE(2, "A negative"),
    B_POSITIVE(3, "B positive"),
    B_NEGATIVE(4, "B negative"),
    AB_POSITIVE(5, "AB positive"),
    AB_NEGATIVE(6, "AB negative"),
    O_POSITIVE(7, "O positive"),
    O_NEGATIVE(8, "O negative");

    private final int id;
    private final String name;
    private static final Map<Integer, BloodType> BY_ID = new HashMap<>();

    static {
        for (BloodType type : BloodType.values()) {
            BY_ID.put(type.id, type);
        }
    }

    BloodType(int id, String name) {
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
    public static BloodType fromId(int id) {
        BloodType type = BY_ID.get(id);
        if (type == null) {
            throw new IllegalArgumentException("No BloodType with id " + id + " found");
        }
        return type;
    }
}
