package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.repository.api.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.assertj.core.util.Arrays;

class StudentTest extends Base {

    StudentRepository studentRepository;

    @BeforeEach
    void before() {
        studentRepository = daoFactory.newStudentRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveStudent() {
        final var graduationClass = Fixtures.createClass();
        final var student = Fixtures.createStudent(graduationClass);
        graduationClass.addStudent(student);
        entityManager.getTransaction().begin();
        entityManager.persist(graduationClass);
        studentRepository.save(student);
        entityManager.getTransaction().commit();
        entityManager.detach(graduationClass);
        entityManager.detach(student);

        var pStudent = studentRepository.findById(student.getId());
        assertThat(pStudent).isNotNull().isNotSameAs(student);
        assertThat(pStudent.getFirstName()).isEqualTo(student.getFirstName());
    }

    @Test
    void shouldFindStudentHavingGradeAverageAbove() {
       final var AVERAGE = 5f; 
       final var gc = Fixtures.createClass();
       final var s1 = Fixtures.createStudent(gc);
       final var s2 = Fixtures.createStudent(gc);
       final var s3 = Fixtures.createStudent(gc);


       final var subject = Fixtures.createSubject();
       
       final var gs1n1 =Fixtures.createGrade(subject);
       final var gs1n2 =Fixtures.createGrade(subject);
       final var gs2n1 =Fixtures.createGrade(subject);
       final var gs2n2 =Fixtures.createGrade(subject);
       final var gs3n1 =Fixtures.createGrade(subject);
       final var gs3n2 =Fixtures.createGrade(subject);
        //#################################################
       gs1n1.setValue(10f);
       gs1n1.setWeight(0.6f);
       
       gs1n2.setValue(10f);
       gs1n2.setWeight(0.4f);

       ArrayList<Grade> l1 = new ArrayList<>();
       l1.add(gs1n1);
       l1.add(gs1n2);
       s1.setGrades(l1);
        //#################################################
       gs2n1.setValue(8f);
       gs2n1.setWeight(0.6f);
       
       gs2n2.setValue(7f);
       gs2n2.setWeight(0.4f);

       ArrayList<Grade> l2 = new ArrayList<>();
       l2.add(gs2n1);
       l2.add(gs2n2);
       s2.setGrades(l2);
        //#################################################
       gs3n1.setValue(2f);
       gs3n1.setWeight(0.6f);
       
       gs3n2.setValue(3f);
       gs3n2.setWeight(0.4f);

       ArrayList<Grade> l3 = new ArrayList<>();
       l3.add(gs3n1);
       l3.add(gs3n2);
       s3.setGrades(l3);
        //#################################################

        ArrayList<ArrayList<Grade>> grades = new ArrayList<>();
        grades.add(l1);
        grades.add(l2);
        grades.add(l3);
        ArrayList<Student> expectedStudents = new ArrayList<>();
        expectedStudents.add(s1);
        expectedStudents.add(s2);
        entityManager.getTransaction().begin();
        entityManager.persist(gc);
        entityManager.persist(subject);

        for (ArrayList<Grade> arrayList : grades) {
            for (Grade grade : arrayList) {
                entityManager.persist(grade);
            }
        }

        studentRepository.save(s1);
        studentRepository.save(s2);
        studentRepository.save(s3);

        entityManager.getTransaction().commit();
        entityManager.detach(gc);
        entityManager.detach(subject);
        for (ArrayList<Grade> arrayList : grades) {
            for (Grade grade : arrayList) {
                 entityManager.detach(grade);
            }
        }
        entityManager.detach(s1);
        entityManager.detach(s2);
        entityManager.detach(s3);

        final var pStudents = studentRepository.findStudentHavingGradeAverageAbove(AVERAGE);
        // for (Student student : pStudents) {
        //     for(Grade grade: student.getGrades()){ 
        //         System.out.println(grade.getValue());
        //     }
        // }
        assertThat(pStudents).isNotNull().isNotSameAs(expectedStudents);
        assertThat(pStudents.size()).isEqualTo(expectedStudents.size());

        
        


    }

    //methode de test suplémentaire pour tester update et getAll
    @Test
    void shouldFindAllStudentsAndUpdate() {
        final var gc = Fixtures.createClass();

        
        ArrayList<Student> expectedStudents = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            expectedStudents.add(Fixtures.createStudent(gc));
        }
        
        entityManager.getTransaction().begin();
        entityManager.persist(gc);
        for (Student student : expectedStudents) {
            studentRepository.save(student);
        }
        for (Student student : expectedStudents) {
            student.setFirstName("mamoune");
        }
        for (Student student : expectedStudents) {
            studentRepository.save(student);
        }
        entityManager.getTransaction().commit();
        for (Student student : expectedStudents){
            entityManager.detach(student);
        }

        final var pStudents = studentRepository.getAll();
        assertThat(pStudents).isNotNull().isNotSameAs(expectedStudents);
        assertThat(pStudents.size()).isEqualTo(expectedStudents.size());
        for (int i = 0; i < pStudents.size(); i++) {
            System.out.println(pStudents.get(i).getFirstName());
            assertThat(pStudents.get(i).getFirstName()).isEqualTo(expectedStudents.get(i).getFirstName());
        }
        


    }


}
