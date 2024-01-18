## Iterator패턴

Iterator패턴은 무엇인가 많이 모여 있을 때 이를 순서대로 가리키며 전체를 검색하고 처리를 
반복하는 것 입니다. iterate라는 영어 단어가 "반복하다"라는 뜻입니다. 그래서 iterator를
반복자라고도 합니다.

## 예제 프로그램

### Iterable<E\>인터페이스
Iterable 인터페이스는 처리를 반복할 대상을 나타내는 것 입니다.

```java
public interface Iterable<E> {
    public abstract Iterator<E> iterator();
}
```
위와 같이 Iterable 인터페이스에는 iterator 메소드가 선언되어 있습니다. 이 메소드는 
집합체에 대응하는 Iterator를 만들기 위한 것입니다. 집합체에 포함된 요소를 하나하나 처리해 나가고 
싶을 때 이 iterator 메소드를 사용해 Iterator 인터페이스를 구현한 클래스의 인스턴스를 하나 만듭니다.

### Iterator<E\> 인터페이스 
Iterator 인터페이스는 하나하나의 요소 처리를 반복하기 위한 것으로 루프 변수와 같은 역할을 합니다. 

```java
public interface Iterator<E>{
    public abstract boolean hasNext();
    public abstract E next();
}
```
hasNext메소드는  다음 요소가 있는지 확인하는 메소드이고, next메소드는 다음요소를 가져오는 메소드 입니다. 
next메소드는 내부 상태를 다음으로 진행시켜 놓는 역할을 하기 때문에 현재 값을 반환 후 다음 값을 내보낼 준비를
내부동작 과정으로 가지고 있습니다.

### Book 클래스 
간단히 이름만 받을 수 있는 메소드를 만듭니다. 이름은 생성자를 통해 초기화 합니다.
```java
public class Book {
    private String name;

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

### BookShelf 클래스
```java
public class BookShelf implements Iterable<Book>{

    private Book[] books;
    private int last = 0;

    public BookShelf(int maxsize) {
        this.books = new Book[maxsize];
    }

    public Book getBookAt(int index){
        return books[index];
    }

    public void appendBook(Book book){
        books[last] = book;
        last++;
    }

    public int getLength(){
        return last;
    }

    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}
```

### BookShelfIterator

bookShelf 필드는 현재 해당 BookShelfIterator가 검색할 책장이고, index필드는 현재 보고 있는 책을 가리킵니다.
next 메소드에서는 book을 반환 후 index를 증가시키고 있으므로, 다음 책을 내보낼 준비를 한다고 볼 수 있습니다.

```java
package part1_iterator_pattern;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BookShelfIterator implements Iterator<Book> {

    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < bookShelf.getLength()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Book next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}

```

### Main 실행
명시적으로 iterator를 사용하는 방법과 foreach 구문을 쓰는 방식이 있습니다. 
foreach구문은 일반적으로 for문의 iterator를 사용하여 처리합니다. 결국 java의 확장 for문 배후에는
Iterator 패턴이 사용된다고 볼 수 있습니다. **덧붙여 Java의 배열은 Iterable 인터페이스를 구현하지는 않았지만,
확장 for문을 사용하여 요소에 대한 반복처리를 기술할 수 있습니다.** 
```java
public class BookMain {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(4);
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
```

## Iterator 패턴의 역할 구분 
Iterator는 총 네 가지 역할 구분이 있습니다. 
### 1. Iteraotor (반복자)
요소를 순서대로 검색하는 **인터페이스를 결정합니다.** 예제 프로그램에서는 Iterator< E > 인터페이스가 이 역할을 맡아서
다음 요소가 존재하는지 조사하는 hasNext(), 다음 요소를 가져오눈 next()메소드를 정합니다.
### 2. ConcreteIterator (구체적인 반복자)
Iterator가 결정한 **인터페이스를 실제로 구현합니다.** 예제 프로그램에서는 BookShelfIterator가 이 역할을 
맡았습니다. 이 역할은 검색에 필요한 정보를 가지고 있어야 합니다. 예제 프로그램에서는 BookShelf클래스의 인스턴스를
bookshelf 필드에서 기억하고, 검색중인 책의 index를 기억합니다. 
### 3. Aggregate (집합체)
Iterator를 만들어 내는 **인터페이스를 결정합니다.**  이 인터페이스는 내가 가진 요소를 차례로 검색해주는 메소드를 
만들어내는 인터페이스 입니다. 예제 프로그램에서는 Iterable< E > 인터페이스가 이 역할을 맡아서 iterator 메소드를
결정합니다. 
### 4. ConcreteAggregate (구체적인 집합체)
Aggregate가 결정한 인터페이스를 실제로 구현합니다. 구체적인 Iterator역할, 즉 ConcreteIterator의
인스턴스를 만들어 냅니다. 예제 프로그램에서는 BookShelf클래스가 이 역할을 맡아서 iterator 메소드를 구현합니다.


## 사고 넓히기 
### iterator패턴의 사용 이유
Iterator패턴은 구현에 의존하지 않습니다. 예를 들어 특정 앞으로 만들어볼 BookShelf클래스 내부에서 Book이라는 객체를
배열에 담아서 구현하고 있습니다. 그리고 BookShelf 클래스가 Iterable 인터페이스를 구현함으로써 iterator를
반환하는 메소드를 사용하게 되는 것 입니다.

이렇게 Iterator라는 특정 구현을 함으로써 우리는 이후에 Book 객체의 관리를 배열에 하든, 연결리스트에 하든
Iterator는 수정할 필요가 없어지는 것입니다.

### 추상화의 힘
우리가 이렇게 Iterator라는 인터페이스를 구현함으로써 다형성 개념이 꽃을 피게 됩니다. 구체적인 클래스로만 개발을
하다보면 확장성과 재사용성이 떨어지기 때문에 우리는 추상 클래스나 인터페이스를 사용하여 프로그래밍 해야함을 기억해야 됩니다. 


## 관련 패턴

### Visitor 패턴
Iterator 패턴은 집합체의 요소를 하나씩 처리해 나갑니다. 그러나 Iterator< E > 인터페이스 안에 그 처리까지는
기술되어 있지 않습니다. Visitor 패턴은 많은 요소가 모여 있는 내부를 돌아다니며 같은 처리를 반복해서 적용해 나갑니다.

### Composite 패턴 
Compositre 패턴은 재귀적인 구조를 가집니다. 

### Factory Method 패턴
iterator 메소드가 Iterator 인스턴스를 만들 때 Factory Method 패턴이 상요되는 경우가 많습니다. 
