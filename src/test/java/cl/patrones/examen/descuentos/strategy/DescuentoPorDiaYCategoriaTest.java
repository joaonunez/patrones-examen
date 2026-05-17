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

class DescuentoPorDiaYCategoriaTest {

	private final DescuentoPorDiaYCategoria strategy = new DescuentoPorDiaYCategoria();

	@Test
	void lunesYCompresorAplicaSeisPorCiento() {
		ContextoDescuento contexto = contexto(DiaSemana.LUNES, CategoriaProducto.COMPRESOR_AIRE);

		assertTrue(strategy.aplica(contexto));
		assertEquals(6, strategy.calcularPorcentaje(contexto));
	}

	@Test
	void martesYEsmerilAplicaOchoPorCiento() {
		ContextoDescuento contexto = contexto(DiaSemana.MARTES, CategoriaProducto.ESMERIL_ANGULAR);

		assertTrue(strategy.aplica(contexto));
		assertEquals(8, strategy.calcularPorcentaje(contexto));
	}

	@Test
	void miercolesYTaladroAplicaDiezPorCiento() {
		ContextoDescuento contexto = contexto(DiaSemana.MIERCOLES, CategoriaProducto.TALADRO_PERCUTOR);

		assertTrue(strategy.aplica(contexto));
		assertEquals(10, strategy.calcularPorcentaje(contexto));
	}

	@Test
	void diaCorrectoConCategoriaIncorrectaNoAplica() {
		ContextoDescuento contexto = contexto(DiaSemana.LUNES, CategoriaProducto.TALADRO_PERCUTOR);

		assertFalse(strategy.aplica(contexto));
		assertEquals(0, strategy.calcularPorcentaje(contexto));
	}

	@Test
	void categoriaCorrectaConDiaIncorrectoNoAplica() {
		ContextoDescuento contexto = contexto(DiaSemana.JUEVES, CategoriaProducto.COMPRESOR_AIRE);

		assertFalse(strategy.aplica(contexto));
		assertEquals(0, strategy.calcularPorcentaje(contexto));
	}

	private ContextoDescuento contexto(DiaSemana dia, CategoriaProducto categoria) {
		return new ContextoDescuento(
				mock(Producto.class),
				new UsuarioActual("juan", TipoUsuario.CLIENTE),
				dia,
				categoria);
	}
}
