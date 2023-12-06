/*2*/
class TC03 {
    public static void main(String[] args) {
        TestTC02 o;
        int g;
        g = 1;
        o = new TestTC02();
        g = o.foo(g, g);
        System.out.println(g);
    }
}

class TestTC02 {
    public int foo(int alpha, int gamma) {
        int a;
        int b;
        int c;
        int d;
        boolean x;
        x = true;
        a = 1;
        b = 2;
        
        d = a;
        c= a + a;
        if(x){
            a = 10;
        }else{
            b = 4;
        }
        c = d;
        d = b + c;
        d = d + a;
        return d;
    }
}