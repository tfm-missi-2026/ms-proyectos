package pe.unir.tfm.srp.proyectos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.unir.tfm.srp.proyectos.config.CurrentUserResolver;
import pe.unir.tfm.srp.proyectos.dto.conversor.TareaConversor;
import pe.unir.tfm.srp.proyectos.dto.request.TareaSituacionRequest;
import pe.unir.tfm.srp.proyectos.exception.RecursoNoEncontradoException;
import pe.unir.tfm.srp.proyectos.model.Tarea;
import pe.unir.tfm.srp.proyectos.repository.SubproyectoMapper;
import pe.unir.tfm.srp.proyectos.repository.TareaMapper;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    private static final UUID TAREA = UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final UUID SITUACION = UUID.fromString("44444444-4444-4444-4444-444444444444");

    @Mock
    private TareaMapper tareaMapper;

    @Mock
    private SubproyectoMapper subproyectoMapper;

    @Mock
    private TareaConversor tareaConversor;

    @Mock
    private CurrentUserResolver currentUserResolver;

    @InjectMocks
    private TareaService tareaService;

    @Test
    void actualizarSituacion_cambiaSoloLaSituacion() {
        Tarea existente = Tarea.builder().id(TAREA).nombre("Tarea").build();
        when(tareaMapper.buscarPorId(TAREA)).thenReturn(existente);

        tareaService.actualizarSituacion(TAREA, new TareaSituacionRequest(SITUACION));

        ArgumentCaptor<Tarea> guardada = ArgumentCaptor.forClass(Tarea.class);
        verify(tareaMapper).actualizarSituacion(guardada.capture());
        assertThat(guardada.getValue().getSituacionId()).isEqualTo(SITUACION);
        assertThat(guardada.getValue().getNombre()).isEqualTo("Tarea");
    }

    @Test
    void actualizarSituacion_conTareaInexistente_lanzaNoEncontrado() {
        when(tareaMapper.buscarPorId(TAREA)).thenReturn(null);

        assertThatThrownBy(() -> tareaService.actualizarSituacion(TAREA, new TareaSituacionRequest(SITUACION)))
                .isInstanceOf(RecursoNoEncontradoException.class);

        verify(tareaMapper, never()).actualizarSituacion(any());
    }
}
