package org.example.bookshop.repository;

import org.example.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {
    boolean existsByIsbn(String isbn);
}
