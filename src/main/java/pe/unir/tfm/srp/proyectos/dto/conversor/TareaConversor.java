package pe.unir.tfm.srp.proyectos.dto.conversor;

import java.util.List;

import org.mapstruct.Mapper;

import pe.unir.tfm.srp.proyectos.dto.response.TareaResponse;
import pe.unir.tfm.srp.proyectos.model.Tarea;

@Mapper
public interface TareaConversor {

    TareaResponse aTareaResponse(Tarea tarea);

    List<TareaResponse> aTareaResponseList(List<Tarea> tareas);
}
