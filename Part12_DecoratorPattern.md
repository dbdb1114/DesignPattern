## Decorator 패턴<hr>
스펀지 케이크가 하나 있다고 가정한다. 크림을 바르면 아무것도 얹혀 있지 않은 크림 케이크가 완성됩니다. 
거기에 딸기를 얹으면 딸기 크림 케이크가 됩니다. 거기에 납작한 초콜릿을 올리고 화이트 초콜릿으로 이름을 쓰고
나이 수만큼 양초를 세우면 생일 케이크가 완성됩니다. 

스펀지 케이크, 크림 케이크, 딸기 크림 케이크, 생일 케이크 모두 그 중심에 있는 것은 같은 스펀지 케이크입니다. 
하지만, 크림을 바르고 딸기를 올리는 등 여러 가지 장식을 하면 더 맛있고 각각의 목적에 맞는 케이크가 됩니다. 

객체도 이런 케이크와 비슷한 점이 있습니다. 먼저 중심이 되는 스펀지 케이크와 같은 객체가 있고, 거기에
장식이 되는 기능을 하나씩 추가해서 목적에 더 맞는 객체로 만들어 가는 것입니다. 

이처럼 객체에 점점 장식을 더해 가는 디자인 패턴을 Decorator 패턴이라고 부릅니다. decorator란 'decorate하는 
사람' 이라는 뜻입니다.

## 예제 프로그램 
여기서 만들 예제 프로그램은 문자열 주위에 장식틀을 붙여 표시하는 것입니다. 여기서 말하는 장식틀이란 -,+,!라는 
문자로 그린 것을 말합니다. 

### Display 클래스
Display클래스는 여러 행으로 이루어진 문자열을 표시하는 추상 클래스입니다.
getColumns와 getRows는 각각 가로 문자수와 세로 행수를 가져오는 추상 메소드로,
하위 클래스에서 구현해야 합니다.(subclass responsibility) getRowText는 
지정한 행의 문자열을 가져오는 메소드입니다. 이것도 추상 메소드로 하위 클래스에서 구현해야 합니다.

show는 모든 행을 표시하는 메소드입니다. 이 메소드에서는 getRows 메소드로 행수를 가져오고
getRowText 메소드로 표시할 문자열을 가져와서 for 루프를 사용하여 모든 행을 표시합니다.
show는 getRows와 getRow-Text라는 추상 메소드를
```java
public abstract class Display {
    public abstract int getColumns();
    public abstract int getRows();
    public abstract String getRowText(int row);
    
    public void show() {
        for (int i = 0; i < getRows(); i++) {
            System.out.println(getRowText(i));
        }
    }
    
}
```

### StringDisplay 클래스
Display 클래스만 봐서는 이해하기 어려우므로, 하위 클래스인 StringDisplay 클래스를 살펴보겠습니다.
케이크 이야기에 비유하자면, StringDisplay 클래스는 생일 케이크의 중심에 있는 스펀지 케이크에 해당합니다. 

StringDisplay클래스는 문자열을 한 줄 표시합니다. StringDisplay클래스는 Display 클래스의
하위 클래스로, Display 클래스에서 선언된 추상 메소드를 구현할 책임이 있습니다. 

string 필드는 표시할 문자열을 보관합니다. StringDisplay 클래스에서 표시하는 것은 string 필드의 내용
 한 줄 뿐이므로 getColumns는 string.length()로 얻을 수 있는 문자열 길이를 반환하고, getRows는
1을 반환합니다.
```java
public class StringDisplay extends Display{
    private String string;

    public StringDisplay(String string) {
        this.string = string;
    }

    @Override
    public int getColumns() {
        return string.length();
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public String getRowText(int row) {
        if(row!=0){
            throw new IndexOutOfBoundsException();
        }
        return string;
    }
}
```

### Border 클래스 
Border 클래스는 '장식틀'을 나타내는 추상 클래스로, 문자열을 표시하는 Display 클래스의
하위 클래스로 정의되어 있습니다. 즉, 상속에 의해 장식틀은 내용물과 동일한 메소드를 갖게 됩니다.
구체적으로 말하면 Border 클래스는 getColumns,getRows,getRowText,show 메소드를 상속받습니다. 
장식틀(Bor-der)이 내용물(Display)과 같은 메소드를 갖는다는 것은 인터페이스(API)관점에서 보면 장식틀과 
내용물을 동일시할 수 있다는 뜻입니다.

장식틀 Border클래스는 Display 형의 display 필드를 가지고 있습니다. display 필드는 장식틀 안의
'내용물'을 가리킵니다. 하지만 display의 내용물이 반드시 StringDisplay 인스턴스라고 할 수는 없습니다. 어쨌든
Border도 Display의 하위 클래스이므로 display필드의 내용물은 또 다른 장식물일지도 모릅니다. 
그리고 그 장식틀 또한 display필드롤 가지고 있을 수 있습니다. 
```java
public abstract class Border extends Display{
    protected Display display;

    protected Border(Display display){
        this.display = display;
    }
}
```

### SideBorder 클래스 
SideBorder 클래스는 구체적인 장식틀의 일종으로 Border 클래스의 하위 클래스입니다.
SideBorder 클래스는 문자열 좌우에 정해진 문자(borderChar)로 장식을 합니다. 
예를 들어 borderChar 필드의 값아 'i'라고 하면, 다음과 같이 '내용물'좌우에 그 문자가 붙어 show로 표시됩니다. 
borderChar필드는 생성자로 지정합니다. 

