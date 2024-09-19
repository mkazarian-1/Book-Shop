package org.example.bookshop.repository.book;

import java.util.Arrays;
import org.example.bookshop.model.Book;
import org.example.bookshop.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String CHARACTERISTIC = "isbn";

    @Override
    public String getKey() {
        return CHARACTERISTIC;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(CHARACTERISTIC)
                .in(Arrays.stream(params).toArray());
    }
}
