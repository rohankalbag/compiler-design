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
        f = !f;
        a = 5;
        b = 6;
        z = x + y; 
        if(f){
            c = a + b;
            c = c + a;
            if(f){
                c = c + a;
            }
            else{
                c = c + b;
            }
        }
        else{
            c = x - y;
            System.out.println(a);
        }
        if(f){
            c = c + a;
        }
        while(f){
            c = c + a;
            f = false;
        }
        return c;
    }
}