SideBorder는 추상 클래스가 아닙니다. 왜냐하면, 상위 클래스에서 선언된 추상 메소드를 모두
구현했기 때문입니다. getColumns는 표시 문자의 가로 문자 수를 얻는 메소드입니다. 문자 수는 어떻게 계산할까요?
간단히 이 장식틀이 감싸고 있는 내용물의 문자수에 좌우 장식 문자 수를 더하면 됩니다. 
내용물으 ㅣ문자 수는 display.getColumns로 얻을 수 있습니다. display 필드는 Border 클래스에서 protected로
선언됐기 때문에 하위 클래스에서 직접 이용할 수 있습니다. 좌우 장식 문자 수를 더한 다음 식이 반환값입니다. 

getColumns 메소드는 만드는 법을 이해하면 getRows 메소드도 바로 이해할 수 있습니다. SideBorder 클래스는
상하 방향으로는 전혀 손대지 않으므로 식 display.getRows()값이 그대로 getRows메소드의 값이 됩니다.
```java
public class SideBorder extends Border{

    private char borderChar;

    public SideBorder(Display display, char ch) {
        super(display);
        this.borderChar = ch;
    }

    @Override
    public int getColumns() {
        return display.getColumns() + 2;
    }

    @Override
    public int getRows() {
        return display.getRows() + 2;
    }

    @Override
    public String getRowText(int row) {
        return borderChar + display.getRowText(row) + borderChar;
    }
}
```
### FullBorder 클래스
FullBorder 클래스는 SideBorder클래스와 마찬가지로 Border의 하위 클래스 중 하나입니다. 
SideBorder 클래스는 좌우로만 장식했는데 FullBorder 클래스는 상하좌우로 장식합니다. 단, SideBorder 
클래스에서는 장식문자를 지정할 수 있었지만, FullBorder 클래스는 장식 문자가 고정되어 있습니다. 
```java
public class FullBorder extends Border{
    public FullBorder(Display display) {
        super(display);
    }

    @Override
    public int getColumns() {
        return 1 + display.getColumns() + 1;
    }

    @Override
    public int getRows() {
        return 1 + display.getRows() + 1;
    }

    @Override
    public String getRowText(int row) {
        if (row == 0){
            return "+" + makeLine('-', display.getColumns()) + "+";
        } else if (row == display.getRows() + 1){
            return "+" + makeLine('-', display.getColumns()) + "+";
        } else {
            return "|" + display.getRowText(row-1) + "|";
        }
    }

    private String makeLine(char ch, int count){
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < count; i++) {
            line.append(ch);
        }
        return line.toString();
    }
}
```
## Decorator 패턴의 등장인물 
<hr>

### Component역
기능을 추가할 때 핵심이 되는 역할입니다. 케이크에 비유하면, 장식하기 전의 스펀지 케이크에 해당합니다. 
Component는 스펀지 케이크의 인터페이스만을 정의합니다. 예제 프로그램에서는 display 클래스가 이 역할을 맡습니다. 

### ConcreteComponent역 
Component역 인터페이스를 구현하는 구체적인 스펀지 케이크입니다. 예제 프로그램에서는 String-Display 클래스가
이 역할을 맡았습니다. 

### Decorator역
Componnent의 동일한 인터페이스를 가지며, 이 Decorator가 장식할 대상이 되는 Component도 가지고 있습니다.
이 역은 자신이 장식할 대상을 아는 셈입니다. 예제 프로그램에서는 Border 클래스가 이 역할을 맡았습니다. 

### ConcreteDecortator (구체적인 장식자)역
구체적인 Decorator입니다. 예제 프로그램에서는 SideBorder 클래스와 FullBorder 클래스가 이 역할을 맡았습니다.

## 사고 넓히기 
### 투과적 인터페이스 
Decorator 패턴에서는 장식물과 내용물을 동일시합니다. 구체적으로 말하면, 예제 프로그램에서 장식물을
나타내는 Border 클래스가 내용을 나타내는 Display 클래스의 하위클래스로 되어 있는 부분에서 그 동일시가 
표현되어 있습니다. 즉, border 클래스 및 그 하위 클래스의 내용을 나타내는 Display 클래스와 같은 인터페이스를 가집니다.

인터페이스가 투과적이므로 Decorator 패턴에서는 Composite 패턴과 유사한 재귀적인 구조가 등장합니다.
즉, 장식틀이 감싼 내용물이 실제로는 다른 것의 장식틀이 되는 구조입니다. 양파 껍질을 벗겨 알맹이가
나왔다고 생각했는데 그것 또한 껍질이었다는 것과 같습니다. 

### 내용물을 바꾸지 않고 기능을 추가할 수 있다. 
Decorator 패턴에서는 장식물과 내용물이 공통의 인터페이스를 가집니다. 인터페이스는 공통이지만,
감싸면 감쌀수록 기능이 추가됩니다. Display를 SideBorder로 감싸면 좌우에 새로운 장식 문자를 붙여 표시할 수 있고 
FullBorder로 감싸면 주위 전체에 장식틀을 붙일 수 있습니다. 이때 감싸지는 쪽을
수정할 필요는 없습니다. 내용물을 변경하지 않고 기능을 추가할 수 있습니다.
