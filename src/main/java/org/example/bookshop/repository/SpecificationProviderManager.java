package org.example.bookshop.repository;

public interface SpecificationProviderManager <T>{
    SpecificationProvider<T> getSpecificationProvider(String key);
}
