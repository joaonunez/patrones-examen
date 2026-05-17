package cl.patrones.examen.carrito.model;

import java.util.Objects;

public class ItemCarrito {

	private final AvisoCarrito aviso;
	private int cantidad;

	public ItemCarrito(AvisoCarrito aviso, int cantidad) {
		this.aviso = Objects.requireNonNull(aviso, "aviso no puede ser null");
		setCantidad(cantidad);
	}

	public AvisoCarrito getAviso() {
		return aviso;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = Math.max(1, Math.min(cantidad, aviso.getStock()));
	}

	public Long subtotal() {
		return aviso.getPrecioFinal() * cantidad;
	}
}
