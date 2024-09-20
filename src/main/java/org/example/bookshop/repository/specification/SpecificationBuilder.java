package org.example.bookshop.repository.specification;

import org.example.bookshop.dto.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto bookSearch);
}
