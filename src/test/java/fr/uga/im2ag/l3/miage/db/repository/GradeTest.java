package fr.uga.im2ag.l3.miage.db.repository;

import fr.uga.im2ag.l3.miage.db.model.Grade;
import fr.uga.im2ag.l3.miage.db.repository.api.GradeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

class GradeTest extends Base {

    GradeRepository gradeRepository;

    @BeforeEach
    void before() {
        gradeRepository = daoFactory.newGradeRepository(entityManager);
    }

    @AfterEach
    void after() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    void shouldSaveGrade() {
        //TODO tester les autres champs ? tester la cle etrangere
        final var grade = Fixtures.createGrade(Fixtures.createSubject()); //on creer un nouvet sujet a ajouter a la note
        entityManager.getTransaction().begin();
        entityManager.persist(grade.getSubject());  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
        gradeRepository.save(grade);
        entityManager.getTransaction().commit();
        entityManager.detach(grade);

        var pGrade = gradeRepository.findById(grade.getId());
        assertThat(pGrade).isNotNull().isNotSameAs(grade);
        assertThat(pGrade.getValue()).isEqualTo(grade.getValue());

    }

    @Test
    void shouldFailUpgradeGrade() {
        // TODO, ici tester que la mise à jour n'a pas eu lieu.
    }

    @Test
    void shouldFindHighestGrades() {
        final var limit = 7;
        final var grade = Fixtures.createGrade(Fixtures.createSubject()); //on creer un nouvet sujet a ajouter a la note
        final var grade1 = Fixtures.createGrade(Fixtures.createSubject()); //on creer un nouvet sujet a ajouter a la note
        final var grade2 = Fixtures.createGrade(Fixtures.createSubject()); //on creer un nouvet sujet a ajouter a la note
        final var grade3 = Fixtures.createGrade(Fixtures.createSubject()); //on creer un nouvet sujet a ajouter a la note
        final var grade4 = Fixtures.createGrade(Fixtures.createSubject()); //on creer un nouvet sujet a ajouter a la note
        grade.setValue(8f);
        grade1.setValue(3f);
        grade2.setValue(9f);
        grade3.setValue(2f);
        grade4.setValue(10f);
        ArrayList<Grade> grades = new ArrayList<>();
            grades.add(grade);
            grades.add(grade2);
            grades.add(grade4);

        
        entityManager.getTransaction().begin();
        entityManager.persist(grade.getSubject());  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
        entityManager.persist(grade1.getSubject());  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
        entityManager.persist(grade2.getSubject());  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
        entityManager.persist(grade3.getSubject());  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
        entityManager.persist(grade4.getSubject());  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
        gradeRepository.save(grade);
        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);
        entityManager.getTransaction().commit();
        entityManager.detach(grade);
        entityManager.detach(grade1);
        entityManager.detach(grade2);
        entityManager.detach(grade3);
        entityManager.detach(grade4);

        var pGrades = gradeRepository.findHighestGrades(limit);
        
        assertThat(pGrades).isNotNull();
        assertThat(pGrades.size()).isEqualTo(grades.size());
    }

    @Test
    void shouldFindHighestGradesBySubject() {
        final var limit=7;
        final var subjectTest = Fixtures.createSubject();  //sujet à chercher
        final var subject = Fixtures.createSubject(); //sujet témoin

        final var grade = Fixtures.createGrade(subjectTest); 
        final var grade1 = Fixtures.createGrade(subjectTest); 
        final var grade2 = Fixtures.createGrade(subjectTest); 
        final var grade3 = Fixtures.createGrade(subject); 
        final var grade4 = Fixtures.createGrade(subject);
        grade.setValue(8f);
        grade1.setValue(10f);
        grade2.setValue(2f);
        grade3.setValue(20f);
        grade4.setValue(20f);
        ArrayList<Grade> grades = new ArrayList<>();
            grades.add(grade);
            grades.add(grade1);

        entityManager.getTransaction().begin();
        entityManager.persist(subjectTest);  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
        entityManager.persist(subject);  //on rend le sujet de la note persistant, sinon le test lève une exception au commit
      
        gradeRepository.save(grade);
        gradeRepository.save(grade1);
        gradeRepository.save(grade2);
        gradeRepository.save(grade3);
        gradeRepository.save(grade4);
        entityManager.getTransaction().commit();
        entityManager.detach(grade);
        entityManager.detach(grade1);
        entityManager.detach(grade2);
        entityManager.detach(grade3);
        entityManager.detach(grade4);

        var pGrades = gradeRepository.findHighestGradesBySubject(limit, subjectTest);
        
        assertThat(pGrades).isNotNull();
        assertThat(pGrades.size()).isEqualTo(grades.size());
    }

}
