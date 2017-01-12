package com.ekart.springbootjetty.sample.dal.repository.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
      * When a new entity is saved (inserted) without a custom ID being generated,
      * JPA automatically fires a SELECT statement to get the entity state from the
      * DB, in order to figure out whether to insert or update the entity. It does
      * this because it is not sure whether the entity is already present in the
      * database. For our cases, we are generating the ID and we know for sure that
      * there is no duplicate at the time of insertion. If there is a duplicate, we
      * are fine with failing without upsert happening. To avoid the penalty of the
      * extra SELECT before every INSERT, this custom repository directly calls the
      * underlying EntityManager's persist method which fires a blind insert.
      *
      * @author vijay.daniel
      *
      */
@NoRepositoryBean
public interface InsertSupportedJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> { // NOSONAR

   // See http://bit.ly/1VJ5eiF

   /**
    * Inserts the specified entity into the database
    *
    * WARNING: Please be careful while using this method. It assumes that the ID
    * is already generated by the caller, and does not perform an upsert. It
    * directly fires an INSERT query on the database and nothing else.
    *
    * @param entity
    *           The entity to be inserted
    */
   void insert(T entity);

   /**
    * Inserts the list of specified entities into the database
    *
    * WARNING: Please be careful while using this method. It assumes that the ID
    * is already generated by the caller, and does not perform an upsert. It
    * directly fires an INSERT query on the database and nothing else.
    *
    * @param entities
    *           The list of entities to be inserted
    */
   void insert(Iterable<T> entities);

   /**
    * Inserts the specified entity into the database and flushes the transaction
    *
    * WARNING: Please be careful while using this method. It assumes that the ID
    * is already generated by the caller, and does not perform an upsert. It
    * directly fires an INSERT query on the database and nothing else.
    *
    * @param entity
    *           The entity to be inserted
    */
   void insertAndFlush(T entity);

   /**
    * Inserts the specified entities into the database and flushes the
    * transaction
    *
    * WARNING: Please be careful while using this method. It assumes that the ID
    * is already generated by the caller, and does not perform an upsert. It
    * directly fires an INSERT query on the database and nothing else.
    *
    * @param entities
    *           The entities to be inserted
    */
   void insertAndFlush(Iterable<T> entities);

   /**
    * Finds all the entities for the specified IDs. If not possible, it raises
    * an EntityNotFoundException
    *
    * @param ids
    *           The IDs to be used for looking up the database rows
    * @return The model instances corresponding to the specified IDs
    * @throws javax.persistence.EntityNotFoundException
    *            If there are no entities for one or more IDs
    */
   List<T> retrieveAllOrThrow(Collection<ID> ids);
}