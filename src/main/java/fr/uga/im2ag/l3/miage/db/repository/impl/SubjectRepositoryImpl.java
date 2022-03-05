package fr.uga.im2ag.l3.miage.db.repository.impl;

import fr.uga.im2ag.l3.miage.db.repository.api.SubjectRepository;
import fr.uga.im2ag.l3.miage.db.model.Subject;
import fr.uga.im2ag.l3.miage.db.model.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubjectRepositoryImpl extends BaseRepositoryImpl implements SubjectRepository {


    public SubjectRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void save(Subject entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Subject entity) {
        entityManager.remove(entity);
    }

    @Override
    public Subject findById(Long id) {
        return entityManager.find(Subject.class, id);
    }

    @Override
    public List<Subject> getAll() {
        Query subjectFindAll = entityManager.createNamedQuery("Subject.findAll", Subject.class);
        @SuppressWarnings("unchecked")
        List<Subject> subjects = subjectFindAll.getResultList();
        return subjects;
    }

    @Override
    public Collection<Teacher> findTeachers(Long id) {
        return entityManager.createNamedQuery("Subject.findTeachers", Teacher.class).setParameter("id", id).getResultList();
    }
}
