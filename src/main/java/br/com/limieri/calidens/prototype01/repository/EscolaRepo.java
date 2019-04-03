package br.com.limieri.calidens.prototype01.repository;

import br.com.limieri.calidens.prototype01.entity.Escola;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscolaRepo extends JpaRepository<Escola,Long> {
    List<Escola> findAllByNomeContaining(String nome);

    List<Escola> findAllByOrderByNomeAsc();
}
