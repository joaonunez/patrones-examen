package cl.patrones.examen.productos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.patrones.examen.descuentos.model.CategoriaProducto;
import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.model.CategoriaVista;
import cl.patrones.examen.productos.service.ProductoService;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {
	
	private ProductoService productoService;
	
	public AppController(ProductoService productoService) {
		super();
		this.productoService = productoService;
	}

	@GetMapping("/")
	String inicio(Model model) {
		var productos = productoService.getProductos();
		model.addAttribute("productos", productos);
		return "inicio";
	}

	@GetMapping("/categoria/{slug}")
	String categoria(@PathVariable String slug, Model model) {
		var categoria = CategoriaProducto.desdeNombre(slug)
				.orElse(CategoriaProducto.OTRA);
		List<? extends Producto> productos = productoService.getProductos().stream()
				.filter(producto -> CategoriaProducto.desde(producto.getCategoria()) == categoria)
				.toList();
		model.addAttribute("categoria", new CategoriaVista(nombreCategoria(categoria), slug));
		model.addAttribute("productos", productos);
		return "categoria";
	}

	private String nombreCategoria(CategoriaProducto categoria) {
		return switch (categoria) {
		case COMPRESOR_AIRE -> "Compresores de aire";
		case ESMERIL_ANGULAR -> "Esmeriles angulares";
		case TALADRO_PERCUTOR -> "Taladros percutores";
		case OTRA -> "Categoria";
		};
	}

}
