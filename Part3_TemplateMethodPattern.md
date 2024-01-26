## Template Method 패턴 
### Template이란?
템플릿이란 문자를 잘 쓸 수 있도록 도와주는 판입니다. 종이 위에 두고 쓰면 더 잘 쓰게되는 원리입니다. 
싸인펜을 쓸지, 연필을 쓸지, 볼펜을 쓸지에 따라서 달라지고 다양해집니다.

### TemplateMethod 패턴이란
상위 클래스에 템플릿이 될 메소드가 정의되어 있고, 그 메소드 정의에 추상 메소드가 사용됩니다. 따라서 상위 클래스의 코드만 봐서는 
어떤 처리가 일어나는지 알 수 없습니다. 
추상 메소드를 실제로 구현하는 것은 하위 클래스입니다. 하위 클래스에서 메소드를 구현하면 구체적인 처리 방식이 정해집니다. 
다른 하위 클래스에서 구현을 다르게 하면, 처리도 다르게 이루어집니다. 그러나 어느 하위 클래스에서 어떻게 구현하더라도
처리의 큰 흐름은 상위 클래스에서 구성한 대로 됩니다. 
이처럼  **상위 클래스에서 처리의 뼈대를 결정하고 하위 클래스에서 그 구체적인 내용을 결정하는 디자인 패턴을 TemplateMethod패턴이라고
부릅니다.**

### 예제 프로그램 ( 문자나 문자열을 5번 반복해서 표시하기 )

**상위클래스**
```java
package part3_template_method_pattern;

public abstract class AbstractDisplay {

    public abstract void open();
    public abstract void print();
    public abstract void close();

    public final void display(){
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }
}
```
display 메소드만 구현되어 있으며, 실제로 open, print, close를 부르기만 하고있다.

**하위클래스**

두 클래스 모두 AbstractDisplay의 구현체로서 각 클래스에 맞는 내용을 override하여 사용한다.
실제로 상속받고 있기 때문에 display를 즉시 사용할 수 있으며, 자바의 상속원리를 생각해보면 특정 클래스의
메소드를 실행시킬 때 부모클래스보다 자식 클래스에서 먼저 해당 메소드를 찾고, override 된 것을 가장
우선순위에 두기 때문에 사용할 수 있는 구현 원리이다.

```java
package part3_template_method_pattern;

public class CharDisplay extends AbstractDisplay{

    char printChar;
    public CharDisplay(char ch) {
        printChar = ch;
    }

    @Override
    public void open() {
        System.out.print("<< ");
    }

    @Override
    public void print() {
        System.out.print(printChar);
    }

    @Override
    public void close() {
        System.out.println(" >>");
    }
}

/****************************************************/
/****************************************************/
/****************************************************/

public class StringDisplay extends AbstractDisplay{
    private String printString;
    private int width;

    public StringDisplay(String printString) {
        this.printString = printString;
        this.width = printString.length();
    }

    @Override
    public void open() {
        printLine();
    }

    @Override
    public void print() {
        System.out.println("|" + this.printString + "|");
    }

    @Override
    public void close() {
        printLine();
    }

    public void printLine(){
        System.out.print("+");
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}

```


### 사고를 넓히자

**로직의 공통화**

이 원리를 사용할 경우 추상클래스가 설계자의 역할을 제대로 할 수 있게 됩니다.
보통 추상화는 설계의 역할을 하기도 하는데 이렇게 구현될 경우 우리가 비즈니스 로직을 구현할 때 
가장 일반화되는 로직만 추려내고, 구체화되는 부분들은 각기 다르게 구현하여 개발을 할 수 있습니다.
**이로써 두 가지의 이점을 취할 수 있습니다.** 
1. 반복 코드 제거
2. 논리 흐름의 설계

**LSP ( 리스코프 치환 원칙 )**

SOLID 원칙 중 하나인 리스코프 치환 원칙을 준수할 수 있게 해줍니다. 리스코프 치환 원칙이란 
부모클래스에 자식 클래스를 대입해도 동작할 수 있게 하는 원칙을 말합니다. 이런 방식을 사용함으로써 
instanceof 연산자 등을 활용해 프로그램이 동작하도록 하는 것입니다.


### 관련 패턴
**Factory Method 패턴**

Template Method패턴을 인스턴스 생성에 응용한 전형적인 예시입니다.

**Strategy 패턴**
Template Method패턴에서는 '상속'을 이용하여 프로그램 동작을 변경할 수 있습니다. 상위 클래스에서
프로그램 동작의 큰 틀을 결정하고 하위 클래스에서 구체적인 행동을 규정하기 때문입니다. 반면 
Strategy 패턴에서는 '위임'을 이용하여 프로그램의 동작을 변경할 수 있습니다. Strategy 패턴에서는
프로그램 일부를 변경하기보다는 알고리즘 전체를 모두 전환합니다.
