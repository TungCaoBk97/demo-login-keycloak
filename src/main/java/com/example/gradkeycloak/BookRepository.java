package com.example.gradkeycloak;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
	private static Map<String, Book> books = new ConcurrentHashMap<>();

	static {
		books.put("B01", new Book("B01", "Algorithm & Data structure", "Alex Algorithm"));
		books.put("B02", new Book("B02", "Harry Potter", "J.K.Rowling"));
		books.put("B03", new Book("B03", "English in use", "Gareth English"));
	}

	public List<Book> readAll(){
		List<Book> allBooks = new ArrayList<>(books.values());
		allBooks.sort(Comparator.comparing(Book::getId));
		return allBooks;
	}
}
