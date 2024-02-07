## AbstrcatFactory 패턴<hr>
abstract factory는 추상적인 공장이라는 뜻입니다. 일반적으로 생각하면 추상적이라는 말과 공장이라는 말은 잘 연결되지 않습니다. 
추상적인 공장에서는 추상적인 부품을 조합하여 추상적인 제품을 만들어냅니다. 구체적인 메소드의 내용은 잊어버리고, 추상 메소드를 사용해 프로그래밍을
하는 의미는 TemplateMethod 패턴이나 Builder 패턴에서도 조금 언급했습니다.<br>
Abstract Factory 패턴에서는 추상적인 공장이 등장하고, 추상적인 부품을 조합하여 추상적인 제품을 만듭니다. 요컨대, 부품의 구체적인 구현에는
주목하지 않고 인터페이스에 주목합니다. 그리고 그 인터페이스만 사용해서 부품을 조립하고 제품으로 완성하는 것입니다.<br>
Template Method 패턴이나 Builder 패턴에서는 하위 클래승 단계에서 구체적으로 구현했습니다. Abstract Factory 패턴에서도 
하위 클래스 단계에서 구체적으로 구현합니다. 하위 클래스 단계에서는 구체적인 공장이 등장하고 구체적인 부품을 조합하여 구체적인 제품을 만듭니다. 


## 예제 프로그램 <hr>
Item 클래스는 Link와 Tray의 상위 클래스로 되어 있습니다. 이것은 Link와 Tray를 동일시하기 위한 클래스입니다. caption필드는
항목의 '표제어'를 나타냅니다. makeHTML 메소드는 추상 메소드이므로, 하위 클래스에서 구현해야만 합니다. 이 메소드를 호출하면, 
HTML의 문자열이 반환값이 됩니다. 
```java
public abstract class Item {
    protected String caption;

    public Item(String caption) {
        this.caption = caption;
    }
    
    public abstract String makeHTML();
}
```

### Link클래스
Link클래스는 HTML 하이퍼링크를 추상적으로 표현한 클래스입니다. url필드에는 링크를 걸 URL이 저장됩니다.
Link 클래스에서는 추상 메소드가 전혀 등장하지 않는 것처럼 보이지만, 그렇지 않습니다.
Link 클래스에서는 상위 클래스(Item)의 추상 메소드(makeHTML)를 구현하지 않았습니다.
그래서 Link 클래스도 추상 클래스가 됩니다.

```java
public abstract class Link extends Item{
    
    protected String url;

    public Link(String caption, String url) {
        super(caption);
        this.url = url;
    }
}
```


### Tray 클래스
Tray 클래스는 복수의 Link나 Tray를 모아서 한데 묶는 클래스입니다. Link나 Tray는 add 메소드를 사용해서 모읍니다. 'Link나 Tray'라는
부분을 표현하고자 add 메소드에서는 Link와 Tray의 상위 클래스인 Item을 인수로 받습니다. Tray 클래스도 Item 클래스의 추상 메소드 
makeHTML을 상속받지만 구현하지 않았습니다. 그래서 Tray 클래스는 추상 클래스가 됩니다. 
```java
public abstract class Tray extends Item {
    protected List<Item> tray = new ArrayList<>();

    public Tray(String caption) {
        super(caption);
    }

    public void add(Item item) {
        tray.add(item);
    }
}
```

### Page 클래스 
Page 클래스는 HTML 페이지 전체를 추상적으로 표현한 클래스입니다. Link나 Tray가 추상적인 '부품'이라면, Page 클래스는 추상적인
'제품'이라 할 수 있습니다. title은 페이지 제목, author는 페이지 작성자를 나타내는 필드입니다. 작성자 이름은 생성자에 인수로 지정합니다. 
페이지에는 add 메소드를 사용해 Item을 추가합니다. 추가한 항목이 이 페이지에서 표시됩니다. output 메소드 안에서는 제목을 바탕으로
파일명을 결정하고, makeHTML 메소드를 사용해 자신의 HTML 내용을 파일에 기록합니다. 파일에 기록할 때는 java.nio.file.Files 클래스의
writeString 메소드를 사용합니다. 
```java
public abstract class Page {
    protected String title;
    protected String author;
    protected List<Item> content = new ArrayList<>();

    public Page(String title, String author){
        this.title = title;
        this.author = author;
    }

    public void add(Item item){
        content.add(item);
    }

    public void output(String filename){
        try{
            Files.writeString(Path.of(filename), makeHTML(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
            System.out.println(filename + " 파일을 작성했습니다.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public abstract String makeHTML();
}
```

