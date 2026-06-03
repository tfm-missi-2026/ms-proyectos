package pe.unir.tfm.srp.proyectos.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TareaActualizarRequest(
    @NotBlank @Size(max = 200) String nombre,
    String descripcion,
    @NotNull LocalDate fechaInicioPlanificada,
    @NotNull LocalDate fechaFinPlanificada,
    @NotNull @Positive Short horasEstimadas,
    @NotNull UUID situacionId,
    UUID origenVariacionId,
    @NotNull Short estado
) {}
