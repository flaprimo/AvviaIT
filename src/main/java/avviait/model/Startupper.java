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
	private List<Startup> skillApprese;

	@OneToMany(mappedBy = "espressoDa")
	private List<VotoSkill> votiSkillDati;

	@OneToMany(mappedBy = "ricevutoDa")
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

	@Override
	public String toString() {
		String startupper = "Startupper [\n" +
				"id: " + id + "\n" +
				"nome: " + nome + "\n" +
				"cognome: " + cognome + "\n" +
				"email: " + email + "\n" +
				"password: " + password + "\n" +
				"saltPassword: " + saltPassword + "\n" +
				"dataIscrizione: " + dataIscrizione.getTime() + "\n" +
				"descrizione: " + descrizione + "\n";

		startupper += "giudiziDati: " + "\n";
		if (giudiziDati != null) {
			for (Giudizio giudizioDato : giudiziDati) {
				startupper += "  -  " + giudizioDato.getId() + " " + giudizioDato.getTitolo() + "\n";
			}
		} else {
			startupper += "  - lista non inizializzata\n";
		}

		startupper += "giudiziRicevuti: " + "\n";
		if (giudiziRicevuti != null) {
			for (Giudizio giudizioDato : giudiziRicevuti) {
				startupper += "  -  " + giudizioDato.getId() + " " + giudizioDato.getTitolo() + "\n";
			}
		} else {
			startupper += "  - lista non inizializzata\n";
		}

		startupper += "]\n";

		return startupper;
	}

}