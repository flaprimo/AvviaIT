package avviait.model;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name="findAllSkill", query="SELECT s FROM Skill s"),
        @NamedQuery(name="findSkillOfStartupper", query=
                "SELECT s " +
                "FROM Skill s, Startupper startupper " +
                "WHERE startupper.id = :startupper " +
                "AND s.startupperSkillAssociate MEMBER OF startupper.skillPossedute"),
        @NamedQuery(name="findSkillOfAnnuncio", query="SELECT s FROM Skill s " +
                "WHERE s.richiestaDaAnnuncioMembri.id = :id"),
        @NamedQuery(name="getNamedSkill", query = "SELECT s FROM Skill s WHERE LOWER(s.nome) = LOWER(:nome)"),
        @NamedQuery(name = "findSkillNotAcquired", query =
            "SELECT s " +
            "FROM Skill s " +
            "WHERE (SELECT count(startupperSkill) " +
                    "FROM StartupperSkill startupperSkill " +
                    "WHERE startupperSkill.skillAssociata = s " +
                    "AND startupperSkill.startupperProprietario = :startupper) = 0 " +
            "ORDER BY s.nome"),

})
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @OneToMany(mappedBy = "skillAssociata")
    private List<StartupperSkill> startupperSkillAssociate;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (id != null ? !id.equals(skill.id) : skill.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
