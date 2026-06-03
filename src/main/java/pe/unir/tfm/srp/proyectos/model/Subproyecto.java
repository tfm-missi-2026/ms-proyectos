package pe.unir.tfm.srp.proyectos.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subproyecto {
    private UUID id;
    private UUID proyectoId;
    private UUID tipoSubproyectoId;
    private String codigoTicket;
    private UUID prioridadId;
    private String descripcion;
    private UUID solicitanteId;
    private LocalDate fechaSolicitud;
    private UUID situacionId;
    private String justificacionRechazo;
    private Short estado;
    private LocalDateTime fechaCreacion;
    private UUID usuarioCreacion;
    private LocalDateTime fechaModificacion;
    private UUID usuarioModificacion;
    private LocalDateTime fechaEliminacion;
    private UUID usuarioEliminacion;
    private String motivoEliminacion;
}
