/*3*/
class TC05 {
    public static void main(String[] args) {
        TestTC04 o;
        int res;
        o = new TestTC04();
        res = o.boo();
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
     public int boo() {
        int a;
        int b;
        int c;

        int e;

        boolean x;
        boolean y;
        boolean ca;
        a = 0;
        b = 10;
        c = 1;
        x = false;
        y = true;
        ca = x || y;
        while(ca){
            y = false;
            a = a + c;
            x = b <= a;
            x = !x;
            System.out.println(a);
            ca = x || y;
        }
        e = a + b;
        return e;
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

    public int choo(){
        int res;
        float f;
        float g;
        res = 3;
        f = 27;
        g = 54;
        f = f + g;
        System.out.println(f);
        return res;
    }
}