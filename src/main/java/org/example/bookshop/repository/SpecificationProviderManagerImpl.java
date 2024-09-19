package org.example.bookshop.repository;

import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpecificationProviderManagerImpl<T>
        implements SpecificationProviderManager<T> {

    private final List<SpecificationProvider<T>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<T> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
            .filter(b -> b.getKey().equals(key))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                    "There is no SpecificationProvider with this key" + key));
    }
}
