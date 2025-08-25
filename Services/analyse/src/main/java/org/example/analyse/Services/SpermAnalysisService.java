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
}
