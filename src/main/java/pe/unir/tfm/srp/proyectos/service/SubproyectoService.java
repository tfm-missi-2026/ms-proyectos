package pe.unir.tfm.srp.proyectos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.unir.tfm.srp.proyectos.config.CurrentUserResolver;
import pe.unir.tfm.srp.proyectos.dto.conversor.SubproyectoConversor;
import pe.unir.tfm.srp.proyectos.dto.request.EliminacionRequest;
import pe.unir.tfm.srp.proyectos.dto.request.SubproyectoActualizarRequest;
import pe.unir.tfm.srp.proyectos.dto.request.SubproyectoCrearRequest;
import pe.unir.tfm.srp.proyectos.dto.response.SubproyectoResponse;
import pe.unir.tfm.srp.proyectos.exception.RecursoNoEncontradoException;
import pe.unir.tfm.srp.proyectos.model.Subproyecto;
import pe.unir.tfm.srp.proyectos.repository.ProyectoMapper;
import pe.unir.tfm.srp.proyectos.repository.SubproyectoMapper;

@Service
@RequiredArgsConstructor
public class SubproyectoService {

    private final SubproyectoMapper subproyectoMapper;
    private final ProyectoMapper proyectoMapper;
    private final SubproyectoConversor subproyectoConversor;
    private final CurrentUserResolver currentUserResolver;

    public List<SubproyectoResponse> listar() {
        return subproyectoConversor.aSubproyectoResponseList(subproyectoMapper.listarActivos());
    }

    public List<SubproyectoResponse> listarPorProyecto(UUID proyectoId) {
        if (proyectoMapper.buscarPorId(proyectoId) == null) {
            throw new RecursoNoEncontradoException("Proyecto " + proyectoId + " no encontrado");
        }
        return subproyectoConversor.aSubproyectoResponseList(subproyectoMapper.listarPorProyecto(proyectoId));
    }

    public SubproyectoResponse buscarPorId(UUID id) {
        Subproyecto subproyecto = subproyectoMapper.buscarPorId(id);
        if (subproyecto == null) {
            throw new RecursoNoEncontradoException("Subproyecto " + id + " no encontrado");
        }
        return subproyectoConversor.aSubproyectoResponse(subproyecto);
    }

    @Transactional
    public SubproyectoResponse crear(SubproyectoCrearRequest request) {
        if (proyectoMapper.buscarPorId(request.proyectoId()) == null) {
            throw new RecursoNoEncontradoException("Proyecto " + request.proyectoId() + " no encontrado");
        }
        Subproyecto nuevo = Subproyecto.builder()
                .id(UUID.randomUUID())
                .proyectoId(request.proyectoId())
                .tipoSubproyectoId(request.tipoSubproyectoId())
                .codigoTicket(request.codigoTicket())
                .prioridadId(request.prioridadId())
                .descripcion(request.descripcion())
                .solicitanteId(request.solicitanteId())
                .fechaSolicitud(request.fechaSolicitud())
                .situacionId(request.situacionId())
                .build();
        subproyectoMapper.insertar(nuevo);
        return subproyectoConversor.aSubproyectoResponse(nuevo);
    }

    @Transactional
    public SubproyectoResponse actualizar(UUID id, SubproyectoActualizarRequest request) {
        Subproyecto existente = subproyectoMapper.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNoEncontradoException("Subproyecto " + id + " no encontrado");
        }
        existente.setTipoSubproyectoId(request.tipoSubproyectoId());
        existente.setCodigoTicket(request.codigoTicket());
        existente.setPrioridadId(request.prioridadId());
        existente.setDescripcion(request.descripcion());
        existente.setSituacionId(request.situacionId());
        existente.setJustificacionRechazo(request.justificacionRechazo());
        subproyectoMapper.actualizar(existente);
        return subproyectoConversor.aSubproyectoResponse(existente);
    }

    @Transactional
    public void eliminar(UUID id, EliminacionRequest request) {
        if (subproyectoMapper.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Subproyecto " + id + " no encontrado");
        }
        subproyectoMapper.eliminarLogico(id, currentUserResolver.obtenerUsuarioActualId(), request.motivoEliminacion());
    }
}
