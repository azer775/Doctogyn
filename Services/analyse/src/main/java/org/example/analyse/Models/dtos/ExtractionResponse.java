package org.example.analyse.Models.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExtractionResponse {
    List<Document> documents= new ArrayList<>();
}
