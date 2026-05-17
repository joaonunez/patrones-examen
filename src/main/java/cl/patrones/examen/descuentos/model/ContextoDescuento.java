package cl.patrones.examen.descuentos.model;

import java.time.LocalDate;
import java.util.Objects;

import cl.patrones.examen.productos.domain.Producto;

public record ContextoDescuento(
		Producto producto,
		UsuarioActual usuario,
		DiaSemana dia,
		CategoriaProducto categoria) {

	public ContextoDescuento {
		Objects.requireNonNull(producto, "producto no puede ser null");
		usuario = Objects.requireNonNullElse(usuario, UsuarioActual.anonimo());
		Objects.requireNonNull(dia, "dia no puede ser null");
		Objects.requireNonNull(categoria, "categoria no puede ser null");
	}

	public static ContextoDescuento desde(Producto producto, UsuarioActual usuario, LocalDate fecha) {
		return new ContextoDescuento(
				producto,
				usuario,
				DiaSemana.desde(fecha),
				CategoriaProducto.desde(producto.getCategoria()));
	}
}
