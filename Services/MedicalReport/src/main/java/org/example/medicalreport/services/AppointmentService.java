package org.example.medicalreport.services;

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

    public Appointment saveAppointment(Appointment appointment) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(appointment.getMedicalRecord().getId());
        appointment.setMedicalRecord(medicalRecord);
        return appointmentRepository.save(appointment);
    }
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }
    public List<Appointment> getAppointmentsByCabinetId(long cabinetId) {
        return appointmentRepository.findByCabinetId(cabinetId);
    }
}
