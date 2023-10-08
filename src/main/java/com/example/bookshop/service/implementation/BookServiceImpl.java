package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import com.example.bookshop.mappers.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto book) {
        Book book1 = bookMapper.toModel(book);
        return bookMapper.toDto(bookRepository.save(book1));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.toDto(bookRepository.getById(id));
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBookById(Long id, CreateBookRequestDto bookDto) {
        Book book = bookMapper.toModel(bookDto);
        bookRepository.findById(id).map(book1 -> {
            book1.setAuthor(book.getAuthor());
            book1.setDescription(book.getDescription());
            book1.setIsbn(book.getIsbn());
            book1.setPrice(book.getPrice());
            book1.setCoverImage(book.getCoverImage());
            book1.setTitle(book.getTitle());
            return bookRepository.save(book1);
        }).orElseGet(() -> bookRepository.save(book));
    }
}
