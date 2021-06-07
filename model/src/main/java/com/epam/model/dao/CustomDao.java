package com.epam.model.dao;

import com.epam.model.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface CustomDao<T extends Entity> {
    T create(T entity);

    Optional<T> findById(long id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(long id);

}