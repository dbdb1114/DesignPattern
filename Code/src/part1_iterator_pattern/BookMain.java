package part1_iterator_pattern;

import java.util.Iterator;

public class BookMain {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf();
        bookShelf.appendBook(new Book("JPA 완전 정복 "));
        bookShelf.appendBook(new Book("GOF 디자인 패턴"));
        bookShelf.appendBook(new Book("토비의 스프링"));
        bookShelf.appendBook(new Book("누구나 자료구조와 알고리즘 "));

        /**명시적으로 iterator를 사용하는 방법*/
        Iterator<Book> it = bookShelf.iterator();
        while (it.hasNext()){
            Book book = it.next();
            System.out.println(book.getName());
        }
        System.out.println();

        /**확장 for문을 활용*/
        for (Book book: bookShelf) {
            System.out.println(book.getName());
        }
        System.out.println();
    }
}
