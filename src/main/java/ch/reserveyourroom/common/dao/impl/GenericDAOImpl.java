package ch.reserveyourroom.common.dao.impl;

import ch.reserveyourroom.common.dao.GenericDao;
import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class GenericDaoImpl<T extends AbstractEntity> implements GenericDao<T> {

    @PersistenceContext
    private EntityManager em;

    @NotNull
    private Class<T> entityClass;

    @NotNull
    private Class<UUID> idClass;

    public GenericDaoImpl(){

        Type type = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        entityClass = (Class<T>) pt.getActualTypeArguments()[0];
        //idClass = (Class<PK>) pt.getActualTypeArguments()[1];
    }

    public long countAll(final Predicate predicate) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(entityClass)));
        cq.where(predicate);
        TypedQuery<Long> typedQuery = em.createQuery(cq);
        return typedQuery.getSingleResult();
    }

    public long countAll() {

        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        Predicate alwaysTrue = cb.conjunction();
        return countAll( alwaysTrue );
    }

    public List<T> loadAll() {

        Query query = em.createQuery("FROM " + entityClass.getName());
        return query.getResultList();
    }

    public String create(final T t) {

        this.em.persist(t);
        return t.getUuid().toString();
    }

    public void delete(final UUID uuid) {

        this.em.remove(this.em.getReference(entityClass, uuid));
    }

    public Optional<T> read(final String id) {

        return Optional.ofNullable(this.em.find(entityClass, UUID.fromString(id)));
    }

    public T update(final T t) throws EntityOptimisticLockException {

        try {
            return this.em.merge(t);
        } catch (OptimisticLockException e) {
            throw new EntityOptimisticLockException(t, e);
        }
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
