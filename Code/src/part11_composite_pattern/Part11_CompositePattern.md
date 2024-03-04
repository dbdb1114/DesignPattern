## Composite패턴 <hr>
컴퓨터의 파일 시스템에는 '디렉토리'라는 것이 있습니다. 디렉토리 안에는 파일이나 다른 디렉토리가 들어갑니다. 
그리고 또 그 하위 디렉토리 안에 다른 파일이나 하위 디렉토리가 들어가기도 합니다. 디렉터리는 그러한 '중첩'된
구조, 재귀적인 구조를 만들어 냅니다. 

그런데, 디렉토리와 파일은 서로 다르지만, 둘 다 디렉토리 안에 넣을 수 있는 것입니다. 디렉토리와 파일을
합쳐서 '디렉토리 엔트리'라고 부르기도 합니다. 디렉토리 엔트리라는 이름으로 디렉토리와 파일을 같은 종류로 간주하는 것입니다.

예를 들어 어떤 디렉토리 안에 무엇이 있는지를 차례대로 조사한다고 할 때 조사하는 것이 하위 디렉토리일 수도 있고
파일일 수도 있습니다. 한마디로 디렉토리 엔트리를 차례대로 조사하는 것입니다. 

디렉토리와 파일을 합쳐서 디렉토리 엔트리로 다루듯 그릇과 내용물을 같은 종류로 취급하면 편리한 경우가 있습니다. 그릇 안에는
내용물을 넣을 수도 있고, 더 작은 그릇을 넣을 수도 있습니다. 
그리고 그 작은 그릇 안에 더 작은 그릇을 넣는 식으로 중첩된 구조, 재귀적인 구조를 만들 수 있습니다. 

Composite패턴은 그릇과 내용물을 동일시하여 재귀적인 구조를 만드는 디자인 패턴입니다. 


## 예제 프로그램 <hr>
Composite패턴의 예제 프로그램으로 파일과 디렉토리를 도식적으로 표현한 프로그램을 만들어 봅니다. 파일을 나타내는 클래스인 File클래스, 디렉토리를
나타내는 클래스인 Directory 클래스, 그 둘을 취합하는 형태의 상위 클래스인 Entry가 있습니다. Entry 클래스는 디렉토리 엔트리를 나타내는 클래스로 File과 
Directory를 동일시하는 클래스입니다. 

### Entry 추상 클래스
```java
public abstract class Entry {
    public abstract String getName();
    public abstract int getSize();
    public void printList(){
        printList("");
    }

    protected abstract void printList(String prefix);

    @Override
    public String toString() {
        return getName() + "(" + getSize() + ")";
    }
}
```
### File클래스 Entry 구현체
```java
public class File extends Entry{
    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void printList(String prefix) {
        System.out.println(prefix+"/"+this);
    }
}
```
### Directory클래스 Entry 구현체 
```java
public class Directory extends Entry{
    String name;
    private List<Entry> directory = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSize() {
        return directory.stream().mapToInt(Entry::getSize).sum();
    }

    @Override
    public void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        for (Entry entry : directory) {
            entry.printList(prefix+"/" + name);
        }
    }

    public Entry add(Entry entry){
        directory.add(entry);
        return this;
    }
}
```

## Composite 패턴의 등장인물 
### Leaf역
'내용물'을 나타냅니다. 이 안에는 다른 것을 넣을 수 없습니다. 예제 프로그램에서는 File 클래스가 이 역할을 맡았습니다.
### Composite(복합체)역
'그릇'을 나타내며, Leaf역이나 Composite역을 넣을 수 있습니다. 예제 프로그램에서는 Directory 클래스가 
이 역할을 맡았습니다. 
### Component 역
Leaf역과 Composite 역을 동일시하기 위한 역할입니다. Component는 Leaf역과 Composite역에 공통되는 상위
클래스로 구현됩니다. 예제 프로그램에서는 Entry 클래스가 이 역할을 맡았습니다. 

## 사고 넓히기 <hr>
### 복수와 단수 동일시하기 
Composite 패턴은 그릇과 내용물을 동일시하는 패턴인데 이를 복수와 단수의 동일시로 부를 수도 있습니다. 
즉, 여러 개를 모아서 마치 하나의 것처럼 취급하는 것입니다. 

예를 들어 KeyboardTest, FileTest, NetworkTest 등의 Test를 전부 묶어서 InputTest라는 '입력 테스트'로 
만드는 것입니다. 마찬가지로 복수의 출력 테스트를 모은 OutputTest라는 '출력 테스트'도 만들 수 있습니다. 

이처럼 여러 그룹을 묶어서 더 큰 그룹을 만드는 방식을 Composite패턴이라고 합니다. 

### add는 어디에 두어야 할까
예제 프로그램에서는 디렉터리 엔트리를 추가하는 add메소드를 Directory 클래스에서 정의했습니다. 디렉터리 엔트리를 
추가할 수 있는 것은 디렉터리뿐이므로, 이 판단은 타당하다고 할 수 있습니다. 

gof책에서는 '자식'을 조작하는 메소드도 Component 역에서 정의합니다. 그런 경우에는 Leaf역에 대해 '자식'을 
조작하는 요청이 발생하면 뭔가 오류 처리가 필요합니다.

'자식'을 조작하는 메소드는 Composite역과 Component역 중 어느 쪽에서 정의하면 좋을까요? 결국 그것은 
'그릇과 내용물을 동일시한 결과로 얻어지는 것은 무엇인가?'에 대한 대답입니다. 예제 프로그램으로
말하자면 'Entry'클래스란 무엇인가? 라는 질문에 답하는 것입니다. 


