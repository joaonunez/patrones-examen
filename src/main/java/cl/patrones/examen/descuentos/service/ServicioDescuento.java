package cl.patrones.examen.descuentos.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.strategy.DescuentoStrategy;
import cl.patrones.examen.productos.domain.Producto;

@Service
public class ServicioDescuento {

	private static final int SIN_DESCUENTO = 0;
	private final List<DescuentoStrategy> estrategias;

	public ServicioDescuento(List<DescuentoStrategy> estrategias) {
		this.estrategias = List.copyOf(Objects.requireNonNullElse(estrategias, List.of()));
	}

	public int seleccionarMayorPorcentaje(ContextoDescuento contexto) {
		Objects.requireNonNull(contexto, "contexto no puede ser null");
		return estrategias.stream()
				.filter(estrategia -> estrategia.aplica(contexto))
				.mapToInt(estrategia -> estrategia.calcularPorcentaje(contexto))
				.max()
				.orElse(SIN_DESCUENTO);
	}

	public Long calcularDescuento(Producto producto, ContextoDescuento contexto) {
		Objects.requireNonNull(producto, "producto no puede ser null");
		int porcentaje = seleccionarMayorPorcentaje(contexto);
		return calcularPorcentaje(producto.getPrecioLista(), porcentaje);
	}

	public Long calcularPrecioFinal(Producto producto, ContextoDescuento contexto) {
		Objects.requireNonNull(producto, "producto no puede ser null");
		Long precioLista = Objects.requireNonNullElse(producto.getPrecioLista(), 0L);
		return precioLista - calcularDescuento(producto, contexto);
	}

	private Long calcularPorcentaje(Long valor, int porcentaje) {
		Long monto = Objects.requireNonNullElse(valor, 0L);
		if (porcentaje <= SIN_DESCUENTO || monto <= 0L) {
			return 0L;
		}
		return monto * porcentaje / 100;
	}
}
