
/*2*/
import static a3.Memory.*;

class TC02 {
    public static void main(String[] args) {
        Object R0;
        alloca(0);
        R0 = new TestTC02();
        R0 = ((TestTC02) R0).foo();
        System.out.println(((int) R0));
    }
}

class TestTC02 {
    public int foo() {
        Object R0;
        Object R1;
        alloca(1);
        store(0, 5);
        R0 = 6;
        R0 = ((int) load(0)) + ((int) R0);
        R1 = ((int) R0) + ((int) load(0));
        R0 = ((int) load(0)) - ((int) R0);
        R0 = ((int) R1) - ((int) R0);
        return ((int) R0);
    }
}
