package cl.patrones.examen.descuentos.strategy;

import java.util.Objects;

import org.springframework.stereotype.Component;

import cl.patrones.examen.descuentos.model.ContextoDescuento;

@Component
public class DescuentoEmpleado implements DescuentoStrategy {

	private static final int SIN_DESCUENTO = 0;
	private static final int DESCUENTO_EMPLEADO = 5;

	@Override
	public boolean aplica(ContextoDescuento contexto) {
		Objects.requireNonNull(contexto, "contexto no puede ser null");
		return contexto.usuario().esEmpleado();
	}

	@Override
	public int calcularPorcentaje(ContextoDescuento contexto) {
		return aplica(contexto) ? DESCUENTO_EMPLEADO : SIN_DESCUENTO;
	}
}
