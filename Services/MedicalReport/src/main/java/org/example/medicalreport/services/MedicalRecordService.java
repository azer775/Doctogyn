package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.*;
import org.example.medicalreport.Models.SummaryDTOs.*;
import org.example.medicalreport.Models.entities.*;
import org.example.medicalreport.repositories.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private GynecologySubRecordService gynecologySubRecordService;
    @Autowired
    private FertilitySubRecordService fertilitySubRecordService;
    @Autowired
    private ObstetricsRecordService obstetricsRecordService;
    @Autowired
    private MedicalBackgroundService medicalBackgroundService;
    @Autowired
    private AiService aiService;
    @Autowired
    private User userService;
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private ConsultationService consultationService;

    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO dto,String auth) {
        String jwtToken = auth.replace("Bearer ", "");
        int doctorId = userService.getCurrentUser(jwtToken);
        MedicalRecord medicalRecord = mapToEntity(dto);
        medicalRecord.setDoctorId(doctorId);
        MedicalRecord saved = medicalRecordRepository.save(medicalRecord);
        return mapToDTO(saved);
    }

    public MedicalRecordDTO getMedicalRecord(Long id) {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id);
        if (medicalRecord.isPresent()) {
            MedicalRecord med = medicalRecord.get();
            MedicalRecordDTO dto = mapToDTO(med);
            // Populate subrecord lists with only ID and date
            dto.setGynecologySubRecords(
                    med.getGynecologySubRecords()
                            .stream()
                            .map(gynecologySubRecordService::mapToCompleteDTO)
                            .toList()
            );
            System.out.println("Gynecology SubRecords: " + dto.getGynecologySubRecords());

            dto.setFertilitySubRecords(
                    med.getFertilitySubRecords()
                            .stream()
                            .map(fertilitySubRecordService::mapToCompleteDTO)
                            .toList()
            );
            dto.setObstetricsRecords(
                    med.getObstetricsRecords()
                            .stream()
                            .map(obstetricsRecordService::mapToCompleteDTO)
                            .toList()
            );
            dto.setMedicalBackgrounds(
                    med.getMedicalBackgrounds().stream()
                            .map(medicalBackgroundService::mapToDTO)
                            .toList()
            );
            return dto;
        }
        return null;
    }
    /*
    * Show Medical record by id
    *
    * */
    public MedicalRecordDTO getMedicalRecordWithSubRecordIdsAndDates(Long id) {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(id);
        if (medicalRecord.isPresent()) {
            MedicalRecord med = medicalRecord.get();
            MedicalRecordDTO dto = mapToDTO(med);
            // Populate subrecord lists with only ID and date
            dto.setGynecologySubRecords(
                    med.getGynecologySubRecords()
                            .stream()
                            .map(gynecologySubRecordService::mapToDateAndId)
                            .toList()
            );
            dto.setFertilitySubRecords(
                    med.getFertilitySubRecords()
                            .stream()
                            .map(fertilitySubRecordService::mapToDateAndId)
                            .toList()
            );
            dto.setObstetricsRecords(
                    med.getObstetricsRecords()
                            .stream()
                            .map(obstetricsRecordService::mapToDateAndId)
                            .toList()
            );
            dto.setMedicalBackgrounds(
                    med.getMedicalBackgrounds().stream()
                            .map(medicalBackgroundService::mapToDTO)
                            .toList()
            );
            return dto;
        }
        return null;
    }

    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public MedicalRecordDTO updateMedicalRecord(Long id, MedicalRecordDTO dto) {
        Optional<MedicalRecord> existing = medicalRecordRepository.findById(id);
        if (existing.isPresent()) {
            MedicalRecord medicalRecord = mapToEntity(dto);
            medicalRecord.setId(id); // Ensure the ID is preserved for update
            MedicalRecord updated = medicalRecordRepository.save(medicalRecord);
            return mapToDTO(updated);
        }
        return null;
    }

    public void deleteMedicalRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }
    public FinalResponse getSummary(long id, String auth){
        Resume resume = resumeService.findByMedicalRecordId(id);
        if(resume==null){
            return this.getResume(id,auth);
        }else {
                Consultation lastConsultation = consultationService.findLastUpdatedConsultation(id);
                if (lastConsultation != null && (resume.getUpdatedAt() == null || lastConsultation.getUpdatedAt().isAfter(resume.getUpdatedAt()))) {
                    FinalResponse summary = this.getResume(id, auth);
                    System.out.println("Generated Summary: " + summary.getSummary());
                    resume.setMedicalRecord(MedicalRecord.builder().id(id).build());
                    resume.setSummary(summary.getSummary());
                    resume.setId(resume.getId());
                    this.resumeService.update(Resume.builder().id(resume.getId()).medicalRecord(resume.getMedicalRecord()).summary(summary.getSummary()).build());
                    return this.getResume(id, auth);
                }else {
                    return FinalResponse.builder().summary(resume.getSummary()).responseType(ResponseType.SUMMARY).unrecognizedAbbreviation(new ArrayList<>()).build();
            }
        }

    }
    public FinalResponse getResume(long id, String auth){
        SummaryRequest summaryRequest = new SummaryRequest();
        summaryRequest.setText(this.toHtmlStructured(id));
       // summaryRequest.setAbbreviations(List.of(new AbbreviationDefinition("VGAA","Vagin anatomique anormal"),new AbbreviationDefinition("ABNP","Absence de battement non périodique"),new AbbreviationDefinition("LUV","Ligament utéro-vésical"),new AbbreviationDefinition("TWH","Test de Whiff")));
        summaryRequest.setAbbreviations(userService.getAbbreviationsByDoctor(auth));
        System.out.println("Abbreviations from user service: " + summaryRequest.getAbbreviations());
        return aiService.toMarkdown(summaryRequest);
    }
    public FinalResponse getResume(long id, String auth,List<AbbreviationDefinition> definitions){
        SummaryRequest summaryRequest = new SummaryRequest();
        summaryRequest.setText(this.toHtmlStructured(id));
        userService.add(auth,definitions);
        summaryRequest.setAbbreviations(userService.getAbbreviationsByDoctor(auth));
        FinalResponse finalResponse=aiService.toMarkdown(summaryRequest);
        Resume resume = resumeService.findByMedicalRecordId(id);
        if(resume!=null){
            resumeService.update(Resume.builder().id(resume.getId()).medicalRecord(MedicalRecord.builder().id(id).build()).summary(resume.getSummary()).build());
        }else {
            resumeService.add(Resume.builder().medicalRecord(MedicalRecord.builder().id(id).build()).summary(finalResponse.getSummary()).build());
        }
        System.out.println("Abbreviations from user service: " + summaryRequest.getAbbreviations());
        return finalResponse;
    }

    public String toHtmlStructured(long id) {
        MedicalRecordDTO medicalRecord = this.getMedicalRecord(id);
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading
        html.append("<h2>Medical Record</h2>");

        // Medical Backgrounds Section
        html.append("<h3>Medical Backgrounds</h3>");
        if (medicalRecord.getMedicalBackgrounds() != null && !medicalRecord.getMedicalBackgrounds().isEmpty()) {
            html.append(medicalBackgroundService.toHtmlTablesByBackgroundType(medicalRecord.getMedicalBackgrounds()));
        } else {
            html.append("<p>No medical backgrounds available</p>");
        }
        html.append("<br>");

        // Gynecology Sub-Records Section
        html.append("<h3>Gynecology Sub-Records</h3>");
        if (medicalRecord.getGynecologySubRecords() != null && !medicalRecord.getGynecologySubRecords().isEmpty()) {
            for (GynecologySubRecordDTO gynecologyRecord : medicalRecord.getGynecologySubRecords()) {
                html.append(gynecologySubRecordService.toHtmlStructured(gynecologyRecord));
            }
        } else {
            html.append("<p>No gynecology sub-records available</p>");
        }
        html.append("<br>");

        // Fertility Sub-Records Section
        html.append("<h3>Fertility Sub-Records</h3>");
        if (medicalRecord.getFertilitySubRecords() != null && !medicalRecord.getFertilitySubRecords().isEmpty()) {
            for (FertilitySubRecordDTO fertilityRecord : medicalRecord.getFertilitySubRecords()) {
                html.append(fertilitySubRecordService.toHtmlStructured(fertilityRecord));
            }
        } else {
            html.append("<p>No fertility sub-records available</p>");
        }
        html.append("<br>");

        // Obstetrics Records Section
        html.append("<h3>Obstetrics Records</h3>");
        if (medicalRecord.getObstetricsRecords() != null && !medicalRecord.getObstetricsRecords().isEmpty()) {
            for (ObstetricsRecordDTO obstetricsRecord : medicalRecord.getObstetricsRecords()) {
                html.append(obstetricsRecordService.toHtmlStructured(obstetricsRecord));
            }
        } else {
            html.append("<p>No obstetrics records available</p>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }
    public List<MedicalRecordDTO> getMedicalRecordsByIds(String auth) {
        String jwtToken = auth.replace("Bearer ", "");
        List<Integer> integerIds = userService.getDoctorsByCabinet(jwtToken);
        return medicalRecordRepository.findByDoctorIdIn(integerIds).stream().map(this::mapToDTO).toList();
    }
    public List<MedicalRecordDTO> getMedicalRecordsNameAndSurname(String auth) {
        String jwtToken = auth.replace("Bearer ", "");
        List<Integer> integerIds = userService.getDoctorsByCabinet(jwtToken);
        return medicalRecordRepository.findByDoctorIdIn(integerIds)
                .stream()
                .map(this::mapToNameAndSurname)
                .toList();
    }
    public MedicalRecordDTO mapToNameAndSurname(MedicalRecord medicalRecord) {
        return MedicalRecordDTO.builder()
                .id(medicalRecord.getId())
                .name(medicalRecord.getName())
                .surname(medicalRecord.getSurname())
                .build();
    }

    private MedicalRecordDTO mapToDTO(MedicalRecord medicalRecord) {
        return MedicalRecordDTO.builder()
                .id(medicalRecord.getId())
                .name(medicalRecord.getName())
                .surname(medicalRecord.getSurname())
                .birthDate(medicalRecord.getBirthDate())
                .sex(medicalRecord.getSex())
                .civilState(medicalRecord.getCivilState())
                .email(medicalRecord.getEmail())
                .doctorId(medicalRecord.getDoctorId())
                .comment(medicalRecord.getComment())
                .build();
    }

    private MedicalRecord mapToEntity(MedicalRecordDTO dto) {
        return MedicalRecord.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .birthDate(dto.getBirthDate())
                .sex(dto.getSex())
                .civilState(dto.getCivilState())
                .email(dto.getEmail())
                .doctorId(dto.getDoctorId())
                .medicalBackgrounds(dto.getMedicalBackgrounds().stream().map(medicalBackgroundService::mapToEntity).toList())
                .comment(dto.getComment())
                .build();
    }
    public String toHtmlReport(long id, ReportRequestDTO reportRequestDTO) {
        //MedicalRecordDTO medicalRecord = this.getMedicalRecord(id);
        StringBuilder html = new StringBuilder();
        html.append("<div>");

        // Main heading
        html.append("<h2>Medical Record</h2>");

        // Medical Backgrounds Section
        if(reportRequestDTO.isAllergiesBackground() || reportRequestDTO.isMedicalBackground() || reportRequestDTO.isFamilialBackground() || reportRequestDTO.isChirurgicalBackground()) {
            html.append("<h3>Medical Backgrounds</h3>");
            //if (medicalRecord.getMedicalBackgrounds() != null && !medicalRecord.getMedicalBackgrounds().isEmpty()) {
                html.append(medicalBackgroundService.toHtmlTablesByBackgroundTypeReport(id, reportRequestDTO));
            //} else {
                html.append("<p>No medical backgrounds available</p>");
            //}
            html.append("<br>");
        }
        // Gynecology Sub-Records Section

        if (reportRequestDTO.getGynecologySubRecords() != null && !reportRequestDTO.getGynecologySubRecords().isEmpty()) {
            for (GynecologySubRecordDTO gynecologyRecord : reportRequestDTO.getGynecologySubRecords()) {
                html.append("<h3>Gynecology Sub-Records</h3>");
                html.append(gynecologySubRecordService.toHtmlReport(gynecologyRecord.getId(),reportRequestDTO));
            }
        } else {
            html.append("<p>No gynecology sub-records available</p>");
        }
        html.append("<br>");

        // Fertility Sub-Records Section
        html.append("<h3>Fertility Sub-Records</h3>");
        if (reportRequestDTO.getFertilitySubRecords() != null && !reportRequestDTO.getFertilitySubRecords().isEmpty()) {
            for (FertilitySubRecordDTO fertilityRecord : reportRequestDTO.getFertilitySubRecords()) {
                html.append(fertilitySubRecordService.toHtmlReport(fertilityRecord.getId(),reportRequestDTO) );
            }
        } else {
            html.append("<p>No fertility sub-records available</p>");
        }
        html.append("<br>");

        // Obstetrics Records Section
        html.append("<h3>Obstetrics Records</h3>");
        if (reportRequestDTO.getObstetricsRecords() != null && !reportRequestDTO.getObstetricsRecords().isEmpty()) {
            for (ObstetricsRecordDTO obstetricsRecord : reportRequestDTO.getObstetricsRecords()) {
                html.append(obstetricsRecordService.toHtmlReport(obstetricsRecord.getId(),reportRequestDTO));
            }
        } else {
            html.append("<p>No obstetrics records available</p>");
        }
        html.append("<br>");

        html.append("</div>");

        return html.toString();
    }

}
