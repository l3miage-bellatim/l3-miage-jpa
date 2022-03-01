package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.repository.api.GraduationClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class GraduationClassTest extends Base {

    GraduationClassRepository classRepository;

    @BeforeEach
    void before() {
        classRepository = daoFactory.newGraduationClassRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveClass() {
        // TODO
        final var graduationClass = Fixtures.createClass();
        final var student = Fixtures.createStudent(graduationClass);
        graduationClass.addStudent(student);
        entityManager.getTransaction().begin();
        entityManager.persist(student);
        classRepository.save(graduationClass);
        entityManager.getTransaction().commit();
        entityManager.detach(graduationClass);
        entityManager.detach(student);

        var pGraduationClass = classRepository.findById(graduationClass.getId());
        assertThat(pGraduationClass).isNotNull().isNotSameAs(graduationClass);
        assertThat(pGraduationClass.getName()).isEqualTo(graduationClass.getName());

    }


    @Test
    void shouldFindByYearAndName() {
        // TODO
    }

}
