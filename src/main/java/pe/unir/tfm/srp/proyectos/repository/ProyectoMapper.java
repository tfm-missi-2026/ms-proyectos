package pe.unir.tfm.srp.proyectos.repository;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import pe.unir.tfm.srp.proyectos.model.Proyecto;

@Mapper
public interface ProyectoMapper {

    Proyecto buscarPorId(@Param("id") UUID id);

    Proyecto buscarPorNombreCorto(@Param("nombreCorto") String nombreCorto);

    List<Proyecto> listarActivos();

    void insertar(Proyecto proyecto);

    void actualizar(Proyecto proyecto);

    void eliminarLogico(@Param("id") UUID id,
                        @Param("usuarioEliminacion") UUID usuarioEliminacion,
                        @Param("motivoEliminacion") String motivoEliminacion);
}
