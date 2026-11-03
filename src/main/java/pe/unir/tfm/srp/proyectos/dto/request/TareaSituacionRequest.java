package pe.unir.tfm.srp.proyectos.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record TareaSituacionRequest(
    @NotNull UUID situacionId
) {}
