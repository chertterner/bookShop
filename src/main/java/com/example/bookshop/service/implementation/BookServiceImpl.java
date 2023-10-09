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
    public BookDto get(Long id) {
        return bookMapper.toDto(bookRepository.getReferenceById(id));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void update(Long id, CreateBookRequestDto bookDto) {
        bookRepository.findById(id)
                .map(book1 ->
                    bookRepository
                        .save(bookMapper.toModel(bookDto)))
                .orElseGet(() -> bookRepository
                        .save(bookMapper
                                .toModel(bookDto)));
    }
}
