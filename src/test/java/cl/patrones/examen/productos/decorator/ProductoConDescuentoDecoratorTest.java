package cl.patrones.examen.productos.decorator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.descuentos.model.CategoriaProducto;
import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.model.DiaSemana;
import cl.patrones.examen.descuentos.model.TipoUsuario;
import cl.patrones.examen.descuentos.model.UsuarioActual;
import cl.patrones.examen.descuentos.service.ServicioDescuento;
import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;

class ProductoConDescuentoDecoratorTest {

	@Test
	void conservaDatosOriginalesDelProducto() {
		Producto producto = productoBase();
		ContextoDescuento contexto = contexto(producto);
		ServicioDescuento servicioDescuento = mock(ServicioDescuento.class);

		Producto decorado = new ProductoConDescuentoDecorator(producto, contexto, servicioDescuento);

		assertEquals("SKU-1", decorado.getSku());
		assertEquals("Compresor", decorado.getNombre());
		assertEquals("compresor.jpg", decorado.getImagen());
		assertEquals(70_000L, decorado.getCosto());
		assertEquals(100_000L, decorado.getPrecioLista());
		assertEquals(producto.getCategoria(), decorado.getCategoria());
	}

	@Test
	void descuentoYPrecioFinalSeCalculanConServicio() {
		Producto producto = productoBase();
		ContextoDescuento contexto = contexto(producto);
		ServicioDescuento servicioDescuento = mock(ServicioDescuento.class);
		when(servicioDescuento.calcularDescuento(producto, contexto)).thenReturn(6_000L);
		when(servicioDescuento.calcularPrecioFinal(producto, contexto)).thenReturn(94_000L);

		Producto decorado = new ProductoConDescuentoDecorator(producto, contexto, servicioDescuento);

		assertEquals(6_000L, decorado.getDescuento());
		assertEquals(94_000L, decorado.getPrecioFinal());
	}

	private Producto productoBase() {
		Categoria categoria = mock(Categoria.class);
		Producto producto = mock(Producto.class);
		when(producto.getSku()).thenReturn("SKU-1");
		when(producto.getNombre()).thenReturn("Compresor");
		when(producto.getImagen()).thenReturn("compresor.jpg");
		when(producto.getCosto()).thenReturn(70_000L);
		when(producto.getPrecioLista()).thenReturn(100_000L);
		when(producto.getCategoria()).thenReturn(categoria);
		return producto;
	}

	private ContextoDescuento contexto(Producto producto) {
		return new ContextoDescuento(
				producto,
				new UsuarioActual("andrea", TipoUsuario.EMPLEADO),
				DiaSemana.LUNES,
				CategoriaProducto.COMPRESOR_AIRE);
	}
}
