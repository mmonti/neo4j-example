package com.dreamworks.example.support;

/**
 * @author mmonti
 */
public interface IDGeneratorOperations {

    /**
     *
     * @return
     */
    String uuid();

    /**
     *
     * @param keys
     * @return
     */
    String hashIt(final Object... keys);
}