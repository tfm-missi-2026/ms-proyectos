package pe.unir.tfm.srp.proyectos.repository;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pe.unir.tfm.srp.proyectos.model.Tarea;

@Mapper
public interface TareaMapper {

    Tarea buscarPorId(@Param("id") UUID id);

    List<Tarea> listarPorSubproyecto(@Param("subproyectoId") UUID subproyectoId);

    List<Tarea> listarActivas();

    void insertar(Tarea tarea);

    void actualizar(Tarea tarea);

    void eliminarLogico(@Param("id") UUID id,
                        @Param("usuarioEliminacion") UUID usuarioEliminacion,
                        @Param("motivoEliminacion") String motivoEliminacion);
}
