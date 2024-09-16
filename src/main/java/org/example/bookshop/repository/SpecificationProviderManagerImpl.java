package org.example.bookshop.repository;

import lombok.RequiredArgsConstructor;
import org.example.bookshop.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SpecificationProviderManagerImpl<Book> implements SpecificationProviderManager<Book> {
    List<SpecificationProvider<Book>> bookSpecificationProviders;
    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(b -> b.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no SpecificationProvider with this key" + key));
    }
}
