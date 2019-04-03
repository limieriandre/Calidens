package br.com.limieri.calidens.prototype01.repository;

import br.com.limieri.calidens.prototype01.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepo extends JpaRepository<Consulta, Long> {
    List<Consulta> findAllByOrderByDataDesc();

    List<Consulta> findAllByPacienteNomeContaining(String nome);

    List<Consulta> findAllByCarieTrue();

    List<Consulta> findAllByCarieFalse();

    List<Consulta> findAllByRestauracaoTrue();

    List<Consulta> findAllByRestauracaoFalse();

    List<Consulta> findAllByCarieTrueAndPacienteEtniaContaining(String etnia);

    List<Consulta> findAllByRestauracaoTrueAndPacienteEtniaContaining(String etnia);

    List<Consulta> findAllByPacienteEtniaContaining(String etnia);

//    @Query("SELECT u FROM Consulta JOIN Paciente on escola.id = paciente.escola_id Where escola_id = :idescola")
//    List<Consulta> findAllByEscola_id(@Param("idescola") Long idescola);


}
