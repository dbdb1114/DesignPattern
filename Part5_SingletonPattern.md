
## Singleton 패턴이란
프로그램을 실행하면 보통은 많은 인스턴스가 생성됩니다. 하지만 경우에 따라서는 인스턴스를 하나만 만들고 싶을 때도 있습니다.
바로 시스템 안에 1개만 존재한다는 것을 프로그램으로 표현하고 싶을 때입니다. 싱글톤 패턴은 지정한 클래스의 인스턴스가
반드시 1개만 존재한다는 것을 보증하고 싶을 때, 인스턴스가 하나만 존재한다는 것을 프로그램 상에서 표현하고 싶을 때 사용합니다.
이처럼 인스턴스가 하나만 존재하는 것을 보증하는 패턴을 Singleton 패턴이라고 부릅니다. singleton이란 요소가 하나뿐인
집합을 말합니다.

## 예제 프로그램
하나만 생성하고자 하는 인스턴스를 `private static`으로 선언해줍니다. 그리고 생성자 또한 `private`으로 선언해줍니다. `new`연산자를
사용하여 인스턴스를 생성할 수 없게 만드는 것입니다. 이를 통해 해당 인스턴스를 생성할 수 있는 방법을 모두 방지합니다. 마지막은 `public static`
키워드를 사용하여 해당 인스턴스를 꺼낼 수 있는 유일한 메소드를 만들어줍니다.
```java
package part5_singleton_pattern;

public class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton(){
        System.out.println("인스턴스를 생성했습니다.");
    }
    public static Singleton getInstance() {
        return singleton;
    }
}
```

## 사고 넓히기
굳이 다른 인스턴스랑 구별하여 사용할 필요 없을 때 사용하기 좋은 패턴입니다. 예를 들어 문자열을 다루는 StringUtil클래스가 있다고 했을 때
아마 해당 클래스는 인수로 받는 문자열을 자르고 합치는 정도의 기능을 가진 메소드만 가질 것입니다. 이럴때는 굳이 이 클래스를 여러개 생성하지 않고
하나의 인스턴스를 만들어 재사용 하는 것이 좋습니다. 
