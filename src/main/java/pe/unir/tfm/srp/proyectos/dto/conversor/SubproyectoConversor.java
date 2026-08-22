package pe.unir.tfm.srp.proyectos.dto.conversor;

import java.util.List;

import org.mapstruct.Mapper;

import pe.unir.tfm.srp.proyectos.dto.response.SubproyectoResponse;
import pe.unir.tfm.srp.proyectos.model.Subproyecto;

@Mapper(componentModel = "spring")
public interface SubproyectoConversor {

    SubproyectoResponse aSubproyectoResponse(Subproyecto subproyecto);

    List<SubproyectoResponse> aSubproyectoResponseList(List<Subproyecto> subproyectos);
}
