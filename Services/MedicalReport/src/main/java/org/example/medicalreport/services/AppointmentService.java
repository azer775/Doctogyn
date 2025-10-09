package org.example.medicalreport.services;

import org.example.medicalreport.Models.DTOs.AppointmentDTO;
import org.example.medicalreport.Models.entities.Appointment;
import org.example.medicalreport.Models.entities.MedicalRecord;
import org.example.medicalreport.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    MedicalRecordService medicalRecordService;
    public AppointmentDTO saveAppointment(Appointment appointment) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(appointment.getMedicalRecord().getId());
        appointment.setMedicalRecord(medicalRecord);
        return MapToDTO(appointmentRepository.save(appointment));
    }
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }
    public List<AppointmentDTO> getAppointmentsByCabinetId(long cabinetId) {
        List<Appointment> appointments = appointmentRepository.findByCabinetId(cabinetId);
        return appointmentRepository.findByCabinetId(cabinetId).stream().map(this::MapToDTO).toList();
    }
    public AppointmentDTO MapToDTO(Appointment appointment) {
        return org.example.medicalreport.Models.DTOs.AppointmentDTO.builder()
                .id(appointment.getId())
                .date(appointment.getDate())
                .reason(appointment.getReason())
                .consultationType(appointment.getConsultationType())
                .cabinetId(appointment.getCabinetId())
                .medicalRecord(medicalRecordService.mapToNameAndSurname(appointment.getMedicalRecord()))
                .build();
    }
}
