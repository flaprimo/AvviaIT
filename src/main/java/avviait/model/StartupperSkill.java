package avviait.model;


import javax.persistence.*;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames={"startupperproprietario_id", "skillassociata_id"})
)
@NamedQueries({
        @NamedQuery(name="findStartupperSkillSingleResult", query="SELECT v FROM StartupperSkill v " +
                "WHERE v.startupperProprietario = :startupper AND v.skillAssociata = :skill"),
})
public class StartupperSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCreazione;

    @ManyToOne
    private Startupper startupperProprietario;

    @ManyToOne
    private Skill skillAssociata;

    @ManyToMany
    @JoinTable(
            name = "Startupper_Skill",
            joinColumns={@JoinColumn(name="StartupperSkill_id", referencedColumnName="id")},
            inverseJoinColumns = {@JoinColumn(name="Startupper_id", referencedColumnName="id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"StartupperSkill_id", "Startupper_id"})}
    )
    private List<Startupper> startupperVotanti;

    public StartupperSkill() {
    }

    public StartupperSkill(Calendar dataCreazione, Startupper startupperProprietario,
                           Skill skillAssociata) {
        this.dataCreazione = dataCreazione;
        this.startupperProprietario = startupperProprietario;
        this.startupperVotanti = new LinkedList<Startupper>();
        this.skillAssociata = skillAssociata;
    }

    public Long getId() {
        return id;
    }

    public Calendar getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(Calendar dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Startupper getStartupperProprietario() {
        return startupperProprietario;
    }

    public void setStartupperProprietario(Startupper startupperProprietario) {
        this.startupperProprietario = startupperProprietario;
    }

    public List<Startupper> getStartupperVotanti() {
        return startupperVotanti;
    }

    public void setStartupperVotanti(List<Startupper> startupperVotanti) {
        this.startupperVotanti = startupperVotanti;
    }

    public Skill getSkillAssociata() {
        return skillAssociata;
    }

    public void setSkillAssociata(Skill skillAssociata) {
        this.skillAssociata = skillAssociata;
    }

    @Override
    public String toString() {
        return "StartupperSkill{" +
                "id=" + id +
                ", dataCreazione=" + dataCreazione +
                ", startupperProprietario=" + startupperProprietario +
                ", startupperVotanti=" + startupperVotanti +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartupperSkill that = (StartupperSkill) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
