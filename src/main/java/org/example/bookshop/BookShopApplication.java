package org.example.bookshop;

import java.math.BigDecimal;
import org.example.bookshop.model.Book;
import org.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookShopApplication {

    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setAuthor("Jhon");
            book.setIsbn("Toor");
            book.setTitle("Some thing cool");
            book.setPrice(new BigDecimal(100));

            Book book2 = new Book();
            book2.setAuthor("Jhon1");
            book2.setIsbn("Toor1");
            book2.setTitle("Some thing cool1");
            book2.setPrice(new BigDecimal(1020));

            bookService.save(book);
            bookService.save(book2);

            System.out.println(bookService.findAll());
        };
    }
}
