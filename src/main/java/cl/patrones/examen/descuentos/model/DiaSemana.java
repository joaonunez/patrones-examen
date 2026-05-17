package cl.patrones.examen.descuentos.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public enum DiaSemana {
	LUNES,
	MARTES,
	MIERCOLES,
	JUEVES,
	VIERNES,
	SABADO,
	DOMINGO;

	public static DiaSemana desde(LocalDate fecha) {
		Objects.requireNonNull(fecha, "fecha no puede ser null");
		return desde(fecha.getDayOfWeek());
	}

	public static DiaSemana desde(DayOfWeek dayOfWeek) {
		Objects.requireNonNull(dayOfWeek, "dayOfWeek no puede ser null");
		return switch (dayOfWeek) {
		case MONDAY -> LUNES;
		case TUESDAY -> MARTES;
		case WEDNESDAY -> MIERCOLES;
		case THURSDAY -> JUEVES;
		case FRIDAY -> VIERNES;
		case SATURDAY -> SABADO;
		case SUNDAY -> DOMINGO;
		};
	}
}
