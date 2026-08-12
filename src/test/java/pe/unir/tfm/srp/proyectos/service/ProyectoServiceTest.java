package pe.unir.tfm.srp.proyectos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.unir.tfm.srp.proyectos.config.CurrentUserResolver;
import pe.unir.tfm.srp.proyectos.dto.conversor.ProyectoConversor;
import pe.unir.tfm.srp.proyectos.dto.request.EliminacionRequest;
import pe.unir.tfm.srp.proyectos.dto.request.ProyectoCrearRequest;
import pe.unir.tfm.srp.proyectos.dto.response.ProyectoResponse;
import pe.unir.tfm.srp.proyectos.exception.ConflictoNegocioException;
import pe.unir.tfm.srp.proyectos.exception.RecursoNoEncontradoException;
import pe.unir.tfm.srp.proyectos.model.Proyecto;
import pe.unir.tfm.srp.proyectos.repository.ProyectoMapper;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceTest {

    private static final UUID ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID GESTOR = UUID.fromString("00000000-0000-0000-0000-000000000900");

    @Mock private ProyectoMapper proyectoMapper;
    @Mock private ProyectoConversor proyectoConversor;
    @Mock private CurrentUserResolver currentUserResolver;

    @InjectMocks private ProyectoService proyectoService;

    private ProyectoCrearRequest crearRequest(String nombreCorto) {
        return new ProyectoCrearRequest(nombreCorto, "Sistema X", "desc", GESTOR);
    }

    @Test
    void crear_nombreCortoDuplicado_lanzaConflicto() {
        when(proyectoMapper.buscarPorNombreCorto("SIGX")).thenReturn(new Proyecto());

        assertThatThrownBy(() -> proyectoService.crear(crearRequest("SIGX")))
                .isInstanceOf(ConflictoNegocioException.class)
                .hasMessageContaining("SIGX");

        verify(proyectoMapper, never()).insertar(any());
    }

    @Test
    void crear_nombreCortoNull_noValidaUnicidad() {
        // Cuando nombreCorto es null, el if corto-circuita sin llamar a
        // buscarPorNombreCorto. No se stubbea el mapper.
        when(proyectoConversor.aProyectoResponse(any(Proyecto.class)))
                .thenReturn(new ProyectoResponse(UUID.randomUUID(), null, "Sistema X", "desc", GESTOR, (short) 1));

        ProyectoResponse resp = proyectoService.crear(crearRequest(null));

        assertThat(resp.nombre()).isEqualTo("Sistema X");
        verify(proyectoMapper).insertar(any(Proyecto.class));
    }

    @Test
    void crear_nombreCortoVacio_noValidaUnicidad() {
        // Cuando nombreCorto es "", isBlank() es true y el if corto-circuita
        // sin llamar a buscarPorNombreCorto. No se stubbea el mapper.
        when(proyectoConversor.aProyectoResponse(any(Proyecto.class)))
                .thenReturn(new ProyectoResponse(UUID.randomUUID(), "", "Sistema X", "desc", GESTOR, (short) 1));

        proyectoService.crear(crearRequest(""));

        verify(proyectoMapper).insertar(any(Proyecto.class));
    }

    @Test
    void buscarPorId_noExiste_lanzaRecursoNoEncontrado() {
        when(proyectoMapper.buscarPorId(ID)).thenReturn(null);

        assertThatThrownBy(() -> proyectoService.buscarPorId(ID))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void eliminar_noExiste_lanzaRecursoNoEncontrado() {
        when(proyectoMapper.buscarPorId(ID)).thenReturn(null);

        assertThatThrownBy(() -> proyectoService.eliminar(
                ID, new EliminacionRequest("Baja")))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    void eliminar_existe_usaUsuarioActual() {
        when(proyectoMapper.buscarPorId(ID)).thenReturn(new Proyecto());
        UUID currentUser = UUID.fromString("00000000-0000-0000-0000-000000000099");
        when(currentUserResolver.obtenerUsuarioActualId()).thenReturn(currentUser);

        proyectoService.eliminar(ID, new EliminacionRequest("Baja"));

        verify(proyectoMapper).eliminarLogico(eq(ID), eq(currentUser), eq("Baja"));
    }

    @Test
    void listar_delegaMappersYConversor() {
        when(proyectoMapper.listarActivos()).thenReturn(List.of());
        when(proyectoConversor.aProyectoResponseList(List.of())).thenReturn(List.of());

        List<ProyectoResponse> resultado = proyectoService.listar();

        assertThat(resultado).isEmpty();
        verify(proyectoMapper).listarActivos();
    }
}
