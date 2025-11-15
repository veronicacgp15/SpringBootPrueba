package com.vcgp.proyecto.proyecto.application.usecase;

import java.util.List;

public interface CrudService<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T create(T requestDTO);
    T edit(ID id, T requestDTO);
    void delete(ID id);
}
