package br.com.limieri.calidens.prototype01.repository;

import br.com.limieri.calidens.prototype01.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {


    List<Usuario> findUsuarioByEmailAndPassword(String email, String password);

    List<Usuario> findAllByEmailContaining(String email);

    List<Usuario> findAllByEmail(String email);

}
