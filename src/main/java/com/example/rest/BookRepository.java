package com.example.rest;

interface BookRepository {
    Book getByIsbn(String isbn);
}
