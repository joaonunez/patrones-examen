package cl.patrones.examen.carrito.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cl.patrones.examen.productos.domain.Producto;

public class Carrito {

	private final List<ItemCarrito> items = new ArrayList<>();

	public List<ItemCarrito> getItems() {
		return Collections.unmodifiableList(items);
	}

	public void agregar(Producto producto, int cantidad) {
		Optional<ItemCarrito> existente = buscar(producto.getSku());
		if (existente.isPresent()) {
			existente.get().setCantidad(cantidad);
			return;
		}
		items.add(new ItemCarrito(new AvisoCarrito(producto), cantidad));
	}

	public void eliminar(String sku) {
		items.removeIf(item -> item.getAviso().getSku().equals(sku));
	}

	public Long total() {
		return items.stream()
				.mapToLong(ItemCarrito::subtotal)
				.sum();
	}

	public int totalItems() {
		return items.stream()
				.mapToInt(ItemCarrito::getCantidad)
				.sum();
	}

	private Optional<ItemCarrito> buscar(String sku) {
		return items.stream()
				.filter(item -> item.getAviso().getSku().equals(sku))
				.findFirst();
	}
}
