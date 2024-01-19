## AdapterPattern (WrapperPattern)
Adapter는 이미 제공된 것을 다른 형태로 변형하여 사용하는 것을 말합니다. 따라서 이 패턴은 WrapperPattern
이라고 하기도 합니다. 일반 상품을 선물할 때 선물포장을 따로 하는 것과 맥이 같습니다. 이런 AdapterPattern은 
두 종류가 있습니다. 
- 클래스에 의한 Adapter 패턴 (상속을 사용한 패턴)
- 인스턴스에 의한 Adapter 패턴 (위임을 사용한 패턴)

## EXProgram1 ( 상속을 사용한 어댑터 패턴 )
주어진 문자열에 대해 다음과 같이 출력시켜야함.  

```java
(Helllo)
*Hello*
```
Banner 클래스는 문자열을 괄호로 묶어서 표시하는 showWithParen 메소드와 문자열 앞,뒤에 *를 붙여서 표시하는
showWithAster 메소드가 있습니다. 이 Banner클래스를 교류 100볼트 처럼 "이미 제공된 것"이라고 가정합니다.

Print 인터페이스는 문자열을 괄호로 묶어 약하게 표시하는 메소드 printWeak와 문자열을 *로 강조해서 표시하는 메소드
printStrong이 선언되어 있습니다. 이 인터페이스를 직류 12볼트 처럼 "필요한 것"이라고 가정합니다. 

지금 하고 싶은 일은 Banner 클래스를 사용하여 Print 인터페이스를 충족하는 클래스를 만드는 것입니다. 즉, 교류 100볼트
를 직류 12볼트로 변환해 주는 어댑터를 만들고 싶습니다. 

어댑터 역할을 담당하는 것이 PrintBanner 클래스입니다. 이 클래스는 제공된 Banner 클래스를 상속받아, 필요한
Print 인터페이스를 구현합니다. PrintBanner 클래스는 showWithParen메소드로 printWeak를 구현하고
showWithAster 메소드로 printStrong을 구현합니다. 

### BannerClass
```java
public class Banner {
    private String string;

    public Banner(String string) {
        this.string = string;
    }

    public void showWithParen() {
        System.out.println("(" + string + ")");
    }

    public void showWithAster() {
        System.out.println("*" + string + "*");
    }
}
```

### PrintInterace
```java
public interface Print {
    void printWeak();
    void printStrong();
}
```

### PrintBanner클래스 
```java
public class PrintBanner extends Banner implements Print{
    public PrintBanner(String string) {
        super(string);
    }

    @Override
    public void printWeak() {
        showWithParen();
    }

    @Override
    public void printStrong() {
        showWithAster();
    }
}
```

### Main
```java
public class BannerMain {
    public static void main(String[] args) {
        Print p = new PrintBanner("Hello");
        p.printStrong();
        p.printWeak();
    }
}
```
### EXprogram1 정리 
Main 클래스에서는 PrintBanner의 인스턴스를 Print 인터페이스형 변수에 대입하는 것에 주의합니다. Main 클래스는 
Print 인터페이스를 사용해서 프로그래밍하고 있습니다. Banner 클래스나 그 내부 메소드는 Main클래스에서는 완전히 숨겨져


## EXProgram2 ( 위임을 이용한 패턴 )
Main 클래스와 Banner 클래스는 기존 그대로 두고, Print 인터페이스만 클래스로 변경합니다. 
즉, Banner 클래스를 이용하여 Print클래스와 같은 메소드를 갖는 클래스를 실현하려는 것입니다.

즉 PrintBanner 클래스는 Print 클래스만을 상속받고, Banner 인스턴스를 필드로 두어 Banner 인스턴스를
활용해서 기존 상속받아서 사용했던 Banner 메소드를 대신하는 것입니다. 

### Print 클래스
```java
public abstract class Print {
    public abstract void printWeak();
    public abstract void printStrong();
}
```

### PrintBanner 클래스
```java
public class PrintBanner extends Print{
    Banner banner;

    public PrintBanner(String string) {
        this.banner = new Banner(string);
    }

    @Override
    public void printWeak() {
        banner.showWithParen();
    }

    @Override
    public void printStrong() {
        banner.showWithAster();
    }
}
```

## Adapter 패턴의 등장인물 
**- Target** <br>
지금 필요한 메소드를 결정합니다. 노트북을 작동시키는 직류 12볼트에 해당합니다. 예제 프로그램에서 Print 인터페이스와
Print 클래스가 이 역할을 맡았습니다. 

**- Client** <br>
Target의 메소드를 사용해 일합니다. 직류 12볼트로 작동하는 노트북 입니다. 예제 프로그램의 Main 클래스가 해당합니다.

**- Adaptee** <br>
예제 프로그램의 Banner 클래스가 이 역할을 합니다. 실제 필요한 것의 원천 제공자라고 할 수 있죠.

**- Adapter** <br>
PrintBanner 클래스가 해당합니다. 필요한 곳과 필요한 곳을 연결해주는 것이죠. PrintBanner 클래스는 
Print클래스나 인터페이스를 상속받거나 구현해서, Print 타입의 객체를 만들어 그들만의 메소드를 이용해 원하는 기능을
만들었습니다. 이 때 원하는 기능은 PrintBanner가 만든것도 아니고, Print에서 온 것도 아니고 Banner 클래스에서
상속받거나 인스턴스를 가져와 만든 것이었죠. 이것이 Adapter의 역할입니다.


## 사고 넓히기
### 언제 사용할까? 
어댑터 패턴의 기존에 만들어진 메소드를 그대로 가져와서 사용하고 싶을때 적용합니다. 당연히 기존에 잘 사용하고 이미 
검증된 것들이 있다면, 재사용하는 것이 리소스를 낭비하지 않는 일이기 때문에 그렇습니다. 

또한 이미 만들어진 소스 내에서 새로이 기능을 추가할 때 기존 클래스를 수정하지 않고 목적한 인터페이스에 맞추려는 것입니다.
기존 소스코드를 수정하게 되면 테스트를 다시 진행해야하므로, 이런 방식으로 하는 것이 신속하고 안전하다고 할 수 있습니다. 

### 버전 업과 호환성
소프트웨어를 버전 업할 때는 구버전과 호환성 문제가 일어납니다. Adapter 패턴은 신버전과 구버전을 공존시키고, 유지보수
까지 편하게 하도록 도와줍니다. 예를 들어 신버전을 Adaptee 역으로 하고, 구버전을 Target역으로 하여 신버전의 클래스를
사용하여 구버전의 메소드를 구현하는 Adapter 역할 클래스를 만듭니다. 

### 상속과 위임 중 어느 쪽을 사용해야 할까?
상속과 위임을 사용한 두 가지 예제가 있었는데 위임을 사용하는 것이 안전합니다. 상속받을 부모 클래스의 내부 동작을 자세히
알고있다면 괜찮지만, 그렇지 않다면 효과적으로 사용하기 어렵기 때문입니다. 

## 관련 패턴
Broidge 패턴 ( part9 )
Adapter 패턴은 인터페이스가 서로 다른 클래스를 연결하는 패턴입니다. Bridge 패턴은 기능 계층과 구현 계층을 
연결하는 패턴입니다. 

Decorator 패턴 ( part12 )
Adaper 패턴은 인터페이스의 차이를 메우는 패턴입니다. Decorator 패턴은 인터페이스를 변경하지 않고 기능을
추가하는 패턴입니다. 

