package com.example.bookshop.service;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.BookSearchParametersDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findByBookCriteria(BookSearchParametersDto bookSearchParametersDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto get(Long id);

    void delete(Long id);

    BookDto update(Long id, CreateBookRequestDto book);
}
