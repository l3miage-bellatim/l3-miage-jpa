package fr.uga.im2ag.l3.miage.db.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;


@Entity 
@Table(name="student")
@DiscriminatorValue(value="student")
@NamedQueries({
    @NamedQuery(name="Student.findAll", query="select student from Student student"),
    @NamedQuery(name="Student.findAboveAverage", query="select s from Student s join s.grades g group by s.id having sum(g.value*g.weight)/sum(g.weight) >= :minAvg ")
})
public class Student extends Person {

    @ManyToOne
    private GraduationClass belongTo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Grade> grades;

    public GraduationClass getBelongTo() {
        return belongTo;
    }

    public Student setBelongTo(GraduationClass belongTo) {
        this.belongTo = belongTo;
        return this;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Student setGrades(List<Grade> grades) {
        this.grades = grades;
        return this;
    }
}
