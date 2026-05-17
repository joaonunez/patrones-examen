package cl.patrones.examen.carrito.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.patrones.examen.carrito.model.Carrito;
import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.service.ProductoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarritoController {

	private static final String CARRITO_SESSION_KEY = "carrito";
	private final ProductoService productoService;

	public CarritoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	@GetMapping("/carrito")
	String verCarrito(Model model, HttpSession session) {
		model.addAttribute("carrito", obtenerCarrito(session));
		return "carrito";
	}

	@GetMapping("/carrito/add")
	String agregar(
			@RequestParam String sku,
			@RequestParam(defaultValue = "1") int cant,
			HttpSession session,
			RedirectAttributes redirectAttributes) {
		Optional<Producto> producto = buscarProducto(sku);
		if (producto.isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
			return "redirect:/";
		}
		obtenerCarrito(session).agregar(producto.get(), cant);
		return "redirect:/carrito";
	}

	@GetMapping("/carrito/remove")
	String eliminar(@RequestParam String sku, HttpSession session) {
		obtenerCarrito(session).eliminar(sku);
		return "redirect:/carrito";
	}

	private Optional<Producto> buscarProducto(String sku) {
		return productoService.getProductos().stream()
				.filter(producto -> producto.getSku().equals(sku))
				.findFirst()
				.map(Producto.class::cast);
	}

	private Carrito obtenerCarrito(HttpSession session) {
		Object value = session.getAttribute(CARRITO_SESSION_KEY);
		if (value instanceof Carrito carrito) {
			return carrito;
		}
		Carrito carrito = new Carrito();
		session.setAttribute(CARRITO_SESSION_KEY, carrito);
		return carrito;
	}
}
