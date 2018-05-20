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

import java.util.List;

/**
 * The interface Base service class.
 *
 * @param <T> the type parameter define data transaction object (dto) class
 * @param <E> the type parameter define entity object class
 * @author Alex Gladkikh
 */
public interface BaseService<T extends BaseEntity, E extends BaseEntity> {


    /**
     * Find all list.
     *
     * @param sort the kind of sort
     * @return the list of entities
     */
    List<T> findAll(Sort sort);


    /**
     * Get count of collection.
     *
     * @return the size of collection
     */
    long count();

    /**
     * Save dto.
     *
     * @param dto the dto
     * @return the saved dto
     */
    T save(T dto);

    /**
     * Find dto by id.
     *
     * @param id the dto id
     * @return the dto
     */
    T findOne(String id);

    /**
     * Delete dto (Data transaction object).
     *
     * @param id the dto (Data transaction object) id
     */
    void delete(String id);

    /**
     * Convert entity to dto (Data transaction object).
     *
     * @param obj the entity object
     * @return the dto (Data transaction object)
     */
    T obj2dto(E obj);

    /**
     * Convert dto (Data transaction object) to entity.
     *
     * @param dto the dto (Data transaction object)
     * @return the entity
     */
    E dto2obj(T dto);
}
