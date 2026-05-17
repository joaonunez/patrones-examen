package cl.patrones.examen.descuentos.strategy;

import cl.patrones.examen.descuentos.model.ContextoDescuento;

public interface DescuentoStrategy {

	boolean aplica(ContextoDescuento contexto);

	int calcularPorcentaje(ContextoDescuento contexto);
}
