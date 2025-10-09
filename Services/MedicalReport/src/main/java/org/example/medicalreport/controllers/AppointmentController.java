package org.example.medicalreport.controllers;

import org.example.medicalreport.Models.DTOs.AppointmentDTO;
import org.example.medicalreport.Models.entities.Appointment;
import org.example.medicalreport.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@CrossOrigin("*")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/add")
    public AppointmentDTO createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }
    @GetMapping("/getByCabinet/{cabinetId}")
    public List<AppointmentDTO> getAppointmentByCabinetId(@PathVariable long cabinetId) {
        return appointmentService.getAppointmentsByCabinetId(cabinetId);
    }

}
