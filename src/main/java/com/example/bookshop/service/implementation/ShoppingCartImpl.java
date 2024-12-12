package com.example.bookshop.service.implementation;

import com.example.bookshop.dto.CartItemRequestDto;
import com.example.bookshop.dto.ShoppingCartDto;
import com.example.bookshop.exception.EntityNotFoundException;
import com.example.bookshop.mapper.ShoppingCartMapper;
import com.example.bookshop.model.CartItem;
import com.example.bookshop.model.ShoppingCart;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CartItemRepository;
import com.example.bookshop.repository.ShoppingCartRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartImpl implements ShoppingCartService {
    private ShoppingCartRepository shoppingCartRepository;
    private ShoppingCartMapper shoppingCartMapper;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private CartItemRepository cartItemRepository;

    @Override
    public Page<ShoppingCartDto> findAll(Pageable pageable) {
        return new PageImpl<>(shoppingCartRepository.findAll(pageable)
                .stream()
                .map(shoppingCartMapper::toDto)
                .toList());
    }

    @Override
    public ShoppingCartDto save(CartItemRequestDto cartItemRequestDto,
                                UserDetails userDetails) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(
                getCurrentUser(userDetails).getId()
        );
        Optional<CartItem> cartItem = Optional.ofNullable(
                cartItemRepository.findByBook_IdAndShoppingCart_Id(
                cartItemRequestDto.getBookId(), shoppingCart.getId()
        ));
        if (cartItem.isPresent()) {
            throw new EntityNotFoundException("This book exists in your cart");
        } else {
            CartItem item = new CartItem();
            item.setQuantity(cartItemRequestDto.getQuantity());
            item.setBook(bookRepository.getReferenceById(cartItemRequestDto.getBookId()));
            shoppingCart.getCartItems().add(item);
        }
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartDto updateBookQuantity(Long id, int quantity, UserDetails userDetails) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(
                getCurrentUser(userDetails).getId()
        );
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCart_Id(
                id, shoppingCart.getId()
        );
        cartItem.setQuantity(quantity);
        return shoppingCartMapper.toDto(cartItemRepository.save(cartItem).getShoppingCart());
    }

    @Override
    public void deleteBook(Long id, UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(user.getId());
        CartItem cartItem = cartItemRepository.findByBook_IdAndShoppingCart_Id(
                id, shoppingCart.getId()
        );
        cartItemRepository.delete(cartItem);
    }

    private User getCurrentUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).get();
    }

}
