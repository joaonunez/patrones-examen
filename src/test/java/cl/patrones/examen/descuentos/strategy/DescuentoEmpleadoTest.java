package cl.patrones.examen.descuentos.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.descuentos.model.CategoriaProducto;
import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.model.DiaSemana;
import cl.patrones.examen.descuentos.model.TipoUsuario;
import cl.patrones.examen.descuentos.model.UsuarioActual;
import cl.patrones.examen.productos.domain.Producto;

class DescuentoEmpleadoTest {

	private final DescuentoEmpleado strategy = new DescuentoEmpleado();

	@Test
	void empleadoRecibeCincoPorCiento() {
		ContextoDescuento contexto = contexto(TipoUsuario.EMPLEADO);

		assertTrue(strategy.aplica(contexto));
		assertEquals(5, strategy.calcularPorcentaje(contexto));
	}

	@Test
	void clienteNoRecibeDescuentoDeEmpleado() {
		ContextoDescuento contexto = contexto(TipoUsuario.CLIENTE);

		assertFalse(strategy.aplica(contexto));
		assertEquals(0, strategy.calcularPorcentaje(contexto));
	}

	private ContextoDescuento contexto(TipoUsuario tipoUsuario) {
		return new ContextoDescuento(
				mock(Producto.class),
				new UsuarioActual("usuario", tipoUsuario),
				DiaSemana.LUNES,
				CategoriaProducto.COMPRESOR_AIRE);
	}
}
