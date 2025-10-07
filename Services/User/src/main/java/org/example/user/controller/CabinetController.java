package org.example.user.controller;

import org.example.user.model.entities.Cabinet;
import org.example.user.service.CabinetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cabinet")
public class CabinetController {
    @Autowired
    CabinetService cabinetService;

    @PutMapping("/update")
    public Cabinet updateCabinet(@RequestBody Cabinet cabinet) {
        System.out.println("Updating cabinet: " + cabinet);
        return cabinetService.updateCabinet(cabinet);
    }
}
