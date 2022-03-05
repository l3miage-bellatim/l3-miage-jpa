package fr.uga.im2ag.l3.miage.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({@NamedQuery(name="Grade.findAll", query="select grade from Grade grade"),
               @NamedQuery(name="Grade.findHighestGrades", query="select grade from Grade grade where grade.value >= :limit"),
               @NamedQuery(name="Grade.findHighestGradesBySubject", query="select grade from Grade grade join grade.subject s where grade.value >= :limit and s.id = :id")})

public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne 
    private Subject subject;
    @Column(name = "grade", updatable = false)
    private Float value; 
    // @Column
    // @Column(precision=1, scale=1) //scale nombre de chiffre apres la virgule, prec chiffres signifocatif
    private Float weight;//precision ou scale de la note et le poid

    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public Grade setSubject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public Float getValue() {
        return value;
    }

    public Grade setValue(Float value) {
        this.value = value;
        return this;
    }

    public Float getWeight() {
        return weight;
    }

    public Grade setWeight(Float weight) {
        this.weight = weight;
        return this;
    }
}
