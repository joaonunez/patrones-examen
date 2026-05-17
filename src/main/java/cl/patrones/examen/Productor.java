package cl.patrones.examen;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.patrones.examen.descuentos.service.ServicioDescuento;
import cl.patrones.examen.productos.service.ProductoServiceImpl;
import cl.patrones.examen.productos.service.ProductoService;
import cl.patrones.examen.productos.service.ProductoServiceConDescuentos;
import cl.patrones.examen.security.UsuarioActualResolver;

@Configuration
public class Productor {

	@Bean
	Clock clock() {
		return Clock.systemDefaultZone();
	}

	@Bean
	ProductoService productoService(
			ServicioDescuento servicioDescuento,
			UsuarioActualResolver usuarioActualResolver,
			Clock clock) {
		return new ProductoServiceConDescuentos(
				new ProductoServiceImpl(),
				servicioDescuento,
				usuarioActualResolver::getUsuarioActual,
				clock);
	}
}
