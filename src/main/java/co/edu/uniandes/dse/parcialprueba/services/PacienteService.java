package co.edu.uniandes.dse.parcialprueba.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.PacienteEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.PacienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PacienteService {

    @Autowired
    PacienteRepository pacienteRepository;

    @Transactional
    public PacienteEntity createpaciente(PacienteEntity paciente) throws IllegalOperationException {
        log.info("inicia proceso de creacion de paciente");

        if (!(paciente.getTelefono().length() == 11)) {
            throw new IllegalOperationException("El numero debe tener 11 digitos");
        }
        if (!(paciente.getTelefono().startsWith("311") || paciente.getTelefono().startsWith("601"))) {
            throw new IllegalOperationException("el numero debe empezar por 311 o 601");
        }

        return pacienteRepository.save(paciente);
    }

    @Transactional
    public PacienteEntity asociarpacientes(Long pacienteID, long acudienteId)
            throws IllegalOperationException, EntityNotFoundException {
        log.info("inicia el proceso de asociar pacientes");
        Optional<PacienteEntity> pacienteEntity = pacienteRepository.findById(pacienteID);
        if (pacienteEntity.isEmpty()) {
            throw new EntityNotFoundException("no se encontro el paciente");
        }

        Optional<PacienteEntity> acudienteEntity = pacienteRepository.findById(acudienteId);
        if (acudienteEntity.isEmpty()) {
            throw new EntityNotFoundException("no se encontro el acudiente");
        }

        if (!(pacienteEntity.get().getAcudiente().equals(null))
                && !(acudienteEntity.get().getHistoria().equals(null))) {
            throw new IllegalOperationException("no se puede asociar por reglas de negocio");
        }

        pacienteEntity.get().getAcudiente().setAcudiente(acudienteEntity.get());
        log.info("termina");
        return pacienteEntity.get();
    }

}
