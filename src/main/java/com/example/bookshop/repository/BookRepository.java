package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("select books from Book books join books.categories category on category.id = ?1")
    List<Book> findAllByCategoryId(Long id);
}
