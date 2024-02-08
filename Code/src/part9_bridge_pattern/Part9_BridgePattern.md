## Bridge패턴 <hr>
이 장에서는 Bridge 패턴에 대해 학습합니다. bridge란 다리라는 뜻입니다. 현실세계의 다리가 강 양쪽을 연결하는 역할을 하는 것처럼
Bridge 패턴도 두 장소를 연결하는 역할을 합니다. Bridge 패턴이 다리역할을 하는 장소는 '기능의 클래스 계층'과 '구현의 클래스 계층'입니다.
- 기능의 클래스 계층
- 구현의 클래스 계층

### 클래스 계층의 두 가지 역할
**새로운 기능을 추가하고 싶을 때**<br>
어떤 클래스 something이 있다고 가정햇을 때, something에 새로운 기능을 추가하고 싶을 때, 우리는 Something의 하위 클래스, 파생 클래스
,확장 클래스로 someghingGood 클래스를 만듭니다. 이 계층은 기능을 추가하기 위해 만들어졌습니다. 
- 상위 클래스는 기본적인 기능을 가지고 있다.
- 하위 클래스에서 새로운 기능을 추가한다.

이 클래스 계층을 기능의 클래스 계층이라 부르기로 합니다. somethingGood 클래스에 새로운 기능을 추가한다고 합시다. 이 경우 SomethingGood 
클래스의 하위 클래스로 SomethingBetter 클래스를 만듭니다. 이로써 기능의 클래스 계층이 한층 더 깊어졌습니다. 
새로운 기능을 추가하고 싶을 때, 클래스 계층 안에서 자신의 목적과 가까운 클래스를 찾아 그 하우 ㅣ클래스를 만들고, 원하는 기능을 추가한 새로운
클래스를 만듭니다. 이것이 기능의 클래스 계층입니다. 

**새로운 '구현'을 추가하고 싶을 때**<br>
Templete Method 패턴에서 우리는 추상 클래스의 역할에 대해 배웠습니다. 추상 클래스는 일련의 메소드를 추상 메소드로 선언하고 인터페이스를
규정합니다. 그리고 하위 클래스 쪽에서 그 추상 메소드를 실제로 구현합니다. 상위 클래스는 추상 메소드로 인터페이스를 규정하는 역항르 하고, 하위
클래스는 추상 메소드를 구현하는 역하을 합니다. 이러한 상위 클래스와 하위 클래스의 역할 분담을 통해 부품으로서의 가치가 높은 클래스를 만들 수 
있습니다. 

예를 들어 상위 클래스 AbstractClass의 추상 메소드를 구현한 하위 클래스를 ConcreteClass라고 작은 클래스 계층이 만들어집니다.
하지만 여기에서 사용되는 클래스 계층은 기능을 추가하기 위해 사용되는 것은 아니며, 새로운 메소드를 늘리기 위해 클래스 계층을 만드는 것도 아닙니다.
여기서는 다음과 같은 역할 분ㅇ담을 위해 클래스 계층이 사용됩니다. 
- 상위 클래스는 추상 메소드로 인터페이스를 규정한다. 
- 하위 클래스는 구상 메소드로 그 인터페이스를 구현한다. 

이 클래스 계층을 '구현의 클래스 계층'으로 부르기로 합니다. 


## 예제 프로그램 <hr>
open(), print(), close() 세 메소드는 Display 클래스에서 제공하는 인터페이스이고, 표시를 실행하는 절차를 나타냅니다.
open은 표시의 전처리, Printsms 표시 그 자체, close는 표시의 후처리를 나타냅니다. 
```java
public class Display {

    private DisplayImpl impl;

    public Display(DisplayImpl impl){
        this.impl = impl;
    }

    public void open() {
        impl.rawOpen();
    }

    public void print() {
        impl.rawPrint();
    }

    public void close() {
        impl.rawClose();
    }

    public final void display() {
        open();
        print();
        close();
    }
}
```
```java
package part9_bridge_pattern;

public abstract class DisplayImpl {

    public abstract void rawOpen();
    public abstract void rawPrint();
    public abstract void rawClose();
}
```
기능의 클래스 계층 : CountDisplay<br>
CountyDisplay는 Display를 여러번 실행하는 기능을 가진 클래스입니다. 기존의 Display 클래스에는 '표시하는'기능밖에 없었지만, CountDispLay
클래스에는 '지정 횟수만큼 표시하는' 기능을 추가했습니다. 바로 multiDisplay 메소드입니다. 
```java
public class CountDisplay extends Display{
    public CountDisplay(DisplayImpl impl){
        super(impl);
    }
    public void multiDisplay(int times){
        open();
        for (int i = 0; i < times; i++) {
            print();
        }
        close();
    }
}
```
구현 클래스 계층 : DisplayImpl 클래스 <br>
DisplayImpl 클래스는 추상 클래스로 rawOpen, rawPrint, rawClose라는 세 가지 메소드를 가지고 있습니다. 이것은 Display 클래스의
open, print, close에 각 대응하며, 전처리, 표시, 후처리를 실행합니다. 
```java
public abstract class DisplayImpl {

    public abstract void rawOpen();
    public abstract void rawPrint();
    public abstract void rawClose();
}
```
구현 클래스계층 : StringDisplayImpl 클래스 <br>
진정한 '구현'입니다. StringDisplayImpl 클래스는 문자열을 표시하는 클래스입니다. 다만, 그냥 표시하는 것이 아니라, DisplayImpl
클래스의 하위 클래스로서 rawOpen, rawPrint, rawClose라는 메소드를 사용하여 표시합니다. 
```java
public class StringDisplayImpl extends DisplayImpl{
    private String string;
    private int width;

    public StringDisplayImpl(String string) {
        this.string = string;
        this.width = string.length();
    }

    @Override
    public void rawOpen() {
        printLine();
    }

    @Override
    public void rawPrint() {
        System.out.println("|" + string + "|");
    }

    @Override
    public void rawClose() {
        printLine();
    }

    private void printLine(){
        System.out.print("+");
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}
```

