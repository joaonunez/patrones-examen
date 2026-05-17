package cl.patrones.examen.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import cl.patrones.examen.descuentos.model.TipoUsuario;
import cl.patrones.examen.descuentos.model.UsuarioActual;

@Component
public class UsuarioActualResolver {

	private static final String ROLE_EMPLEADO = "ROLE_EMPLEADO";

	public UsuarioActual getUsuarioActual() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return UsuarioActual.anonimo();
		}
		return new UsuarioActual(authentication.getName(), resolverTipo(authentication));
	}

	private TipoUsuario resolverTipo(Authentication authentication) {
		boolean esEmpleado = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(ROLE_EMPLEADO::equals);
		return esEmpleado ? TipoUsuario.EMPLEADO : TipoUsuario.CLIENTE;
	}
}
