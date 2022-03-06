package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.repository.api.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherTest extends Base {

    TeacherRepository teacherRepository;

    @BeforeEach
    void before() {
        teacherRepository = daoFactory.newTeacherRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveTeacher()  {
        // TODO
        final var subject = Fixtures.createSubject();
        final var graduationClass = Fixtures.createClass();
        final var student = Fixtures.createStudent(graduationClass);
        final var teacher = Fixtures.createTeacher(subject, graduationClass, student);

        entityManager.getTransaction().begin();
        entityManager.persist(subject);
        entityManager.persist(graduationClass);
        entityManager.persist(student);
        teacherRepository.save(teacher);
        entityManager.getTransaction().commit();
        entityManager.detach(teacher);
        entityManager.detach(subject);
        entityManager.detach(student);
        entityManager.detach(graduationClass);

        var pTeacher=teacherRepository.findById(teacher.getId());
        assertThat(pTeacher).isNotNull().isNotSameAs(teacher);
    }

    @Test
    void shouldFindHeadingGraduationClassByYearAndName() {
        final var YEAR = 2022;
        final var NAME = "la classe";
        final var gc = Fixtures.createClass();
        gc.setYear(YEAR);
        gc.setName(NAME);

        final var gc2 = Fixtures.createClass();
        final var t1 = Fixtures.createTeacher(null,gc, (Student[])null);
        final var t2 = Fixtures.createTeacher(null,gc2, (Student[])null);

        entityManager.getTransaction().begin();
        entityManager.persist(gc);
        entityManager.persist(gc2);
        teacherRepository.save(t1);
        teacherRepository.save(t2);
        entityManager.getTransaction().commit();
        entityManager.detach(gc);
        entityManager.detach(gc2);
        entityManager.detach(t1);
        entityManager.detach(t2);

        var pTeacher = teacherRepository.findHeadingGraduationClassByYearAndName(YEAR, NAME);
        assertThat(pTeacher).isNotNull().isNotSameAs(t1);
        assertThat(pTeacher.getHeading().getName()).isEqualTo(t1.getHeading().getName());

    }

}
