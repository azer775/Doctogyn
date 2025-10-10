package org.example.analyse.Services;

import org.example.analyse.Models.dtos.Document;
import org.example.analyse.Models.dtos.ExtractionResponse;
import org.example.analyse.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractionService {
    @Autowired
    BiologyRepository biologyRepository;
    @Autowired
    BacteriologyRepository bacteriologyRepository;
    @Autowired
    BloodGroupRepository bloodGroupRepository;
    @Autowired
    RadiologyRepository radiologyRepository;
    @Autowired
    SerologyRepository serologyRepository;
    @Autowired
    SpermAnalysisRepository spermAnalysisRepository;
    @Autowired
    BiologyService biologyService;
    @Autowired
    BacteriologyService bacteriologyService;
    @Autowired
    BloodGroupService bloodGroupService;
    @Autowired
    RadiologyService radiologyService;
    @Autowired
    SerologyService serologyService;
    @Autowired
    SpermAnalysisService spermAnalysisService;

    public ExtractionResponse addanalyses(ExtractionResponse response, long consultationId) {
        ExtractionResponse extractedResponse = new ExtractionResponse();
        Document document = new Document();
        response.getDocuments().forEach(doc -> {
            if (doc.getBiologies() != null) {
                doc.getBiologies().forEach(biology -> biology.setConsultationId(consultationId));
                document.setBiologies(biologyRepository.saveAll(doc.getBiologies()));
            }
            if (doc.getBacteriologies() != null) {
                doc.getBacteriologies().forEach(bacteriology -> bacteriology.setConsultationId(consultationId));
                document.setBacteriologies(bacteriologyRepository.saveAll(doc.getBacteriologies()));
            }
            if (doc.getBloodgroups() != null) {
                doc.getBloodgroups().forEach(bloodGroup -> bloodGroup.setConsultationId(consultationId));
                document.setBloodgroups(bloodGroupRepository.saveAll(doc.getBloodgroups()));
            }
            if (doc.getRadiologies() != null) {
                doc.getRadiologies().forEach(radiology -> radiology.setConsultationId(consultationId));
                document.setRadiologies(radiologyRepository.saveAll(doc.getRadiologies()));
            }
            if (doc.getSerologies() != null) {
                doc.getSerologies().forEach(serology -> serology.setConsultationId(consultationId));
                document.setSerologies(serologyRepository.saveAll(doc.getSerologies()));
            }
            if (doc.getSpermAnalyses() != null) {
                doc.getSpermAnalyses().forEach(spermAnalysis -> spermAnalysis.setConsultationId(consultationId));
                document.setSpermAnalyses(spermAnalysisRepository.saveAll(doc.getSpermAnalyses()));
            }
        });
        extractedResponse.getDocuments().add(document);
        return extractedResponse;
    }
    public ExtractionResponse getByConsultation(long id) {
        Document document = new Document();
        ExtractionResponse response = new ExtractionResponse();
        document.setBacteriologies(bacteriologyRepository.findByConsultationId(id));
        document.setBiologies(biologyRepository.findByConsultationId(id));
        document.setBloodgroups(bloodGroupRepository.findByConsultationId(id));
        document.setRadiologies(radiologyRepository.findByConsultationId(id));
        document.setSerologies(serologyRepository.findByConsultationId(id));
        document.setSpermAnalyses(spermAnalysisRepository.findByConsultationId(id));
        response.getDocuments().add(document);
        return response;
    }
    public String toHtml(long id) {
        ExtractionResponse response = getByConsultation(id);
        Document doc = response.getDocuments().getFirst();
        StringBuilder html = new StringBuilder();

        // Wrap content in a container div
        html.append("<div>");

        // Biology table
        if (!doc.getBiologies().isEmpty()) {
            html.append("<h2>Biology Results</h2>");
            html.append(this.biologyService.toHtmlTable(doc.getBiologies()));
            html.append("<br>");
        }

        // Bacteriology table
        if (!doc.getBacteriologies().isEmpty()) {
            html.append("<h2>Bacteriology Results</h2>");
            html.append(this.bacteriologyService.toHtmlTable(doc.getBacteriologies()));
            html.append("<br>");
        }

        // Blood Group table
        if (!doc.getBloodgroups().isEmpty()) {
            html.append("<h2>Blood Group Results</h2>");
            html.append(this.bloodGroupService.toHtmlTable(doc.getBloodgroups()));
            html.append("<br>");
        }

        // Radiology table
        if (!doc.getRadiologies().isEmpty()) {
            html.append("<h2>Radiology Results</h2>");
            html.append(this.radiologyService.toHtmlTable(doc.getRadiologies()));
            html.append("<br>");
        }

        // Serology table
        if (!doc.getSerologies().isEmpty()) {
            html.append("<h2>Serology Results</h2>");
            html.append(this.serologyService.toHtmlTable(doc.getSerologies()));
            html.append("<br>");
        }

        // Sperm Analysis table
        if (!doc.getSpermAnalyses().isEmpty()) {
            html.append("<h2>Sperm Analysis Results</h2>");
            html.append(this.spermAnalysisService.toHtmlTable(doc.getSpermAnalyses()));
            html.append("<br>");
        }

        html.append("</div>");

        return html.toString();
    }
    public String toHtml(ExtractionResponse response) {
        Document doc = response.getDocuments().getFirst();
        StringBuilder html = new StringBuilder();

        // Wrap content in a container div
        html.append("<div>");

        // Biology table
        if (!doc.getBiologies().isEmpty()) {
            html.append("<h2>Biology Results</h2>");
            html.append(this.biologyService.toHtmlTable(doc.getBiologies()));
            html.append("<br>");
        }

        // Bacteriology table
        if (!doc.getBacteriologies().isEmpty()) {
            html.append("<h2>Bacteriology Results</h2>");
            html.append(this.bacteriologyService.toHtmlTable(doc.getBacteriologies()));
            html.append("<br>");
        }

        // Blood Group table
        if (!doc.getBloodgroups().isEmpty()) {
            html.append("<h2>Blood Group Results</h2>");
            html.append(this.bloodGroupService.toHtmlTable(doc.getBloodgroups()));
            html.append("<br>");
        }

        // Radiology table
        if (!doc.getRadiologies().isEmpty()) {
            html.append("<h2>Radiology Results</h2>");
            html.append(this.radiologyService.toHtmlTable(doc.getRadiologies()));
            html.append("<br>");
        }

        // Serology table
        if (!doc.getSerologies().isEmpty()) {
            html.append("<h2>Serology Results</h2>");
            html.append(this.serologyService.toHtmlTable(doc.getSerologies()));
            html.append("<br>");
        }

        // Sperm Analysis table
        if (!doc.getSpermAnalyses().isEmpty()) {
            html.append("<h2>Sperm Analysis Results</h2>");
            html.append(this.spermAnalysisService.toHtmlTable(doc.getSpermAnalyses()));
            html.append("<br>");
        }

        html.append("</div>");

        return html.toString();
    }
    public ExtractionResponse updateanalyses(ExtractionResponse response) {
        ExtractionResponse  extractedResponse = new ExtractionResponse();
        Document document = new Document();
        response.getDocuments().stream().forEach(doc -> {
            if (doc.getBiologies() != null)
                document.setBiologies(biologyRepository.saveAll(doc.getBiologies()));
            if (doc.getBacteriologies() != null)
                document.setBacteriologies(bacteriologyRepository.saveAll(doc.getBacteriologies()));
            if (doc.getBloodgroups() != null)
                document.setBloodgroups(bloodGroupRepository.saveAll(doc.getBloodgroups()));
            if (doc.getRadiologies() != null)
                document.setRadiologies(radiologyRepository.saveAll(doc.getRadiologies()));
            if (doc.getSerologies() != null)
                document.setSerologies(serologyRepository.saveAll(doc.getSerologies()));
            if (doc.getSpermAnalyses() != null)
                document.setSpermAnalyses(spermAnalysisRepository.saveAll(doc.getSpermAnalyses()));
        });
        extractedResponse.getDocuments().add(document);
        return extractedResponse;
    }
}
