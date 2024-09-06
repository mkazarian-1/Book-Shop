package org.example.bookshop.repository;

import java.util.List;
import org.example.bookshop.model.Book;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
