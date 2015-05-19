package avviait.model;


import javax.persistence.*;
import java.util.Calendar;

@Entity
public class VotoSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String titolo;

    @Column(nullable = false)
    private String testo;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dataCreazione;

    @ManyToOne(cascade={CascadeType.ALL})
    private Startupper espressoDa;

    @ManyToOne(cascade={CascadeType.ALL})
    private Startupper ricevutoDa;

    @ManyToOne(cascade={CascadeType.ALL})
    private Skill skillAssociata;
}
