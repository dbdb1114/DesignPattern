## Visitor패턴<hr>
데이터 구조 안에 많은 요소가 저장되어 있고, 각 요소에 대해 어떠한 처리를 한다고 할 때 
그'처리' 코드는 어디에 써야 할까요? 일반적으로 생각하면 데이터 구조를 나타내는 클래스 안에 쓸 것입니다.
하지만 만약 그 '처리'가 한 종류가 아니라면 어떨까요? 이런 경우엔 새로운 처리가 필요할 때마다 데이터 구조의 
클래스를 수정해야 합니다. 

Visitor 패턴에서는 데이터 구조와 처리를 분리합니다. 데이터 구조 안을 돌아다니는 주체인 '방문자'를 나타내는
클래스를 준비하고 그 클래스에 처리를 맡깁니다. 새로운 처리를 추가하고 싶을 때는 새로운 '방문자'를 만들면 됩니다. 
데이터 구조 쪽에서는 문을 두드리는 '방문자'를 받아주면 됩니다. 

## 예제 프로그램 <hr>
Visitor 패턴의 예제 프로그램을 살펴봅시다. 방문자가 돌아다니는 데이터 구조로 Composite 패턴에서 등장한 
파일과 디렉터리의 예를 다시 사용합니다. 파일과 디렉터리로 구성된 데이터 구조 안을 방문자가 돌아다니며 파일 목록을 표시하는
프로그램을 만듭니다. 

### Visitor클래스
Visitor 클래스는 방문자를 나타내는 추상 클래스입니다. 이 방문자는 방문하는 곳의 데이터 구조에 의존합니다. 

Visitor 클래스에는 이름이 같은 visit이라는 메소드가 두 개 선언되어 있습니다. 
이 둘은 이름은 같지만 인수가 다릅니다. 한쪽은 file을, 한쪽은 Directory를 인수로 가집니다. 
visit(File)은 File 방문시 File 클래스가 호출하는 메소드이고, visit(Directory)는 Directory 방문 시
Directory 클래스가 호출하는 메소드입니다. Visitor 패턴은 클래스 간 상호 호출이 복잡하여, visitor클래스만
보아서는 이해되지 않습니다. 여기서는 Visitor 클래스가 두 개의 visit 메소드를 가지고 있다는 것만 이해하면 됩니다. 

```java
public abstract class Visitor {
    abstract void visit(File file);
    abstract void visit(Directory directory);
    
}
```

### Element 클래스
visitor 클래스는 '방문자'를 나타내는 클래스입니다. 반면에, Element 인터페이스는 방문자를 받아들이는
인터페이스입니다.

Element의 accept 메소드를 직접 구현하는 것은 File과 Directory 클래스입니다.
accpet 메소드는 visit(this) 방식으로 호출합니다. 이렇게 하는 이유는 visitor 클래스에 해당 인스턴스
자체를 공유하기 위함입니다. 

```java
public interface Element {
    void accept(Visitor v);
}

public abstract class Entry implements Element {
    public abstract String getName();
    public abstract int getSize();
    public abstract String getPath();
    public abstract void setPath(String path);
    public void printList(){
        printList("");
    }

    protected abstract void printList(String prefix);


    @Override
    public String toString() {
        return getName() + "(" + getSize() + ")";
    }
}

public class File extends Entry {
    private String path;
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    /**
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
    
    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}

public class Directory extends Entry implements Iterable<Entry> {
    private String name;
    private List<Entry> directory = new ArrayList<>();
    private String path="";
    
    /**
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Override
    public Iterator<Entry> iterator() {
        return directory.iterator();
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
}
```

### ListVisitor 클래스 
ListVisitor클래스는 visitor 클래스의 하위 클래스로, 데이터 구조를 돌아다니면서 목록을 표시합니다.
Visitor 클래스의 하위 클래스이므로 visit(File) 메소드와 visit(Directory)메소드를 구현했습니다.

