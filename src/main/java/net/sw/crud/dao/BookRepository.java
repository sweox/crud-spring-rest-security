package net.sw.crud.dao;

import net.sw.crud.entitiy.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
