package cl.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.ms.model.UsuarioModel;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, String>{

	public UsuarioModel findByEmail(String email);
	
	public UsuarioModel findByEmailAndPassword(String email, String password);
}