currentdir 필드에는 현재 바라보고 있는 디렉토리의 이름을 저장합니다. visit(File) 메소드는 파일 방문시
File 클래스의 accept 메소드 안에서 호출됩니다. 인수 file은 방문한 File 클래스의 인스턴스입니다. 
즉, 이 visit(File) 메소드는 'File 클래스의 인스턴스에 해야 할 처리'를 기술하는 장소입니다. 여기서는
현재 디렉터리의 이름뒤에 슬래시로 구분하고 파일 이름을 표시합니다. 

하나의 객체를 이용하여 visitor의 print를 실행시킵니다. 포인트는 Directory의 iterable을 이용하여, 
해당 객체 내부에 있는 Entry 타입의 모든 것들을 accept를 실행시킵니다. 깊이에 따라서 출력이 끝날땐 
currentdir을 수정해줍니다. 이런 재귀적인 방식으로 가장 깊히 있는 파일이든 디렉토리든 모두 visit할 수 있습니다.

```java
public class ListVisitor extends Visitor {
    
    private String currentdir = "";
    
    @Override
    void visit(File file) {
        System.out.println(currentdir + "/" + file);
    }

    @Override
    void visit(Directory directory) {
        System.out.println(currentdir + "/" + directory);
        String savedir = currentdir;
        currentdir = currentdir + "/" + directory.getName();
        for (Entry entry : directory) {
            entry.accept(this);
        }
        currentdir = savedir;
    }
}
```

## Visitor 패턴의 등장인물<hr>
### Visitor 역
데이터 구조의 구체적인 요소마다 "xxxx를 방문했다"는 visit(XXXX) 메소드를 선언합니다. 
visit(XXXX)는 XXXX를 처리하기 위한 메소드로 실제 코드는 ConcreteVisitor 쪽에 기술됩니다.
예제 프로그램에서는 Visitor 클래스가 이 역할을 맡았습니다. 

### ConcreteVisitor 
Visitor의 인터페이스를 구현합니다. visit 형태의 메소드를 구현하고, 각 ConcreteElement마다 
처리를 기술합니다. 예제 프로그램에서는 ListVisitor 클래스가 이 역할을 맡았습니다. ListVisitor 에서
currentdir 필드 값이 변화한 것처럼 visit(XXXX)를 처리하는 중에 ConcreteVisitor 역의 내부 상태가
변화하기도 합니다. 

### Element 역
Visitor가 방문할 곳을 나타내며, 방문자를 받아들이는 accept 메소드를 선언합니다. accept 메소드의 인수로는
Visitor역이 전달됩니다. 예제 프로그램에서는 Element 인터페이스가 이 역할을 맡았습니다. 

### ConcreteElement
Element의 인터페이스를 구현합니다. 예제 프로그램에서는 File 클래스와 Directory 클래스가 이 역할을 맡았습니다. 

### ObjectStructure
Element 집합을 다룹니다. ConcreteVisitor가 각각의 Element를 취급할 수 있는 메소드를 갖추고 있습니다.
예제 프로그램에서는 Directory 클래스가 이 역할을 맡았습니다. ConcreteVisitor가 각각의 element를 
다룰 수 있도록 예제 프로그램의 Director 클래스에 iterator 메소드가 준비되어 있습니다. 

## 사고 넓히기<hr>
### 왜 visitor를 써야하는가?
Visitor 패턴의 목적은 처리를 데이터 구조와 분리하는 것입니다. 데이터 구조는 요소를 집합으로 정리하거나
요소 사이를 연결해 주는 중요한 역할을 합니다. 그러나, 구조를 유지하는 것과 그 구조를 기초로 한 처리를
기술하는 것은 다릅니다. 

일반적으로 이런 방식을 따르면 처리를 추가하고 싶을 때는 Visitor만 추가해주면 됩니다. 일반적으로 
ConcreteVisitor 역은 File 또는 Directory 클래스와 독립적으로 개발할 수 있습니다. 즉 Visitor 패턴은
File클래스나 Directory 클래스의 부품으로서의 독립성을 높여줍니다.
