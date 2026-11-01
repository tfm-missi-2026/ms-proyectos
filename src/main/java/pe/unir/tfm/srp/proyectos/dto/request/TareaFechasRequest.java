package pe.unir.tfm.srp.proyectos.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record TareaFechasRequest(
    @NotNull LocalDate fechaInicioPlanificada,
    @NotNull LocalDate fechaFinPlanificada
) {}