## Bridge 패턴의 등장인물 
### Abstraction 역 
'기능의 클래스 계층'의 최상위 클래스입니다. Implementor 역의 메소드를 사용하여 기본 기능만 기술된 클래스입니다. 이 인스턴스는
Implementor 역을 가집니다. 예제 프로그램에서는 Display 클래스가 이 역할을 맡았습니다. 
### RefinedAbstraction(개선된 추상화) 역
Abstraction 역에 기능을 추가했습니다. 예제 프로그램에서는 CountDisplay 클래스가 이 역할을 맡았습니다. 
### Implementor 역
'구현의 클래스 계층'의 최상위 클래스입니다. Abstraction 역의 인터페이스를 구현하기 위한 메소드를 규정하는 역할입니다. 예제 프로그램에서는
DisplayImpl 클래스가 이 역할을 맡았습니다.
### ConcreteImplementor 역
구체적으로 Implementor 역의 인터페이스를 구현합니다. 예제 프로그램에서는 StringDisplayImplf클래스가 이 역할을 맡았습니다. 

## 사고넓히기<br>
### 분리해 두면 확장이 편해진다. 
Bridge 패턴의 특징은 '기능의 클래스 계층'과 '구현의 클래스 계층'을 분리하는 것입니다. 이 두 개의 클래스 계층을 분리해 두면 각각의 
클래스 계층을 독립적으로 확장할 수 있습니다. 
기능을 추가하고 싶으면 기능의 클래스 계층에 클래스를 추가합니다. 이때 구현의 클래스 계층은 전혀 수정할 필요가 없습니다. 게다가 새로 추가한
기능은 '모든 구현'에서 이용할 수 있게 됩니다. 

### 상속은 강한 결합이고 위임은 약한 결합이다. 
'상속'은 클래스를 확장하는 편리한 방법이지만, 클래스 간의 연결을 강하게 고정시킵니다. 다음과 같이 소스 코드를 작성하면, somethingGood 클래스는
Something 클래스의 하위 클래스가 됩니다. 이 관계는 소스 코드를 다시 쓰지 않는 한 바꿀 수 없습니다. 소스 코드를 다시 쓰지 않는 한 바꿀 수
없다는 것은 매우 강하게 결합된다는 뜻입니다. 필요에 따라 클래스 간의 관계를 척척 전환하고자 할 때 상속을 사용하는 것은 부적절합니다. 전환할 때마다
소스 코드를 변경하고 있을 수는 없으니까요. 이런 때는 '상속'이 아니라 '위임'을 사용합니다. 

여기서 말하는 위임이 Display 클래스에서 DisplayImpl 구현체를 가져와 모든 일을 처리하는 것을 말합니다. 이렇게 필요한 인스턴스를 가져와서 
사용한다면, impl에게 역할을 위임시키고 결합도는 떨어뜨릴 수 있습니다. 


## 관련패턴 <hr>
**Template Method 패턴<br>**
Template Method 패턴에서는 구현의 클래스 계층을 이용합니다. 상위 클래스에서는 추상 메소드를 사용해 프로그래밍하고, 하위 클래스에서는
그 추상 메소드를 구현합니다.

**Abstract Factory 패턴<br>**
Bridge 패턴에 등장하는 ConcreteImplementor 역을 환경에 맞추어 적절히 구축하기 위해 AbstractFactory 패턴이 이용되는 경우가 
있씁니다. 

**Adapter 패턴<br>**
Bridge 패턴은 기능의 클래스 계층과 구현의 클래스 계층을 확실히 분리한 다음 결합하는 패턴입니다. Adapter 패턴은 기능은 비슷하지만,
인터페이스는 다른 클래스끼리 결합하는 패턴입니다. 
