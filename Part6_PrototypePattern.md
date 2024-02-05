## Prototype 패턴 <hr>
특정 클래스의 인스턴스를 만들고자 할 때 보통 new 연산자를 사용하여 `new something()`과 같이 
만듭니다. 이처럼 new를 사용해서 인스턴스를 만들 때는 클래스 이름을 반드시 지정해야만 합니다. 그러나 
클래스 이름을 지정하지 않고 인스턴스를 생성하고 싶을 때도 있습니다. 대략 아래와 같은 경우에는 
클래스로부터 인스턴스를 만드는 대신 인스턴스를 복사해서 새 인스턴스를 만듭니다. 


1. **취급할 오브젝트 종류가 너무 많아서, 하나하나 다른 클래스로 만들면 소스 파일을 많이 작성해야하는 경우**
2. **클래스로부터 인스턴스 생성이 어려운 경우** <br>
   인스턴스가 복잡한 과정을 거쳐 만들어지는 것으로, 클래스로부터 만들기가 매우 어려운 경우입니다. 예를 들어, 그래픽
   에디터 등에서 사용자가 마우스로 그린 도형을 나타내는 인스턴스가 있을 때 이렇게 사용자 조작으로 만들어지는 인스턴스를 프로그래밍해서
   만들기는 어렵습니다. 사용자 조작으로 만들어진 인스턴스와 같은 것을 다시 만들고 싶은 경우에는 지금 만든 인스턴스를 일단
   저장해두고, 만들고 싶을 때 그것을 복사합니다.
3. **프레임워크와 생성하는 인스턴스를 분리하고 싶은 경우**<br> 
   인스턴스를 생성하는 프레임워크를 특정 클래스에 의존하지 않게 하고 싶은 경우입니다. 이러한 경우에는 클래스 이름을 지정해서 인스턴스를 만드는 것이 아니라, 미리 '원형'
   이 될 인스턴스를 등록해두고, 등록된 인스턴스를 복사해서 인스턴스를 생성합니다.

인스턴스로부터 다른 인스턴스를 생성하는 것은 복사기로 문서를 복사하는 것과 비슷합니다. 원본 서류를 어떻게 만들었는지 모르더라도 복사기에 넣으면 
같은 서류를 몇 장이든 만들 수 있습니다. 이 장에서는 클래스에서 인스턴스를 생성하는 대신 인스턴스로부터 다른 인스턴스를 생성하는 Prototype패턴에
관하여 학습하겠습니다. prototype은 '원형', '모범'이라는 뜻으로 Prototype패턴은 모범이 되는 인스턴스를 바탕으로 새로운 인스턴스를
만드는 패턴입니다.


## 예제 프로그램 <hr>
### framework 패키지
clone() 메소드 사용을 위해 Cloneable을 상속받는 Product 클래스를 만들고, 인스턴스를 관리할 수 있는 Manager클래스
입니다.
```java
package part6_prototype_pattern.framework;

public interface Product extends Cloneable{
    void use(String s);
    Product createCopy();
}
```
```java
package part6_prototype_pattern.framework;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private Map<String, Product> showcase = new HashMap<>();

    public void register(String name, Product prototype){
        showcase.put(name, prototype);
    }
    public Product create(String prototypeName){
        Product p = showcase.get(prototypeName);
        return p.createCopy();
    }
}
```

### 구현체들
Product 클래스를 구현한 두 구현체 입니다. 해당 클래스의 인스턴스들을 Manager클래스에서 관리해줍니다.
```java
package part6_prototype_pattern;

import part6_prototype_pattern.framework.Product;

public class MessageBox implements Product {
    private String decochar;

    public MessageBox(String decochar) {
        this.decochar = decochar;
    }

    @Override
    public void use(String s) {
        int decolen = 1 + s.length() + 1;
        for (int i = 0; i < decolen; i++) {
            System.out.print(decochar);
        }
        System.out.println();
        System.out.println(decochar + s + decochar);
        for (int i = 0; i < decolen; i++) {
            System.out.print(decochar);
        }
        System.out.println();
    }

    @Override
    public Product createCopy() {
        Product p = null;
        try {
            p = (Product) clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}
```

```java
package part6_prototype_pattern;

import part6_prototype_pattern.framework.Product;

public class UnderlinePen implements Product {
    private String ulchar;

    public UnderlinePen(String ulchar) {
        this.ulchar = ulchar;
    }

    @Override
    public void use(String s) {
        int ulen = s.length();
        System.out.println(s);
        for (int i = 0; i < ulen; i++) {
            System.out.print(ulchar);
        }
        System.out.println();
    }

    @Override
    public Product createCopy() {
        Product p = null;
        try {
            p = (Product) clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}
```
## 사고 넓히기<hr>
예제 프로그램에서는 3개의 원형이 등장했습니다. 이 예는 간단한 프로그램이라서 원형이 세 개뿐이지만, 마음먹기에 따라 얼마든지 
많은 종류의 원형을 만들 수 있습니다. 그러나 그 원현을 모두 개별 클래스로 만들어 버리면, 클래스 수가 너무 많아져서 소스
프로그램을 관리하기 어렵습니다.

### 클래스 이름은 속박인가
객체지향 프로그래밍의 목표 중 하나가 '부품으로서의 재사용'이라는 점을 다시 한 번 상기시킬 필요가 있습니다. 소스 코드 
안에 이용할 클래스 이름을 쓰는 것이 항상 나쁜 것만은 아닙니다. 그러나 소스 코드 안에 이용할 클래스 이름이 쓰여 있으면,
그 클래스와 분리해서 재사용할 수 없게 됩니다. 

물론 코드 자체를 수정할 수 있찌만, '부품으로서의 재사용'에서는 소스 코드를 수정하는 것은 고려하지 않습니다. java로 말하면
클래스 파일만 있어도 그 클래스를 재사용할 수 있는지가 중요합니다. 다시 말해 소스 파일이 없어도 재사용할 수 있는냐가 포인트입니다.

### 객체지향이 잘 지켜진 코드란?
객체지향이 잘 지켜진 코드는 해당 프로그램에서 확장을 하게될때 기존 코드를 수정할 필요 없는 코드라고 할 수 있습니다. 
그래서 스스로가 객체지향적으로 코드를 잘 작성했는지 확인하려면 이런 관점으로 스스로의 코드를 확인해보면 좋습니다.


## 관련패턴 <hr>
### Flyweight 패턴 
Prototype 패턴에서는 현재 인스턴스와 동일한 상태의 별도의 인스턴스를 만들어 이용합니다. Flyweight 패턴에서는 
하나의 인스턴스를 여러 장소에서 공유하여 사용합니다. 

### Memento 패턴
Prototype 패턴에서는 현재 인스턴스와 동일한 상태의 별도의 인스턴스를 만듭니다. Memento 패턴에서는 스냅샷과 undo를 
실행하기 위해 현재 인스턴스 상태를 저장합니다. 

### Composite 패턴 및 Decorator 패턴
Coposite 패턴이나 Decorator 패턴을 많이 사용할 때 복잡한 구조의 인스턴스가 동적으로 만들어지는 경우가 있습니다.
이런 때 Prototype 패턴을 사용하면 편리합니다. 

### Command 패턴
Command 패턴에 등장하는 명령을 복제하고자 하는 경우에 Prototype 패턴이 사용될 수 있습니다.
