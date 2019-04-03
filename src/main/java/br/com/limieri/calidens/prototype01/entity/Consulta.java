package br.com.limieri.calidens.prototype01.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date data;

    @Column(name = "carie")
    private Boolean carie;


    @Column(name = "restauracao")
    private Boolean restauracao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Paciente paciente;



    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Boolean getCarie() {
        return carie;
    }

    public void setCarie(Boolean carie) {
        this.carie = carie;
    }

    public Boolean getRestauracao() {
        return restauracao;
    }

    public void setRestauracao(Boolean restauracao) {
        this.restauracao = restauracao;
    }


}
