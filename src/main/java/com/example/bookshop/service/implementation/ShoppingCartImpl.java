package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.AddBookShoppingCartRequestDto;
import com.example.bookshop.dto.ShoppingCartDto;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartImpl implements ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private ShoppingCartMapper shoppingCartMapper;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Override
    public Page<ShoppingCartDto> findAll(Pageable pageable) {
        return new PageImpl<>(shoppingCartRepository.findAll(pageable)
                .stream()
                .map(shoppingCartMapper::toDto)
                .toList());
    }

    @Override
    public void save(AddBookShoppingCartRequestDto addBookShoppingCartRequestDto,
                                UserDetails userDetails) {
        Book book = bookRepository.getReferenceById(addBookShoppingCartRequestDto.getBookId());
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(addBookShoppingCartRequestDto.getQuantity());
        User user = getCurrentUser(userDetails);
        user.getShoppingCart().getCartItems().add(cartItem);
        userRepository.save(user);
    }

    @Override
    public void updateBookQuantity(Long id, int quantity, UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        user.getShoppingCart()
                .getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getId().equals(id))
                .forEach(cartItem -> cartItem.setQuantity(quantity));
        userRepository.save(user);
    }

    @Override
    public void deleteBook(Long id, UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        user.getShoppingCart()
                .getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getBook()
                        .getId()
                        .equals(id))
                .forEach(cartItem -> cartItem.setBook(null));
        userRepository.save(user);
    }

    private User getCurrentUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).get();
    }

}
