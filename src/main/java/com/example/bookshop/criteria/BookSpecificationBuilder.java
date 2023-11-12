package com.example.bookshop.criteria;

import com.example.bookshop.dto.BookSearchParametersDto;
import com.example.bookshop.model.Book;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    @Autowired
    private SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto bookSearchParametersDto) {
        Specification<Book> specification = Specification.where(null);
        if (bookSearchParametersDto.getTitles() != null
                && !bookSearchParametersDto.getTitles().isEmpty()) {
            specification = specification
                    .and(specificationProviderManager
                            .getSpecificationProvider("title")
                            .getSpecification(bookSearchParametersDto
                                    .getTitles()));
        }

        if (bookSearchParametersDto.getAuthors() != null
                && !bookSearchParametersDto.getAuthors().isEmpty()) {
            specification = specification
                    .and(specificationProviderManager
                            .getSpecificationProvider("author")
                            .getSpecification(bookSearchParametersDto
                                    .getAuthors()));
        }

        if (bookSearchParametersDto.getPrices() != null
                && !bookSearchParametersDto.getPrices().isEmpty()) {
            specification = specification
                    .and(specificationProviderManager
                            .getSpecificationProvider("price")
                            .getSpecification(bookSearchParametersDto
                                    .getPrices().toString().lines().collect(Collectors.toList())));
        }
        return specification;
    }
}
