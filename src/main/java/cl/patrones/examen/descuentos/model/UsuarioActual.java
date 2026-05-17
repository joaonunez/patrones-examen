package cl.patrones.examen.descuentos.model;

import java.util.Objects;

public record UsuarioActual(String username, TipoUsuario tipo) {

	public UsuarioActual {
		username = username == null || username.isBlank() ? "anonimo" : username;
		tipo = Objects.requireNonNullElse(tipo, TipoUsuario.CLIENTE);
	}

	public static UsuarioActual cliente(String username) {
		return new UsuarioActual(username, TipoUsuario.CLIENTE);
	}

	public static UsuarioActual empleado(String username) {
		return new UsuarioActual(username, TipoUsuario.EMPLEADO);
	}

	public static UsuarioActual anonimo() {
		return cliente("anonimo");
	}

	public boolean esEmpleado() {
		return tipo == TipoUsuario.EMPLEADO;
	}
}
