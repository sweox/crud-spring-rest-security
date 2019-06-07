package net.sw.crud.controller;

import net.sw.crud.dao.BookRepository;
import net.sw.crud.entitiy.Book;
import net.sw.crud.error.BookNotFoundException;
import net.sw.crud.error.BookUnSupportedFieldPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@Validated
public class BookController {

    @Autowired
    private BookRepository repository;

    // Find all books
    @GetMapping("/books")
    public List<Book> findAll() {
        return repository.findAll();
    }

    // Save book
    //return 201 instead of 200
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public Book newBook(@Valid @RequestBody Book newBook) {
        return repository.save(newBook);
    }

    // Find book by id
    @GetMapping("/books/{id}")
    public Book findOne(@PathVariable @Min(1) Long id) {

        return repository
                .findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // Save or update book by id
    @PutMapping("/books/{id}")
    public Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {

        return repository
                .findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setAuthor(newBook.getAuthor());
                    x.setPrice(newBook.getPrice());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    // update author only
    @PatchMapping("/books/{id}")
    public Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repository
                .findById(id)
                .map(x -> {

                    String author = update.get("author");
                    if (!StringUtils.isEmpty(author)) {
                        x.setAuthor(author);

                        // better create a custom method to update a value = :newValue where id = :id
                        return repository.save(x);
                    } else {
                        throw new BookUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new BookNotFoundException(id);
                });

    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
