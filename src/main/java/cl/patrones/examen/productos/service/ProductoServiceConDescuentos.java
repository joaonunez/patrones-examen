package cl.patrones.examen.productos.service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.model.UsuarioActual;
import cl.patrones.examen.descuentos.service.ServicioDescuento;
import cl.patrones.examen.productos.decorator.ProductoConDescuentoDecorator;
import cl.patrones.examen.productos.domain.Producto;

public class ProductoServiceConDescuentos implements ProductoService {

	private final ProductoService delegate;
	private final ServicioDescuento servicioDescuento;
	private final Supplier<UsuarioActual> usuarioActualSupplier;
	private final Clock clock;

	public ProductoServiceConDescuentos(
			ProductoService delegate,
			ServicioDescuento servicioDescuento,
			Supplier<UsuarioActual> usuarioActualSupplier,
			Clock clock) {
		this.delegate = Objects.requireNonNull(delegate, "delegate no puede ser null");
		this.servicioDescuento = Objects.requireNonNull(servicioDescuento, "servicioDescuento no puede ser null");
		this.usuarioActualSupplier = Objects.requireNonNull(usuarioActualSupplier, "usuarioActualSupplier no puede ser null");
		this.clock = Objects.requireNonNull(clock, "clock no puede ser null");
	}

	@Override
	public List<Producto> getProductos() {
		UsuarioActual usuarioActual = Objects.requireNonNullElse(usuarioActualSupplier.get(), UsuarioActual.anonimo());
		LocalDate fecha = LocalDate.now(clock);
		return delegate.getProductos().stream()
				.map(producto -> decorar(producto, usuarioActual, fecha))
				.toList();
	}

	private Producto decorar(Producto producto, UsuarioActual usuarioActual, LocalDate fecha) {
		ContextoDescuento contexto = ContextoDescuento.desde(producto, usuarioActual, fecha);
		return new ProductoConDescuentoDecorator(producto, contexto, servicioDescuento);
	}
}
