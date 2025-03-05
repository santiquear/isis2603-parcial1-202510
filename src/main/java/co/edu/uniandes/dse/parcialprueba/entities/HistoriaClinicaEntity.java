package co.edu.uniandes.dse.parcialprueba.entities;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class HistoriaClinicaEntity extends BaseEntity {
    private String nombre;
    private String diagnostico;
    private String tratamiento;
    private String fechadecreacion;

    @PodamExclude
    @ManyToOne
    private PacienteEntity pacientes;

}