/*2*/
class TC06 {
    public static void main(String[] args) {
        TestTC02 o;
        int res;
        o = new TestTC02();
        res = o.foo();
        System.out.println(res);
    }
}

class TestTC02 {
    public int foo() {
        int a;
        int b;
        int c;
        int d;
        int e;
        int t;
        float x;
        TestTC02 o;
        o = new TestTC02();
        x = o.bar();
        a = 5;
        b = o.bar();
        c = a + b;
        d = c + a;
        e = a - c;
        t = d - e;
        return t;
    }

    public float bar() {
        float x;
        float y;
        float z;
        x = 5;
        y = 6;
        z = x + y;
        return z;
    }
}