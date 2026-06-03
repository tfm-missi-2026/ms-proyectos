package pe.unir.tfm.srp.proyectos.dto.response;

import java.util.UUID;

public record ProyectoResponse(
    UUID id,
    String nombreCorto,
    String nombre,
    String descripcion,
    UUID gestorId,
    Short estado
) {}
