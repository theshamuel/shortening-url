/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.baseclasses.service;

import com.theshamuel.shrturl.baseclasses.entity.BaseEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Base service implementation.
 *
 * @param <T> the type parameter define data transaction object (dto) class
 * @param <E> the type parameter define entity object class
 *
 * @author Alex Gladkikh
 */
public abstract class BaseServiceImpl<T extends BaseEntity, E extends BaseEntity> implements BaseService<T,E> {


    /**
     * The Mongo repository of entity object class.
     */
    MongoRepository<E,String> mongoRepository;

    /**
     * Instantiates a new Base service.
     *
     * @param mongoRepository the mongo repository
     */
    public BaseServiceImpl(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll(Sort sort) {
        final List<T> result = new ArrayList<>();
        Optional<List<E>> listOfEntities = Optional.ofNullable(mongoRepository.findAll(sort));
            listOfEntities.ifPresent(item->{
                item.forEach(e-> {
                    result.add(obj2dto(e));
                });
        });
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        final List<T> result = new ArrayList<>();
        Optional<List<E>> listOfEntities = Optional.ofNullable(mongoRepository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "createdDate"))));
        listOfEntities.ifPresent(item->{
            item.forEach(e-> {
                result.add(obj2dto(e));
            });
        });
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return mongoRepository.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T dto) {
        return obj2dto(mongoRepository.save(dto2obj(dto)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(String id) {
        E result = mongoRepository.findOne(id);
        if (result!=null)
            return obj2dto(result);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        mongoRepository.delete(id);
    }



}
