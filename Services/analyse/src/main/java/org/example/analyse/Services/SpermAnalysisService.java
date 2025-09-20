package org.example.analyse.Services;

import org.example.analyse.Models.entities.SpermAnalysis;
import org.example.analyse.repositories.SpermAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpermAnalysisService {

    @Autowired
    private SpermAnalysisRepository spermAnalysisRepository;

    public List<SpermAnalysis> findAll() {
        return spermAnalysisRepository.findAll();
    }

    public Optional<SpermAnalysis> findById(Long id) {
        return spermAnalysisRepository.findById(id);
    }

    public SpermAnalysis save(SpermAnalysis spermAnalysis) {
        return spermAnalysisRepository.save(spermAnalysis);
    }

    public SpermAnalysis update(Long id, SpermAnalysis spermAnalysisDetails) {
        Optional<SpermAnalysis> spermAnalysisOptional = spermAnalysisRepository.findById(id);
        if (spermAnalysisOptional.isEmpty()) {
            throw new RuntimeException("SpermAnalysis not found with id: " + id);
        }
        SpermAnalysis spermAnalysis = spermAnalysisOptional.get();
        spermAnalysis.setDate(spermAnalysisDetails.getDate());
        spermAnalysis.setAbstinence(spermAnalysisDetails.getAbstinence());
        spermAnalysis.setPh(spermAnalysisDetails.getPh());
        spermAnalysis.setVolume(spermAnalysisDetails.getVolume());
        spermAnalysis.setConcentration(spermAnalysisDetails.getConcentration());
        spermAnalysis.setProgressivemobility(spermAnalysisDetails.getProgressivemobility());
        spermAnalysis.setTotalmotility(spermAnalysisDetails.getTotalmotility());
        spermAnalysis.setTotalcount(spermAnalysisDetails.getTotalcount());
        spermAnalysis.setRoundcells(spermAnalysisDetails.getRoundcells());
        spermAnalysis.setLeukocytes(spermAnalysisDetails.getLeukocytes());
        spermAnalysis.setMorphology(spermAnalysisDetails.getMorphology());
        spermAnalysis.setNorm(spermAnalysisDetails.getNorm());
        spermAnalysis.setVitality(spermAnalysisDetails.getVitality());
        spermAnalysis.setTms(spermAnalysisDetails.getTms());
        spermAnalysis.setConsultationId(spermAnalysisDetails.getConsultationId());
        return spermAnalysisRepository.save(spermAnalysis);
    }

    public void deleteById(Long id) {
        spermAnalysisRepository.deleteById(id);
    }

    public List<SpermAnalysis> findByConsultationId(Long consultationId) {
        return spermAnalysisRepository.findByConsultationId(consultationId);
    }

    public String toHtmlTable(List<SpermAnalysis> spermAnalyses) {
        if (spermAnalyses == null || spermAnalyses.isEmpty()) {
            return "<table border=\"1\"><tr><td colspan=\"14\">No data available</td></tr></table>";
        }

        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\">");

        // Header row
        html.append("<tr>");
        html.append("<th>Date</th>");
        html.append("<th>Abstinence</th>");
        html.append("<th>pH</th>");
        html.append("<th>Volume</th>");
        html.append("<th>Concentration</th>");
        html.append("<th>Progressive Mobility</th>");
        html.append("<th>Total Motility</th>");
        html.append("<th>Total Count</th>");
        html.append("<th>Round Cells</th>");
        html.append("<th>Leukocytes</th>");
        html.append("<th>Morphology</th>");
        html.append("<th>Norm</th>");
        html.append("<th>Vitality</th>");
        html.append("<th>TMS</th>");
        html.append("</tr>");

        // Data rows
        for (SpermAnalysis analysis : spermAnalyses) {
            html.append(analysis.toHtmlRow());
        }

        html.append("</table>");
        return html.toString();
    }
}
