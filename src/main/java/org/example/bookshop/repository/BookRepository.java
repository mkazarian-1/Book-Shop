package org.example.bookshop.repository;

import java.util.List;
import java.util.Optional;
import org.example.bookshop.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);

    List<Book> findAllByCategoriesId(Pageable pageable, Long categoryId);

    @EntityGraph(attributePaths = "categories")
    @NonNull
    Page<Book> findAll(@NonNull Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    @NonNull
    Page<Book> findAll(Specification<Book> specification, @NonNull Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    @NonNull
    Optional<Book> findById(@NonNull Long id);
}
