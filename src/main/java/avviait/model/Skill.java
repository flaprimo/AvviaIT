package avviait.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "skillApprese")
    private List<Startupper> appresaDa;

    @ManyToMany(mappedBy = "skillRichieste")
    private List<AnnuncioMembri> richiestaDaAnnuncioMembri;

    public Skill() {
    }

    public Skill(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Startupper> getAppresaDa() {
        return appresaDa;
    }

    public List<AnnuncioMembri> getRichiestaDaAnnuncioMembri() {
        return richiestaDaAnnuncioMembri;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
