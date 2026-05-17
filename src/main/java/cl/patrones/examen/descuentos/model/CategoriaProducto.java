package cl.patrones.examen.descuentos.model;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import cl.patrones.examen.productos.domain.Categoria;

public enum CategoriaProducto {
	COMPRESOR_AIRE("compresores-de-aire"),
	ESMERIL_ANGULAR("esmeriles-angulares"),
	TALADRO_PERCUTOR("taladros-percutores"),
	OTRA("otra");

	private final String slug;

	CategoriaProducto(String slug) {
		this.slug = slug;
	}

	public String getSlug() {
		return slug;
	}

	public static CategoriaProducto desde(Categoria categoria) {
		if (categoria == null) {
			return OTRA;
		}
		return desdeNombre(categoria.getNombre()).orElse(OTRA);
	}

	public static Optional<CategoriaProducto> desdeNombre(String nombre) {
		if (nombre == null || nombre.isBlank()) {
			return Optional.empty();
		}
		String slug = normalizar(nombre);
		if (slug.contains("compresor")) {
			return Optional.of(COMPRESOR_AIRE);
		}
		if (slug.contains("esmeril")) {
			return Optional.of(ESMERIL_ANGULAR);
		}
		if (slug.contains("taladro")) {
			return Optional.of(TALADRO_PERCUTOR);
		}
		return Optional.empty();
	}

	private static String normalizar(String texto) {
		String normalizado = Normalizer.normalize(Objects.requireNonNull(texto), Normalizer.Form.NFD);
		return normalizado.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
				.toLowerCase(Locale.ROOT)
				.replaceAll("[^a-z0-9]+", "-")
				.replaceAll("^-|-$", "");
	}
}
