/*2*/
class TC04 {
    public static void main(String[] args) {
        TestTC04 o;
        int res;
        o = new TestTC04();
        res = o.foo();
        System.out.println(res);
    }
}

class TestTC04 {
    public int foo() {
        int a;
        int b;
        int c;
        int d;
        int e;
        int t;
        a = 5;
        b = 6;
        c = a + b;
        d = c + a;
        e = a - c;
        t = d - e;
        return t;
    }
}


class Demo {
    public int foo(int x, int y, int z) {
        int a;
        int b;
        int c;
        boolean f;
        f = true;
        a = 5;
        b = 6;
        if(f){
            c = a + b;
            c = c + a;
        }
        else{
            c = x - y;
        }
        return c;
    }
}