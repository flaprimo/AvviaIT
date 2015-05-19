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

    @OneToMany(mappedBy = "skillAssociata")
    private List<VotoSkill> associataAVotoSkill;

}
