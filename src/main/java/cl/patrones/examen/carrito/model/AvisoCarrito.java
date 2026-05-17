package cl.patrones.examen.carrito.model;

import java.util.Objects;

import cl.patrones.examen.productos.domain.Producto;

public class AvisoCarrito {

	private static final int STOCK_DEMO = 99;
	private final Producto producto;

	public AvisoCarrito(Producto producto) {
		this.producto = Objects.requireNonNull(producto, "producto no puede ser null");
	}

	public String getSku() {
		return producto.getSku();
	}

	public String getTitulo() {
		return producto.getNombre();
	}

	public String getImagen() {
		return producto.getImagen();
	}

	public Long getPrecioFinal() {
		return producto.getPrecioFinal();
	}

	public int getStock() {
		return STOCK_DEMO;
	}

	public Producto getProducto() {
		return producto;
	}
}
