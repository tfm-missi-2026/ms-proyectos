package pe.unir.tfm.srp.proyectos.dto.conversor;

import java.util.List;

import org.mapstruct.Mapper;

import pe.unir.tfm.srp.proyectos.dto.response.ProyectoResponse;
import pe.unir.tfm.srp.proyectos.model.Proyecto;

@Mapper
public interface ProyectoConversor {

    ProyectoResponse aProyectoResponse(Proyecto proyecto);

    List<ProyectoResponse> aProyectoResponseList(List<Proyecto> proyectos);
}
