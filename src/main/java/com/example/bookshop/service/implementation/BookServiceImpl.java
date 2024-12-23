package com.example.bookshop.service.implementation;

import com.example.bookshop.criteria.SpecificationBuilder;
import com.example.bookshop.dto.book.BookDto;
import com.example.bookshop.dto.book.BookSearchParametersDto;
import com.example.bookshop.dto.book.CreateBookRequestDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.BookMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.service.BookService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private SpecificationBuilder<Book> specificationBuilder;
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
    public List<BookDto> findByBookCriteria(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> specification = specificationBuilder.build(bookSearchParametersDto);

        return bookRepository.findAll(Objects.requireNonNull(specification))
                .stream().map(bookMapper::toDto)
                .toList();
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        return new PageImpl<>(bookRepository.findAll(pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList());
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
    public BookDto update(Long id, CreateBookRequestDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can't found book with id: " + id);
        }
        Book book = bookMapper.toModel(bookDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }
}
