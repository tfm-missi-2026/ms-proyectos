package pe.unir.tfm.srp.proyectos.repository;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pe.unir.tfm.srp.proyectos.model.Subproyecto;

@Mapper
public interface SubproyectoMapper {

    Subproyecto buscarPorId(@Param("id") UUID id);

    List<Subproyecto> listarPorProyecto(@Param("proyectoId") UUID proyectoId);

    List<Subproyecto> listarActivos();

    void insertar(Subproyecto subproyecto);

    void actualizar(Subproyecto subproyecto);

    void eliminarLogico(@Param("id") UUID id,
                        @Param("usuarioEliminacion") UUID usuarioEliminacion,
                        @Param("motivoEliminacion") String motivoEliminacion);
}
