package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.GraduationClass;
import fr.uga.im2ag.l3.miage.db.model.Student;
import fr.uga.im2ag.l3.miage.db.model.Teacher;
import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

class SubjectTest extends Base {

    SubjectRepository subjectRepository;

    @BeforeEach
    void before() {
        subjectRepository = daoFactory.newSubjectRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveSubject() {

        final var subject = Fixtures.createSubject();

        entityManager.getTransaction().begin();
        subjectRepository.save(subject);
        entityManager.getTransaction().commit();
        entityManager.detach(subject);

        var pSubject = subjectRepository.findById(subject.getId());
        assertThat(pSubject).isNotNull().isNotSameAs(subject);
        assertThat(pSubject.getName()).isEqualTo(subject.getName());

    }

    @Test
    void shouldFindTeachersForSubject() {
        // TODO
        final var subject = Fixtures.createSubject();
        final var teacher = Fixtures.createTeacher(subject,(GraduationClass) null,(Student[]) null); //classe et student pas necessaire, null en placeholder
        final var teacher2 = Fixtures.createTeacher(subject,(GraduationClass) null,(Student[]) null);
        final var teacher3 = Fixtures.createTeacher(subject,(GraduationClass) null,(Student[]) null);

        final Collection<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher2);
        teachers.add(teacher3);

        entityManager.getTransaction().begin();

        entityManager.persist(teacher);
        entityManager.persist(teacher2);
        entityManager.persist(teacher3);
        subjectRepository.save(subject);

        entityManager.getTransaction().commit();

        entityManager.detach(subject);
        entityManager.detach(teacher);
        entityManager.detach(teacher3);
        entityManager.detach(teacher2);

        var pTeachers = subjectRepository.findTeachers(subject.getId());
        assertThat(pTeachers).isNotNull();
        assertThat(pTeachers.size()).isEqualTo(teachers.size());  //on compare la taille de la collection retournée et la collection crée avant la persistence
    }

}
