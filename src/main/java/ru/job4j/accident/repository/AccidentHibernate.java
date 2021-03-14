package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import javax.persistence.EntityManager;;
import java.util.*;

//@Repository
public class AccidentHibernate {

    private final SessionFactory sf;

    @Autowired
    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
        AccidentType at1 = AccidentType.of(1, "две машины");
        AccidentType at2 = AccidentType.of(2, "машина и человек");
        AccidentType at3 = AccidentType.of(3, "машина и велосипед");
        Rule r1 = Rule.of(1, "статья 1");
        Rule r2 = Rule.of(2, "статья 2");
        Rule r3 = Rule.of(3, "статья 3");
        try (Session session = sf.openSession()) {
            session.getTransaction().begin();
            session.save(at1);
            session.save(at2);
            session.save(at3);
            session.save(r1);
            session.save(r2);
            session.save(r3);
            session.getTransaction().commit();
        }
    }

    public void accAdRules(Accident accident, String[] ids, Session session) {
        EntityManager em = session.getEntityManagerFactory().createEntityManager();
        for (String id : ids) {
            Rule r = em.find(Rule.class, Integer.parseInt(id));
            accident.addRule(r);
        }
        em.close();
    }

    public void create(Accident accident, String[] ids) {
        try (Session session = sf.openSession()) {
            session.getTransaction().begin();
            accAdRules(accident, ids, session);
            session.save(accident);
            session.getTransaction().commit();
        }
    }

    public void updateAccident(Accident accident, String[] ids) {
        try (Session session = sf.openSession()) {
            session.getTransaction().begin();
            accAdRules(accident, ids, session);
            session.update(accident);
            session.getTransaction().commit();
        }
    }

    public List<Accident> getAllAccidents() {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "select distinct a from Accident a join fetch a.rules", Accident.class).list();
        }
    }

    public List<AccidentType> getAllTypes() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from AccidentType", AccidentType.class).list();
        }
    }

    public List<Rule> getAllRules() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from Rule", Rule.class).list();
        }
    }

    public Accident findById(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "select distinct a from Accident a join fetch a.rules where a.id = :id",
                    Accident.class
            ).setParameter("id", id).uniqueResult();
        }
    }
}
