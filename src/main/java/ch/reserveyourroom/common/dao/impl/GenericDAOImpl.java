package ch.reserveyourroom.common.dao.impl;

import ch.reserveyourroom.common.dao.GenericDAO;
import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.common.exception.ReserveYourRoomOptimisticLockException;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class GenericDAOImpl<T extends AbstractEntity<PK>, PK extends Serializable> implements GenericDAO<T, PK> {

    @PersistenceContext
    protected EntityManager em;

    private Class<T> entityClass;

    public GenericDAOImpl(){

        Type type = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) type;
        entityClass = (Class) pt.getActualTypeArguments()[0];
    }

    public long countAll(final Predicate predicate) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        cq.select(cb.count(cq.from(entityClass)));
        cq.where(predicate);
        return em.createQuery(cq).getSingleResult();
    }

    public PK create(final T t) {

        this.em.persist(t);
        return t.getId();
    }

    public void delete(final Object id) {

        this.em.remove(this.em.getReference(entityClass, id));
    }

    public Optional<T> read(final PK id) {

        return Optional.ofNullable((T) this.em.find(entityClass, id));
    }

    public T update(final T t) throws ReserveYourRoomOptimisticLockException {

        try {
            return this.em.merge(t);
        } catch (OptimisticLockException e) {
            throw new ReserveYourRoomOptimisticLockException((AbstractEntity) t);
        }
    }
}
