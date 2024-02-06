
## Builder 패턴<hr>
도시에는 빌딩이 많습니다. 빌딩은 구조를 갖춘 커다란 건축물입니다. 일반적으로 구조를 갖춘 큰 구조물을 건축하거나
구축하는 것을 build라고 합니다. 

빌딩을 지을 때는 먼저 지반을 다진 후, 뼈대를 만들고 아래에서 위로 조금씩 만들어 갑니다. 대체로 복잡한 구조를 가진
구조물을 만들 경우, 단숨에 오나성하기는 어렵습니다. 우선 전체를 구성하는 각 부분을 만들고 단계를 밟아 나가며 만들게 됩니다. 



## 예제프로그램<hr>
### Builder와 Director 
Builder는 HTMLBuilder와 TextBuilder가 가져야할 메소드를 정의해두었습니다. 이를 통해 해당 클래스를 상속받고 추상 메소드를
구현함으로써 정형화된 Builder가 만들어지게 됩니다. 이후 Director 클래스에서는 Builder 인스턴스를 활용하여 construct()메소드 
안에서 일처리를 하게됩니다.
```java
public abstract class Builder {
    abstract void makeTitle(String title);
    abstract void makeString(String str);
    abstract void makeItems(String[] items);
    abstract void close();
}
```
```java
public class Director {
    Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void construct() {
        builder.makeTitle("Greeting");
        builder.makeString("일반적인 인사");
        builder.makeItems(new String[]{ "How are you?", "Hello", "Hi" });
        builder.makeString("시간대별 인사");
        builder.makeItems(new String[] { "Good morning", "Good afternoon", "Good evening" });
        builder.close();
    }
}
```
### Builder의 구현체들
각 Builder의 설계에 맞게 그리고 각자에 맞게 구현된 클래스입니다. 이로써 Director 클래스가 이들을 편하게 가져다가 
쓸 수 있게 됩니다.
```java
public class HTMLBuilder extends Builder{
    private String filename = "untitled.html";
    private StringBuilder sb = new StringBuilder();

    @Override
    void makeTitle(String title) {
        filename = title + ".html";
        sb.append("<!DOCTYPE html>\n")
                .append("<html>\n")
                .append("<head><title>")
                .append("title")
                .append("</title></head>\n")
                .append("<body>\n")
                .append("<h1>")
                .append(title)
                .append("</h1<\n\n");
    }

    @Override
    void makeString(String str) {
        sb.append("<p>")
                .append(str)
                .append("</p>\n\n");
    }

    @Override
    void makeItems(String[] items) {
        sb.append("<ul>\n");
        for (String s : items) {
            sb.append("<li>")
                    .append(s)
                    .append("</li>\n");
        }
        sb.append("</ul>\n\n");
    }

    @Override
    void close() {
        sb.append("</body>")
                .append("</html>\n");
        try {
            Writer writer = new FileWriter(filename);
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String getHTMLResult() {
        return filename;
    }
}
```
```java
public class TextBuilder extends Builder{
    private StringBuilder sb = new StringBuilder();
    @Override
    void makeTitle(String title) {
        sb.append("================================================\n")
                .append("[")
                .append(title)
                .append("]\n\n");
    }

    @Override
    void makeString(String str) {
        sb.append("\uD83D\uDCE6")
                .append(str)
                .append("\n\n");
    }

    @Override
    void makeItems(String[] items) {
        for (String s : items) {
            sb.append(" .")
                    .append(s)
                    .append("\n");
        }
        sb.append("\n");
    }

    @Override
    void close() {
        sb.append("==========================================\n");
    }

    public String getTextResult() {
        return sb.toString();
    }
}
```

### 정리 
TextBuilder와 HTMLBuilder는 Builder의 하위 클래스이고 Director는 Builder의 메소드만 사용해 문서를 만듭니다. 
Builder의 메소드만 사용한다는 것은 Director는 실제로 동작하는 것이 TextBuidler인지 HTMLBuilder인지 의식하지 않는다는 
뜻입니다. 따라서 Builder는 문서 구축이라는 목적을 달성하는 데 필요학고 충분한 메소드군을 선언하고 있어야 합니다. 단, 텍스트나
HTML 파일에 고유한 메소드까지 Builder가 제공해서는 안 됩니다.


## Builder패턴의 역할 구현 <hr>
### Builder(건축가) 역
인스턴스를 생성하기 위한 인터페이스를 결정합니다. Builder 역에는 인스턴스의 각 부분을 만드는 메소드가 준비됩니다. 예제 프로그램에서는
Buidler 클래스가 이 역할을 맡았습니다. 

### ConcreteBuilder(구체적인 건축가)역
Builder의 인터페이스를 구현하는 클래스입니다. 실제 인스턴스 생성으로 호출되는 메소드가 여기에 정의됩니다. 또한, 최종적으로 
완성된 결과를 얻는 메소드가 준비됩니다. 예제 프로그램에서는 TextBuidler클래스의 HTMLBuilder 클래스가 이 역할을 맡았습니다.

### Director(감독관) 역
Builder의 인터페이스를 사용하여 인스턴스를 생성합니다. ConcreteBuilder 역에 의존하는 프로그래밍은 하지 않습니다. 
ConcreteBuilder 역이 무엇이든 잘 작동하도록 Builder의 메소드만 사용합니다. 예제 프로그램에서는 Director클래스가
이 역할을 맡았습니다. 

## 관련 패턴<hr>
### Template Method 패턴 
Builder 패턴에서는 Director역이 Builder 역을 제어합니다. 반면에 Template Method 패턴에서는 상위 클래스가 하위 클래스를
제어앟ㅂ니다. 
### Composite 패턴 
Builder 패턴으로 만들어진 생성물은 Composite 패턴이 되는 경우가 많습니다. 
### Abstract Factory 패턴
Builder 패턴과 Abstract Factory 패턴 모두 복잡한 인스턴스를 생성합니다. 
### Facade 패턴
Builder 패턴의 Director 역은 Builder 역의 복잡한 메소드를 조합하여 인스턴스를 구축하는 간단한 인터페이스를 외부에 제공합니다.
Fascade패턴의 Fascade 역은 내부 모듈을 조합하여, 작업하기 위한 간단한 인터페이스를 외부에 제공합니다. 
 

