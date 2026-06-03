package pe.unir.tfm.srp.proyectos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.unir.tfm.srp.proyectos.config.CurrentUserResolver;
import pe.unir.tfm.srp.proyectos.dto.conversor.ProyectoConversor;
import pe.unir.tfm.srp.proyectos.dto.request.EliminacionRequest;
import pe.unir.tfm.srp.proyectos.dto.request.ProyectoActualizarRequest;
import pe.unir.tfm.srp.proyectos.dto.request.ProyectoCrearRequest;
import pe.unir.tfm.srp.proyectos.dto.response.ProyectoResponse;
import pe.unir.tfm.srp.proyectos.exception.ConflictoNegocioException;
import pe.unir.tfm.srp.proyectos.exception.RecursoNoEncontradoException;
import pe.unir.tfm.srp.proyectos.model.Proyecto;
import pe.unir.tfm.srp.proyectos.repository.ProyectoMapper;

@Service
@RequiredArgsConstructor
public class ProyectoService {

    private final ProyectoMapper proyectoMapper;
    private final ProyectoConversor proyectoConversor;
    private final CurrentUserResolver currentUserResolver;

    public List<ProyectoResponse> listar() {
        return proyectoConversor.aProyectoResponseList(proyectoMapper.listarActivos());
    }

    public ProyectoResponse buscarPorId(UUID id) {
        Proyecto proyecto = proyectoMapper.buscarPorId(id);
        if (proyecto == null) {
            throw new RecursoNoEncontradoException("Proyecto " + id + " no encontrado");
        }
        return proyectoConversor.aProyectoResponse(proyecto);
    }

    @Transactional
    public ProyectoResponse crear(ProyectoCrearRequest request) {
        if (request.nombreCorto() != null && !request.nombreCorto().isBlank()
                && proyectoMapper.buscarPorNombreCorto(request.nombreCorto()) != null) {
            throw new ConflictoNegocioException(
                    "Ya existe un proyecto con nombre_corto " + request.nombreCorto());
        }
        Proyecto nuevo = Proyecto.builder()
                .id(UUID.randomUUID())
                .nombreCorto(request.nombreCorto())
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .gestorId(request.gestorId())
                .build();
        proyectoMapper.insertar(nuevo);
        return proyectoConversor.aProyectoResponse(nuevo);
    }

    @Transactional
    public ProyectoResponse actualizar(UUID id, ProyectoActualizarRequest request) {
        Proyecto existente = proyectoMapper.buscarPorId(id);
        if (existente == null) {
            throw new RecursoNoEncontradoException("Proyecto " + id + " no encontrado");
        }
        existente.setNombreCorto(request.nombreCorto());
        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        existente.setGestorId(request.gestorId());
        existente.setEstado(request.estado());
        proyectoMapper.actualizar(existente);
        return proyectoConversor.aProyectoResponse(existente);
    }

    @Transactional
    public void eliminar(UUID id, EliminacionRequest request) {
        if (proyectoMapper.buscarPorId(id) == null) {
            throw new RecursoNoEncontradoException("Proyecto " + id + " no encontrado");
        }
        proyectoMapper.eliminarLogico(id, currentUserResolver.obtenerUsuarioActualId(), request.motivoEliminacion());
    }
}
