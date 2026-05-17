package cl.patrones.examen.productos.decorator;

import java.util.Objects;

import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.service.ServicioDescuento;
import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;

public class ProductoConDescuentoDecorator implements Producto {

	private final Producto producto;
	private final ContextoDescuento contexto;
	private final ServicioDescuento servicioDescuento;

	public ProductoConDescuentoDecorator(
			Producto producto,
			ContextoDescuento contexto,
			ServicioDescuento servicioDescuento) {
		this.producto = Objects.requireNonNull(producto, "producto no puede ser null");
		this.contexto = Objects.requireNonNull(contexto, "contexto no puede ser null");
		this.servicioDescuento = Objects.requireNonNull(servicioDescuento, "servicioDescuento no puede ser null");
	}

	@Override
	public String getSku() {
		return producto.getSku();
	}

	@Override
	public String getNombre() {
		return producto.getNombre();
	}

	@Override
	public String getImagen() {
		return producto.getImagen();
	}

	@Override
	public Long getCosto() {
		return producto.getCosto();
	}

	@Override
	public Long getPrecioLista() {
		return producto.getPrecioLista();
	}

	@Override
	public Long getDescuento() {
		return servicioDescuento.calcularDescuento(producto, contexto);
	}

	@Override
	public Long getPrecioFinal() {
		return servicioDescuento.calcularPrecioFinal(producto, contexto);
	}

	@Override
	public Categoria getCategoria() {
		return producto.getCategoria();
	}
}