### Factory 클래스 
이상으로 추상적인 부품과 추상적인 제품의 소스 코드를 읽었습니다. 이번에는 드디어 추상적인 공장입니다. getFactory 메소드는 
클래스 이름을 문자열로 지정하여 구체적인 공장의 인스턴스를 작성합니다. 인수의 Classname에는 예를 들어 다음과 같이 작성할 구체적인
공장의 클래스 이름을 문자열로 지정합니다. 
getFactory 안에서는 Class 클래스의 forName 메소드를 사용하여 해당 클래스를 동적으로 갖져옵니다. 그리고 getDeclaredConstructor
메소드로 생성자를 얻고 newInstance 메소드로 인스턴스를 만듭니다. 그것이 getFactory의 반환값입니다. 

여기서는 클래스나 생성자와 같은 프로그램의 구성 요소를 프로그램 자신이 다루고 있습니다. 이러한 처리를 일반적으로 리플렉션(reflection)
이라고 합니다. getFactory 메소드에서는 구체적인 공장의 인스턴스를 만들지만, 반환값의 타입은 추상적인 공장(Factory)임에 주의해야합니다.


## Abstract Factory 패턴의 등장인물<hr>
### AbstractProduct( 추상적인 제품 )
AbstractFactory 역에 의해 만들어지는 추상적인 부품이나 제품의 인터페이스를 결정합니다.예제 프로그램에서는 Link클래스, Tray클래스,
Page클래스가 이역할을 맡았습니다. 
### AbstractFactory( 추상적인 공장 )
AbstractProduct 역의 인스턴스를 만들기 위한 인터페이스를 결정합니다. 예제프로그램에서는 Factory 클래스가
이 역할을 맡았습니다. 
### ConcreteProduct( 구체적인 제품 )
AbstractProduct 역의 인터페이스를 구현합니다. 예제 프로그램에서는 패키지마다 다음오가 같은 클래스가 이 역할을 맡았습니다.
- listfactory 패키지 - ListLink 클래스, ListTray 클래스, ListPage 클래스
- divfactory 패키지 - DivLink 클래스, DivTray 클래스, DivPage 클래스 

### ConcreteFactory ( 구체적인 공장 )
AbstractFactory 역의 인터페이스를 구현합니다. 예제 프로그램에서는 패키지마다 다음과 같은 클래스가 이 역할을 맡았습니다. 
- listfactory 패키지 - ListFactory 클래스
- divfactory 패키지 - DivFactory 클래스

## 사고 넓히기 <hr>
### 구체적인 공장을 새로 추가하는 것은 간단하다. 
AbstractFactory 패턴의 경우 구현체를 추가하는 것은 간단합니다. 이미 추상화된채로 Interface가 만들어져 있기 때문에,
해당 형식에 맞게 추가하면 되는 일이기 때문입니다. factory 패키지의 클래스가 가진 추상적인 부분을 구체화함으로써 간단하게 구현체를 
추가할 수 있습니다. 
### 다만, 새로운 부품을 추가하기는 어렵습니다. 
만약 새로운 부품을 추가하고자 한다면, interface 자체를 수정해야하고, 그렇게 되면 이를 구현하는 모든 구현체가 수정돼야 됩니다. 
그렇기 때문에 이미 만들어진 구체적인 공장이 많을수록 수정은 힘든 작업이 됩니다. 

## 관련패턴 <hr>
### Builder 패턴
Abstract Factory 패턴은 인터페이스가 정해져 있는 추상적인 부품을 조합해 복잡한 구조를 가진 인스턴스를 만듭니다. 
Builder 패턴은 단계적으로 큰 인스턴스를 만듭니다. 
### Factory Method 패턴
Abstract Factory 패턴으로 제품이나 부품을 만드는 부분은 Factory Method 패턴이 되는 경우가 있습니다. 
### Composite 패턴
Abstract Factory 패턴으로 만들어지는 제품은 Composite 패턴이 되는 경우가 있습니다. 
### Singleton 패턴
Abstract Factory 패턴의 구체적인 공장은 Singleton 패턴이 되는 경우가 있습니다.
