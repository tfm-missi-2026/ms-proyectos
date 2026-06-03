package pe.unir.tfm.srp.proyectos.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.unir.tfm.srp.proyectos.dto.request.EliminacionRequest;
import pe.unir.tfm.srp.proyectos.dto.request.ProyectoActualizarRequest;
import pe.unir.tfm.srp.proyectos.dto.request.ProyectoCrearRequest;
import pe.unir.tfm.srp.proyectos.dto.response.ProyectoResponse;
import pe.unir.tfm.srp.proyectos.service.ProyectoService;

@RestController
@RequestMapping("/api/proyectos")
@RequiredArgsConstructor
public class ProyectoController {

    private final ProyectoService proyectoService;

    @GetMapping
    public ResponseEntity<List<ProyectoResponse>> listar() {
        return ResponseEntity.ok(proyectoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProyectoResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(proyectoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO')")
    public ResponseEntity<ProyectoResponse> crear(@Valid @RequestBody ProyectoCrearRequest request) {
        return ResponseEntity.ok(proyectoService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO')")
    public ResponseEntity<ProyectoResponse> actualizar(@PathVariable UUID id,
                                                       @Valid @RequestBody ProyectoActualizarRequest request) {
        return ResponseEntity.ok(proyectoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA')")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id, @Valid @RequestBody EliminacionRequest request) {
        proyectoService.eliminar(id, request);
        return ResponseEntity.noContent().build();
    }
}
