package fr.uga.im2ag.l3.miage.db.model;

import java.util.List;

import javax.persistence.*;
// TODO ajouter une named query pour une des requêtes à faire dans le repository
@Entity
@Table(name="teacher")
@DiscriminatorValue(value="teacher")
@NamedQueries({
    @NamedQuery(name="Teacher.findAll", query="select teacher from Teacher teacher"),
    @NamedQuery(name="Teacher.findClassByYearAndName", query="select t from Teacher t join t.heading gc where gc.year = :year and gc.name = :name")
})
public class Teacher extends Person {

    @ManyToOne
    private Subject teaching;
    @OneToMany
    private List<Student> favorites;
    @OneToOne
    private GraduationClass heading;

    public Subject getTeaching() {
        return teaching;
    }

    public Teacher setTeaching(Subject teaching) {
        this.teaching = teaching;
        return this;
    }

    public List<Student> getFavorites() {
        return favorites;
    }

    public Teacher setFavorites(List<Student> favorites) {
        this.favorites = favorites;
        return this;
    }

    public GraduationClass getHeading() {
        return heading;
    }

    public Teacher setHeading(GraduationClass heading) {
        this.heading = heading;
        return this;
    }

}
