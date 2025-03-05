package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.HistoriaClinicaEntity;
import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.HistoriaClinicaRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class HistoriaClinicaService {

    @Autowired
    HistoriaClinicaRepository historiaClinicaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Transactional
    public HistoriaClinicaEntity createhistoria(HistoriaClinicaEntity historia, Long pacienteID)
            throws IllegalOperationException, EntityNotFoundException {
        log.info("inicia el proceso");
        Optional<PacienteEntity> pacienteEntity = pacienteRepository.findById(pacienteID);
        if (pacienteEntity.isEmpty()) {
            throw new EntityNotFoundException("no se encontro el paciente");
        }

        if (pacienteEntity.get().getAcudiente().equals(null)) {
            historia.getNombre().replaceAll(historia.getNombre(), "HistoriaCompartida");
        }

        return historiaClinicaRepository.save(historia);
    }

}
