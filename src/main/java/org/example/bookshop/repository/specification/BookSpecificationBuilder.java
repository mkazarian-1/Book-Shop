package org.example.bookshop.repository.specification;

import lombok.RequiredArgsConstructor;
import org.example.bookshop.dto.book.BookSearchParametersDto;
import org.example.bookshop.model.Book;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearch) {
        Specification<Book> specification = Specification.where(null);

        if (bookSearch.title() != null && bookSearch.title().length != 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(bookSearch.title()));
        }

        if (bookSearch.author() != null && bookSearch.author().length != 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(bookSearch.author()));
        }

        if (bookSearch.isbn() != null && bookSearch.isbn().length != 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(bookSearch.isbn()));
        }

        return specification;
    }
}
