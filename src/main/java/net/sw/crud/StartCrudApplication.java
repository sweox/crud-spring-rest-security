package net.sw.crud;

import net.sw.crud.dao.BookRepository;
import net.sw.crud.entitiy.Book;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class StartCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartCrudApplication.class, args);
    }

    // init bean to insert 3 books into database.
    @Bean
    public CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            repository.save(new Book("Java 8 in Action", "Raoul-Gabriel Urma", new BigDecimal("44.36")));
            repository.save(new Book("JAVA Methods Programming", "Blinov", new BigDecimal("9.29")));
            repository.save(new Book("High Perfomance Java Persistence", "Mihalcea", new BigDecimal("48.25")));
        };
    }

}
