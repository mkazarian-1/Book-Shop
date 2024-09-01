package org.example.bookshop.service;

import java.util.List;
import org.example.bookshop.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
