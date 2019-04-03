package br.com.limieri.calidens.prototype01.repository;

import br.com.limieri.calidens.prototype01.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepo extends JpaRepository<Paciente,Long> {

//    public List<StudentEntity> findAllByOrderByIdAsc();

     List<Paciente> findAllByOrderByIdAsc();
//
     List<Paciente> findAllByOrderByDtNascimentoAsc();
//
     List<Paciente> findAllByOrderByNomeAsc();

     List<Paciente> findAllByNomeContaining(String nome);

     List<Paciente> findAllByEscola_IdOrderByNomeAsc(Long id);

     List<Paciente> findAllByEscola_IdAndNomeContaining(Long id,String nome);

     @Query("SELECT u FROM Paciente u WHERE u.sexo = 'F'")
     List<Paciente> findAllBySexoF();

     @Query("SELECT u FROM Paciente u WHERE u.sexo = 'M'")
     List<Paciente> findAllBySexoM();

     List<Paciente> findAllByEtniaLike(String etnia);


}
