package pe.unir.tfm.srp.proyectos.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubproyectoCrearRequest(
    @NotNull UUID proyectoId,
    @NotNull UUID tipoSubproyectoId,
    @Size(max = 50) String codigoTicket,
    @NotNull UUID prioridadId,
    @NotBlank String descripcion,
    @NotNull UUID solicitanteId,
    @NotNull LocalDate fechaSolicitud,
    @NotNull UUID situacionId
) {}
