package cl.patrones.examen.descuentos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.descuentos.model.CategoriaProducto;
import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.model.DiaSemana;
import cl.patrones.examen.descuentos.model.TipoUsuario;
import cl.patrones.examen.descuentos.model.UsuarioActual;
import cl.patrones.examen.descuentos.strategy.DescuentoEmpleado;
import cl.patrones.examen.descuentos.strategy.DescuentoPorDiaYCategoria;
import cl.patrones.examen.productos.domain.Producto;

class ServicioDescuentoTest {

	private final ServicioDescuento servicio = new ServicioDescuento(List.of(
			new DescuentoEmpleado(),
			new DescuentoPorDiaYCategoria()));

	@Test
	void siSoloAplicaEmpleadoRetornaCincoPorCiento() {
		Producto producto = productoConPrecio(100_000L);
		ContextoDescuento contexto = contexto(producto, DiaSemana.DOMINGO, CategoriaProducto.COMPRESOR_AIRE,
				TipoUsuario.EMPLEADO);

		assertEquals(5, servicio.seleccionarMayorPorcentaje(contexto));
		assertEquals(5_000L, servicio.calcularDescuento(producto, contexto));
	}

	@Test
	void siAplicaEmpleadoYCompresorLunesRetornaSeisPorCiento() {
		Producto producto = productoConPrecio(100_000L);
		ContextoDescuento contexto = contexto(producto, DiaSemana.LUNES, CategoriaProducto.COMPRESOR_AIRE,
				TipoUsuario.EMPLEADO);

		assertEquals(6, servicio.seleccionarMayorPorcentaje(contexto));
		assertEquals(94_000L, servicio.calcularPrecioFinal(producto, contexto));
	}

	@Test
	void siAplicaEmpleadoYEsmerilMartesRetornaOchoPorCiento() {
		Producto producto = productoConPrecio(100_000L);
		ContextoDescuento contexto = contexto(producto, DiaSemana.MARTES, CategoriaProducto.ESMERIL_ANGULAR,
				TipoUsuario.EMPLEADO);

		assertEquals(8, servicio.seleccionarMayorPorcentaje(contexto));
		assertEquals(92_000L, servicio.calcularPrecioFinal(producto, contexto));
	}

	@Test
	void siAplicaEmpleadoYTaladroMiercolesRetornaDiezPorCiento() {
		Producto producto = productoConPrecio(100_000L);
		ContextoDescuento contexto = contexto(producto, DiaSemana.MIERCOLES, CategoriaProducto.TALADRO_PERCUTOR,
				TipoUsuario.EMPLEADO);

		assertEquals(10, servicio.seleccionarMayorPorcentaje(contexto));
		assertEquals(90_000L, servicio.calcularPrecioFinal(producto, contexto));
	}

	@Test
	void siNoAplicaNingunaReglaRetornaCeroPorCiento() {
		Producto producto = productoConPrecio(100_000L);
		ContextoDescuento contexto = contexto(producto, DiaSemana.DOMINGO, CategoriaProducto.COMPRESOR_AIRE,
				TipoUsuario.CLIENTE);

		assertEquals(0, servicio.seleccionarMayorPorcentaje(contexto));
		assertEquals(0L, servicio.calcularDescuento(producto, contexto));
		assertEquals(100_000L, servicio.calcularPrecioFinal(producto, contexto));
	}

	@Test
	void nuncaSumaDescuentos() {
		Producto producto = productoConPrecio(100_000L);
		ContextoDescuento contexto = contexto(producto, DiaSemana.MIERCOLES, CategoriaProducto.TALADRO_PERCUTOR,
				TipoUsuario.EMPLEADO);

		assertEquals(10, servicio.seleccionarMayorPorcentaje(contexto));
		assertEquals(10_000L, servicio.calcularDescuento(producto, contexto));
	}

	private Producto productoConPrecio(Long precioLista) {
		Producto producto = mock(Producto.class);
		when(producto.getPrecioLista()).thenReturn(precioLista);
		return producto;
	}

	private ContextoDescuento contexto(
			Producto producto,
			DiaSemana dia,
			CategoriaProducto categoria,
			TipoUsuario tipoUsuario) {
		return new ContextoDescuento(producto, new UsuarioActual("usuario", tipoUsuario), dia, categoria);
	}
}
