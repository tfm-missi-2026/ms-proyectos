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
import pe.unir.tfm.srp.proyectos.dto.request.TareaActualizarRequest;
import pe.unir.tfm.srp.proyectos.dto.request.TareaCrearRequest;
import pe.unir.tfm.srp.proyectos.dto.response.TareaResponse;
import pe.unir.tfm.srp.proyectos.service.TareaService;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public ResponseEntity<List<TareaResponse>> listar() {
        return ResponseEntity.ok(tareaService.listar());
    }

    @GetMapping("/por-subproyecto/{subproyectoId}")
    public ResponseEntity<List<TareaResponse>> listarPorSubproyecto(@PathVariable UUID subproyectoId) {
        return ResponseEntity.ok(tareaService.listarPorSubproyecto(subproyectoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(tareaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO')")
    public ResponseEntity<TareaResponse> crear(@Valid @RequestBody TareaCrearRequest request) {
        return ResponseEntity.ok(tareaService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO')")
    public ResponseEntity<TareaResponse> actualizar(@PathVariable UUID id,
                                                    @Valid @RequestBody TareaActualizarRequest request) {
        return ResponseEntity.ok(tareaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JEFE_AREA', 'GESTOR_PROYECTO')")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id, @Valid @RequestBody EliminacionRequest request) {
        tareaService.eliminar(id, request);
        return ResponseEntity.noContent().build();
    }
}
