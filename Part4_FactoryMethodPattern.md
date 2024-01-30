

## FactoryMethod 패턴
Template Method 패턴은 상위 클래스에서 처리의 뼈대를 만들고, 하위 클래스에서 구체적인 처리의 살을 붙였다면,
FactoryMethod 패턴은 인스턴스 생성 장면에 적용한 것입니다. FactoryMethod 패턴에서는 인스턴스 생성 방법을 상위 클래스에서 결정하되, 구체적인
클래스 이름까지는 결정하지 않습니다. 구체적인 살은 모두 하위 클래스에서 붙입니다.

## 예제 프로그래밍
**추상화된 상위 클래스 Create와 Product**

아래의 두 클래스는 각각 Creator와 Product라는 두 가지를 추상화한 클래스 입니다. 세부 내용을 보면
Factory 클래스에서는 Producr라는 객체를 만들어내는 것일뿐 정확히 Product가 어떤건지 알 수 없습니다.
또한 이외의 등록하고, 생성하는 과정 조차 정확히 알 수 없습니다.
```java
package part4_factory_method_pattern.framework;

public abstract class Factory {
    public final Product create(String owner) {
        Product p = createProduct(owner);
        registerProduct(p);
        return p;
    }
    protected abstract Product createProduct(String owner);

    protected abstract void registerProduct(Product product);

}


/***************************************************/


package part4_factory_method_pattern.framework;

public abstract class Product {
    public abstract void use();

}

```

**구체화된 클래스 ConcreteProduct와 ConcreteCreator**

두 클래스는 상위 클래스에 대해서 구체적으로 살을 붙이는 클래스입니다. 두 상위 클래스를 구현하여
정확히 프로덕트를 생성하는 것과 등록하는 것에 대한 구체적인 내용을 설명합니다.

```java
package part4_factory_method_pattern.id_card_factory;

import part4_factory_method_pattern.framework.Factory;
import part4_factory_method_pattern.framework.Product;

public class IDCardFactory extends Factory {


    @Override
    public IDCard createProduct(String owner) {
        return new IDCard(owner);
    }

    @Override
    public void registerProduct(Product product) {
        System.out.println(product + "을 등록했습니다.");
    }
}

/*******************************************************/

package part4_factory_method_pattern.id_card_factory;

        import part4_factory_method_pattern.framework.Product;

public class IDCard extends Product {
    private String owner;

    IDCard(String owner) {
        System.out.println(owner + "의 카드를 만듭니다.");
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println(this + "을 사용합니다.");
    }

    @Override
    public String toString() {
        return "[IDCard:" + owner + "]";
    }

    public String getOwner() {
        return owner;
    }

}

```

## TemplateMethod와 FactodyMethod패턴의 차이
이 내용은 필자가 직접 정리하면서 약간 헷갈리는 부분이어서 굳이 따로 글을 써봅니다. FactoryMethod 패턴은 인스턴스의 생성을 new 연산자를
사용하는 것이 아닌 다른 클래스에게 의존하는 것을 의미합니다. 그래서 이름도 Factory라고 붙은 것입니다. Factory라는 것 자체가 어떤 객체를
찍어내는 것이기 때문에 이런 이름이 생긴 것입니다. TemplateMehtod 패턴은 구현체의 전체적인 흐름 자체를 미리 설계해두는 것입니다. 따라서
추상화된 클래스나 인터페이스를 만들고 그것을 구현한다는 맥락 자체는 같지만, 개념이 출발한 근본이 다른 것입니다.

TemplateMethod Pattern : 객체의 메소드 사용이나 흐름을 미리 설계해두는 것. <br>
FactoryMethod Pattern : 인스턴스 생성을 다른 클래스에게 의존한다는 것.


## Factory Method 패턴의 등장인물
Factory Method 총 네 가지로 구성됩니다. 첫 째로 Creator와 Product를 만드는 추상화된 상위 클래스와 그 두 클래스를 각각 구현하는 구현체
ConcreteCreator, ConcreteProduct가 있습니다. 앞서 이야기 했듯 관계 정의가 1 : 1로 되어 다른 하나의 인스턴스를 생성하는 것에 책임을
지는 것이 FactoryMethod패턴입니다.

## 사고 넓히기

### 프레임워크와 구체적인 내용
예제를 보면 추상화된 **두 클래스를 프레임워크라고** 하고, 그들을 각각 사용하는 것을 **구체적인 내용이라고** 설명했습니다.
예제프로그램의 내용을 보면 구체적인 내용은 프레임워크에 의존하지만, 프레임워크는 구체적인 내용에 의존하지 않습니다. 그저 Factory라는 추상화된
클래스와 Product라는 추상화된 클래스로 서로의 이용과 관계에 대해서만 설계를 해준 것이고, 이를 구현하는 클래스는 각각의 Factory와 Product를
구현하면서 추상화된 두 클래스의 이용과 관계를 그대로 가져온 것일 뿐입니다. 그리고 그 안에 구체적인 내용들을 담아준 것이지요.

### 인스턴스 생성 - 메소드 구현 방법
Factory 메소드를 보면 createProduct를 할때 `return new Product(owner)` 방식을 사용하여 구현체를 만들어놓을 수 있었을 것 같습니다.
그렇게 하는 방법도 있고 이렇게 하는 방법도 있지만 굳이 `new Product(owner)`를 쓰지 않은 것은 이렇게 할 경우 Product 클래스를 해당 코드 내에서
일일이 구현하거나 추상클래스로 둘 수 없기 때문입니다. 추상화라는 개념이라고 생각하면 될 것 같습니다.
```java
package part4_factory_method_pattern.framework;

public abstract class Factory {
    public final Product create(String owner) {
        Product p = createProduct(owner);
        registerProduct(p);
        return p;
    }
    protected abstract Product createProduct(String owner);

    protected abstract void registerProduct(Product product);

}
``` 


## 관련 패턴
**Template Method 패턴**<br>
FactoryMethod 패턴은 Template Method 패턴의 전형적인 응용입니다. 예제 프로그램의 create 메소드가 템플릿 메소드로
되어 있습니다.

**Singleton 패턴**<br>
Creator역을 맡는 클래스는 대부분 Singleton 패턴으로 만들 수 있습니다. 프로그램 안에서 인스턴스가 여러 개 존재할 필요가 별로
없기 때문입니다. 단, 이 예제 프로그램은 Singleton패턴으로 되어있지 않습니다.

**Composite 패턴**<br>
Product역에 Composite 패턴을 적용할 수 있습니다.

**Iterator 패턴**
Iterator 패턴에서 iterator 메소드가 Iterator 인스턴스를 만들 때 Factory Method 패턴을 사용할 수 있습니다. 


