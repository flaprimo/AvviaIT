package avviait.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
@NamedQueries({
		@NamedQuery(name="findAllStartupper", query="SELECT s FROM Startupper s"),
		@NamedQuery(name="findStartupperByEmail", query="SELECT s FROM Startupper s WHERE s.email = :email"),
		@NamedQuery(name = "findAllStartupAttuali", query = "SELECT s FROM Startup s " +
				"JOIN s.membri m WHERE m = :startupper"),
		@NamedQuery(name = "findAllStartupPassate", query = "SELECT s FROM Startup s " +
				"JOIN s.membriPassati mp WHERE mp = :startupper"),
		@NamedQuery(name = "findAllStartupAmministrate", query = "SELECT s FROM Startup s " +
				"JOIN s.amministratori a WHERE a = :startupper")
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
	@JoinTable(name = "amministratori_startupamministrate",
			uniqueConstraints = {@UniqueConstraint(columnNames = {"amministratori_id", "startupamministrate_id"})})
    private List<Startup> startupAmministrate;

    @ManyToMany
	@JoinTable(name = "membri_startupattuali",
			uniqueConstraints = {@UniqueConstraint(columnNames = {"membri_id", "startupattuali_id"})})
    private List<Startup> startupAttuali;

    @ManyToMany
	@JoinTable(name = "membripassati_startuppassate",
			uniqueConstraints = {@UniqueConstraint(columnNames = {"membripassati_id", "startuppassate_id"})})
    private List<Startup> startupPassate;

	// Skill e voti skill
	@OneToMany(mappedBy = "startupperProprietario")
	private List<StartupperSkill> skillPossedute;

	@ManyToMany(mappedBy = "startupperVotanti")
	private List<StartupperSkill> skillVotate;

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
		this.startupPassate = new LinkedList<Startup>();
		this.giudiziDati = new LinkedList<Giudizio>();
		this.giudiziRicevuti = new LinkedList<Giudizio>();
        this.skillPossedute = new LinkedList<StartupperSkill>();
        this.skillVotate = new LinkedList<StartupperSkill>();
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

	public List<StartupperSkill> getSkillPossedute() {
		return skillPossedute;
	}

    public List<StartupperSkill> getSkillVotate() {
        return skillVotate;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Startupper that = (Startupper) o;

		return !(email != null ? !email.equals(that.email) : that.email != null);

	}

	@Override
	public int hashCode() {
		return email != null ? email.hashCode() : 0;
	}
}