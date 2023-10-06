package com.example.bookshop.repository.implementation;

import com.example.bookshop.exceptions.DataProcessingException;
import com.example.bookshop.exceptions.EntityNotFoundException;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl implements BookRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Book save(Book book) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert a book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return entityManager.createNativeQuery("SELECT * FROM book", Book.class)
                    .getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get all books ", e);
        }
    }

    @Override
    public Book getById(Long id) {
        try {
            return entityManager.find(Book.class, id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get all book by id : " + id, e);
        }
    }
}
