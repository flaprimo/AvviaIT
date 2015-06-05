package avviait.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name="findAllStartupper", query="SELECT s FROM Startupper s"),
		@NamedQuery(name="findStartupperByEmail", query="SELECT s FROM Startupper s WHERE s.email = :email")
})
public class Startupper {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String cognome;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String saltPassword;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataIscrizione;

	@Column
	private String descrizione;

	@Column(nullable = false)
	private Boolean attivo;

	@OneToMany(mappedBy = "autore")
	private List<Giudizio> giudiziDati;

	@OneToMany(mappedBy = "giudicato")
	private List<Giudizio> giudiziRicevuti;

    @ManyToMany
    private List<Startup> startupAmministrate;

    @ManyToMany
    private List<Startup> startupAttuali;

    @ManyToMany
    private List<Startup> startupPassate;

	// Skill e voti skill
	@ManyToMany
    @JoinTable(
            name = "Startupper_Skill",
            joinColumns = {@JoinColumn(name="Startupper_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="Skill_id", referencedColumnName="id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"Startupper_id", "Skill_id"})}
    )
	private List<Skill> skillApprese;

	@OneToMany(mappedBy = "autore")
	private List<VotoSkill> votiSkillDati;

	@OneToMany(mappedBy = "giudicato")
	private List<VotoSkill> votiSkillRicevuti;

	public Startupper() {
	}

	public Startupper(String nome, String cognome, String email, String password, String saltPassword,
					  Calendar dataIscrizione) {
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.attivo = true;
		this.password = password;
		this.saltPassword = saltPassword;
		this.dataIscrizione = dataIscrizione;
		this.startupAmministrate = new LinkedList<Startup>();
        this.startupAttuali = new LinkedList<Startup>();
		this.giudiziDati = new LinkedList<Giudizio>();
		this.giudiziRicevuti = new LinkedList<Giudizio>();
        this.skillApprese = new LinkedList<Skill>();
        this.votiSkillDati = new LinkedList<VotoSkill>();
        this.votiSkillRicevuti = new LinkedList<VotoSkill>();
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSaltPassword() {
		return saltPassword;
	}

	public void setSaltPassword(String saltPassword) {
		this.saltPassword = saltPassword;
	}

	public Calendar getDataIscrizione() {
		return dataIscrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

	public List<Giudizio> getGiudiziDati() {
		return giudiziDati;
	}

	public List<Giudizio> getGiudiziRicevuti() {
		return giudiziRicevuti;
	}

    public List<Startup> getStartupAmministrate() {
        return startupAmministrate;
    }

    public List<Startup> getStartupAttuali() {
        return startupAttuali;
    }

    public List<Startup> getStartupPassate() {
        return startupPassate;
    }

    public List<Skill> getSkillApprese() {
        return skillApprese;
    }

    public List<VotoSkill> getVotiSkillDati() {
        return votiSkillDati;
    }

    public List<VotoSkill> getVotiSkillRicevuti() {
        return votiSkillRicevuti;
    }

    @Override
	public String toString() {
		return "Startupper{" +
				"id=" + id +
				", nome='" + nome + '\'' +
				", cognome='" + cognome + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", saltPassword='" + saltPassword + '\'' +
				", dataIscrizione=" + dataIscrizione.getTime() +
				", descrizione='" + descrizione + '\'' +
				", attivo=" + attivo +
				", giudiziDati=" + giudiziDati +
				", giudiziRicevuti=" + giudiziRicevuti +
				", startupAmministrate=" + startupAmministrate +
				", startupAttuali=" + startupAttuali +
				", startupPassate=" + startupPassate +
				'}';
	}

}