package com.example.bookshop.criteria;

import com.example.bookshop.model.Book;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "title";
    }

    public Specification<Book> getSpecification(List<String> params) {
        return (root, query, criteriaBuilder) -> root.get("title").in(params.stream().toList());
    }
}
