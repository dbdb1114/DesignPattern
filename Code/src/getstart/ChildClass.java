package getstart;

public class ChildClass extends ParentClass{
    @Override
    void methodA() {
        System.out.println("ChildClass.methodA");
    }
    static void methodC(){
        System.out.println("ChildClass.methodC");
    }
}
