package com.example.spaceshipapi.mapper;

import java.util.Map;

/**
 * Definición de mapeadores de objetos.
 *
 * @param <S> Source
 * @param <T> Target
 *
 * @author JMARO9T
 *
 */
public interface IBaseMapper<S, T> {

    /**
     *
     * @param source
     * @param mappingContext
     * @return
     */
    T map(S source);

    S reverseMap(T source);
}