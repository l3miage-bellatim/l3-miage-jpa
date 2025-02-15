package fr.uga.im2ag.l3.miage.db.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "graduationclass")
@NamedQueries({
    @NamedQuery(name="GraduationClass.findAll", query="select gc from GraduationClass gc"),
    @NamedQuery(name="GraduationClass.findByYearAndName", query="select gc from GraduationClass gc where gc.name= :name and gc.year= :year")
})
public class GraduationClass {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable=false)
    private String name;
    // @Column(nullable=false)
    @Column(name="class_year", nullable=false)
    private Integer year;
    // @Column
    @OneToMany(mappedBy="belongTo")
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GraduationClass setId(Long id) {
        this.id = id;
        return this;
    }

    public GraduationClass setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public GraduationClass setYear(Integer year) {
        this.year = year;
        return this;
    }

    public List<Student> getStudents() {
        return students;
    }

    public GraduationClass setStudents(List<Student> students) {
        this.students = students;
        return this;

    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);

    }
}
