package cl.patrones.examen.productos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.model.UsuarioActual;
import cl.patrones.examen.descuentos.service.ServicioDescuento;
import cl.patrones.examen.productos.decorator.ProductoConDescuentoDecorator;
import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;

class ProductoServiceConDescuentosTest {

	@Test
	void retornaProductosDecorados() {
		Producto producto = productoBase("Compresores de aire");
		ProductoService delegate = mock(ProductoService.class);
		ServicioDescuento servicioDescuento = mock(ServicioDescuento.class);
		Clock clock = Clock.fixed(Instant.parse("2026-05-18T10:00:00Z"), ZoneId.of("UTC"));
		when(delegate.getProductos()).thenAnswer(invocation -> List.of(producto));
		when(servicioDescuento.calcularDescuento(eq(producto), any(ContextoDescuento.class))).thenReturn(6_000L);
		when(servicioDescuento.calcularPrecioFinal(eq(producto), any(ContextoDescuento.class))).thenReturn(94_000L);

		ProductoServiceConDescuentos service = new ProductoServiceConDescuentos(
				delegate,
				servicioDescuento,
				() -> UsuarioActual.empleado("andrea"),
				clock);

		List<Producto> productos = service.getProductos();

		assertEquals(1, productos.size());
		assertTrue(productos.get(0) instanceof ProductoConDescuentoDecorator);
		assertNotSame(producto, productos.get(0));
		assertEquals(6_000L, productos.get(0).getDescuento());
		assertEquals(94_000L, productos.get(0).getPrecioFinal());
	}

	private Producto productoBase(String nombreCategoria) {
		Categoria categoria = mock(Categoria.class);
		when(categoria.getNombre()).thenReturn(nombreCategoria);
		Producto producto = mock(Producto.class);
		when(producto.getPrecioLista()).thenReturn(100_000L);
		when(producto.getCategoria()).thenReturn(categoria);
		return producto;
	}
}
