package pe.unir.tfm.srp.proyectos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.unir.tfm.srp.proyectos.config.CurrentUserResolver;
import pe.unir.tfm.srp.proyectos.dto.conversor.TareaConversor;
import pe.unir.tfm.srp.proyectos.dto.request.EliminacionRequest;
import pe.unir.tfm.srp.proyectos.dto.request.TareaActualizarRequest;
import pe.unir.tfm.srp.proyectos.dto.request.TareaCrearRequest;
import pe.unir.tfm.srp.proyectos.dto.response.TareaResponse;
import pe.unir.tfm.srp.proyectos.exception.RecursoNoEncontradoException;
import pe.unir.tfm.srp.proyectos.model.Tarea;
import pe.unir.tfm.srp.proyectos.repository.SubproyectoMapper;
import pe.unir.tfm.srp.proyectos.repository.TareaMapper;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaMapper tareaMapper;
    private final SubproyectoMapper subproyectoMapper;
    private final TareaConversor tareaConversor;
    private final CurrentUserResolver currentUserResolver;

    public List<TareaResponse> listar() {
        return tareaConversor.aTareaResponseList(tareaMapper.listarActivas());
    }

    public List<TareaResponse> listarPorSubproyecto(UUID subproyectoId) {
        if (subproyectoMapper.buscarPorId(subproyectoId) == null) {
            throw new RecursoNoEncontradoException("Subproyecto " + subproyectoId + " no encontrado");
        }
        return tareaConversor.aTareaResponseList(tareaMapper.listarPorSubproyecto(subproyectoId));
    }

    public TareaResponse buscarPorId(UUID id) {
        Tarea tarea = tareaMapper.buscarPorId(id);
        if (tarea == null) {
            throw new RecursoNoEncontradoException("Tarea " + id + " no encontrada");
        }
        return tareaConversor.aTareaResponse(tarea);
    }

    @Transactional
    public TareaResponse crear(TareaCrearRequest request) {
        if (subproyectoMapper.buscarPorId(request.subproyectoId()) == null) {
            throw new RecursoNoEncontradoException("Subproyecto " + request.subproyectoId() + " no encontrado");
        }
        Tarea nueva = Tarea.builder()
                .id(UUID.randomUUID())
                .subproyectoId(request.subproyectoId())
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .fechaInicioPlanificada(request.fechaInicioPlanificada())
                .fechaFinPlanificada(request.fechaFinPlanificada())
                .horasEstimadas(request.horasEstimadas())
                .situacionId(request.situacionId())
                .origenVariacionId(request.origenVariacionId())
                .build();
        tareaMapper.insertar(nueva);
        return tareaConversor.aTareaResponse(nueva);
    }

    @Transactional
    public TareaResponse actualizar(UUID id, TareaActualizarRequest request) {
        Tarea existente = tareaMapper.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNoEncontradoException("Tarea " + id + " no encontrada");
        }
        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        existente.setFechaInicioPlanificada(request.fechaInicioPlanificada());
        existente.setFechaFinPlanificada(request.fechaFinPlanificada());
        existente.setHorasEstimadas(request.horasEstimadas());
        existente.setSituacionId(request.situacionId());
        existente.setOrigenVariacionId(request.origenVariacionId());
        tareaMapper.actualizar(existente);
        return tareaConversor.aTareaResponse(existente);
    }

    @Transactional
    public void eliminar(UUID id, EliminacionRequest request) {
        if (tareaMapper.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Tarea " + id + " no encontrada");
        }
        tareaMapper.eliminarLogico(id, currentUserResolver.obtenerUsuarioActualId(), request.motivoEliminacion());
    }
}
