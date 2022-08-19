package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.exceptions.UserNotFound;
import com.bezkoder.springjwt.models.Item;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.ItemRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.exceptions.ItemNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/items")
    Item newItem(@RequestBody Item newItem)
    {
        return itemRepository.save(newItem);
    }

    @GetMapping("/items")
    List<Item> getAllItems()
    {
        return itemRepository.findAll();
    }

    @GetMapping("/items/{id}")
    Item getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFound(id));
    }

    @PutMapping("/items/{id}")
    Item updateItem(@RequestBody Item newItem, @PathVariable Long id) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setDescription(newItem.getDescription());
                    item.setImageUrl(newItem.getImageUrl());
                    return itemRepository.save(item);
                }).orElseThrow(() -> new ItemNotFound(id));
    }



    @PostMapping("/users/{userId}/items")
    public ResponseEntity<Item> addItemToUser(@PathVariable(value = "userId") Long userId, @RequestBody Item itemRequest) {
        Item item = userRepository.findById(userId).map(user -> {
            long itemId = itemRequest.getId();

            // item is existed
            if (itemId != 0L) {
                Item _item = itemRepository.findById(itemId)
                        .orElseThrow(() -> new ItemNotFound(itemId));
                user.addItem(_item);
                userRepository.save(user);
                return _item;
            }

            // add and create new Item
            user.addItem(itemRequest);
            return itemRepository.save(itemRequest);
        }).orElseThrow(() -> new UserNotFound(userId));

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }



    // Get the items with a users id
    @GetMapping("/users/{userId}/items")
    public ResponseEntity<List<Item>> getAllItemsByUserId(
            @PathVariable(value = "userId") Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFound(userId);
        }

        List<Item> items = itemRepository.findItemsByUsersId(userId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }


}
