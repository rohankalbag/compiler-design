class t3 {
    public static void main(String[] args) {
        System.out.println((1 + 2) * 3);
    }
}

class A {
    int x;
    boolean y;
    int[] m;

    public int func(int p, int q, A a, boolean f) {
        int r;
        int[] l;
        l = new int[r];
        l = new int[y]; // 2 errors
        r = m[2];
        r = m[x];
        r = (new int[3])[2];
        p = p + 2;
        x = x + 1;
        r = x * 2;
        m[x] = 2 * 3;
        m[false] = (y != false); // 2 errors
        x = (a.z) + 2; // 3 errors
        x = (m).length;
        x = (l).length;
        if (x <= (r + 2)) {
            System.out.println(p);
        }

        if (!(y || (y && (!y)))) {
            m[r] = 2 + 2;
            m[r + 1] = x;
        }

        if (y != f) {
            System.out.println((a.x) + (p + q));
        }
        return x;
    }
}

class B extends A {
    int u;
    boolean v;
    A cl;
    B me;

    public int foo(A bruh) {
        A k;
        B mofo;
        D hahaha;
        k = new A();
        cl = new A();  
        mofo = new B();
        mofo = new D();
        k = me.cl;
        k = me.me;
        k = me.f; // 2 errors
        k = me.u; // 1 error
        x = (bruh).func(1, 2, mofo, false);
        x = (bruh).func(1, 2, hahaha, false);    
        if(v){
            x = x + 1;
        }
        while(v){
            x = x * 1;
        }
        u = this.pool(1);
        u = u + x;
        if (cl != k) {
            x = x + 1;
        }
        x = (u + x)[3]; // 2 errors
        cl.x = 2 + 3;
        cl.y = v;
        if (!v) {
            u = this.meow(); // 2 errors
        } else {
            x = this.func(2, 3, k, false);
            x = this.func(false, false, cl, false); // 1 errors
        }
        x = (k).func(2, 2, cl, v);
        //x = (k).func();
        if (y != v) {
            System.out.println(u);
        }
        return 0;
    }

    public int pool(int e) {
        return e * 2;
    }
}

class C{
    int x;
    public D hello(){
        D l;
        l = new D();
        return l;
    }
    public int bar(){
        A m;
        B n;
        m = new A();
        n = new B();
        m = n;
        m = new B();
        m = this.hello();
        n.cl = this.hello();
        return 0;
    }
}

class D extends B{}