class Test {
    public static void main(String[] args) {
        System.out.println(new A().foo(10));
    }
}

class A {
    public int foo(int p) {
        int x;
        boolean y;
        int z;
        int w;
        int q;
        C obj;
        x = 10;
        y = true;
        z = 0;
        if (z) { // Type error 1: non-boolean expression for if statement
            z = z + p;
            if (z != false) { // Type error 2: comparison between integer and boolean
                y = false;
            }
        }
        obj = this.bar(3, false);
        return z;
    }

    public C bar(int p, boolean v) {
        int a;
        int b;
        C c;
        c = new D();
        a = c.x;
        return c;
    }
}

class C{
    int x;
}

class D extends C{
    int y;
    int b;
}