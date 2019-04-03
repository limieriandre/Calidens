package br.com.limieri.calidens.prototype01.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "escola")
public class Escola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @NotNull
    private String nome;

    @Column(name = "endereco")
    @NotNull
    private String endereco;

//    @Column(name = "cidade")
//    @NotNull
//    private String cidade;
//
//    @Column(name = "regiao")
//    @NotNull
//    private String regiao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
