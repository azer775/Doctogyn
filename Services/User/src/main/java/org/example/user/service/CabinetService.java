package org.example.user.service;

import org.example.user.model.entities.Cabinet;
import org.example.user.repository.CabinetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CabinetService {
    @Autowired
    CabinetRepository cabinetRepository;

    public Cabinet updateCabinet(Cabinet cabinet) {
        return cabinetRepository.save(cabinet);
    }
}
