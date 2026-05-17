package cl.patrones.examen.descuentos.strategy;

import java.util.Objects;

import org.springframework.stereotype.Component;

import cl.patrones.examen.descuentos.model.CategoriaProducto;
import cl.patrones.examen.descuentos.model.ContextoDescuento;
import cl.patrones.examen.descuentos.model.DiaSemana;

@Component
public class DescuentoPorDiaYCategoria implements DescuentoStrategy {

	private static final int SIN_DESCUENTO = 0;
	private static final int DESCUENTO_COMPRESORES_LUNES = 6;
	private static final int DESCUENTO_ESMERILES_MARTES = 8;
	private static final int DESCUENTO_TALADROS_MIERCOLES = 10;

	@Override
	public boolean aplica(ContextoDescuento contexto) {
		return calcularPorcentaje(contexto) > SIN_DESCUENTO;
	}

	@Override
	public int calcularPorcentaje(ContextoDescuento contexto) {
		Objects.requireNonNull(contexto, "contexto no puede ser null");
		if (contexto.dia() == DiaSemana.LUNES && contexto.categoria() == CategoriaProducto.COMPRESOR_AIRE) {
			return DESCUENTO_COMPRESORES_LUNES;
		}
		if (contexto.dia() == DiaSemana.MARTES && contexto.categoria() == CategoriaProducto.ESMERIL_ANGULAR) {
			return DESCUENTO_ESMERILES_MARTES;
		}
		if (contexto.dia() == DiaSemana.MIERCOLES && contexto.categoria() == CategoriaProducto.TALADRO_PERCUTOR) {
			return DESCUENTO_TALADROS_MIERCOLES;
		}
		return SIN_DESCUENTO;
	}
}
