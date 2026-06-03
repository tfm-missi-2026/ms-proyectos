package pe.unir.tfm.srp.proyectos.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SubproyectoActualizarRequest(
    @NotNull UUID tipoSubproyectoId,
    @Size(max = 50) String codigoTicket,
    @NotNull UUID prioridadId,
    @NotBlank String descripcion,
    @NotNull UUID situacionId,
    @Size(max = 500) String justificacionRechazo,
    @NotNull Short estado
) {}
