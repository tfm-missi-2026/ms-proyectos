package pe.unir.tfm.srp.proyectos.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record SubproyectoResponse(
    UUID id,
    UUID proyectoId,
    UUID tipoSubproyectoId,
    String codigoTicket,
    UUID prioridadId,
    String descripcion,
    UUID solicitanteId,
    LocalDate fechaSolicitud,
    UUID situacionId,
    String justificacionRechazo,
    Short estado
) {}
