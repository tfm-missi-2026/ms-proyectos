package pe.unir.tfm.srp.proyectos.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProyectoCrearRequest(
    @Size(max = 50) String nombreCorto,
    @NotBlank @Size(max = 200) String nombre,
    String descripcion,
    @NotNull UUID gestorId
) {}
