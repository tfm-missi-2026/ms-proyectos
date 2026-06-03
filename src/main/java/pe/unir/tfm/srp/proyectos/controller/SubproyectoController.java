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
import pe.unir.tfm.srp.proyectos.dto.request.SubproyectoActualizarRequest;
import pe.unir.tfm.srp.proyectos.dto.request.SubproyectoCrearRequest;
import pe.unir.tfm.srp.proyectos.dto.response.SubproyectoResponse;
import pe.unir.tfm.srp.proyectos.service.SubproyectoService;

@RestController
@RequestMapping("/api/subproyectos")
@RequiredArgsConstructor
public class SubproyectoController {

    private final SubproyectoService subproyectoService;

    @GetMapping
    public ResponseEntity<List<SubproyectoResponse>> listar() {
        return ResponseEntity.ok(subproyectoService.listar());
    }

    @GetMapping("/por-proyecto/{proyectoId}")
    public ResponseEntity<List<SubproyectoResponse>> listarPorProyecto(@PathVariable UUID proyectoId) {
        return ResponseEntity.ok(subproyectoService.listarPorProyecto(proyectoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubproyectoResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(subproyectoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO', 'RECURSO_TECNICO')")
    public ResponseEntity<SubproyectoResponse> crear(@Valid @RequestBody SubproyectoCrearRequest request) {
        return ResponseEntity.ok(subproyectoService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO')")
    public ResponseEntity<SubproyectoResponse> actualizar(@PathVariable UUID id,
                                                          @Valid @RequestBody SubproyectoActualizarRequest request) {
        return ResponseEntity.ok(subproyectoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO')")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id, @Valid @RequestBody EliminacionRequest request) {
        subproyectoService.eliminar(id, request);
        return ResponseEntity.noContent().build();
    }
}
