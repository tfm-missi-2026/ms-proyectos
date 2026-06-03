package pe.unir.tfm.srp.proyectos.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record TareaResponse(
    UUID id,
    UUID subproyectoId,
    String nombre,
    String descripcion,
    LocalDate fechaInicioPlanificada,
    LocalDate fechaFinPlanificada,
    Short horasEstimadas,
    UUID situacionId,
    UUID origenVariacionId,
    Short estado
) {}
