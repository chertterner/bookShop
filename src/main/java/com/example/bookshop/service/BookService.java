package com.example.bookshop.service;

import com.example.bookshop.dto.BookDto;
import com.example.bookshop.dto.CreateBookRequestDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> getAll(Map<String, List<String>> params);

    List<BookDto> findAll(Pageable pageable);

    BookDto get(Long id);

    void delete(Long id);

    BookDto update(Long id, CreateBookRequestDto book);
}
