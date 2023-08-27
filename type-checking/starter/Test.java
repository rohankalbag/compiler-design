class Test {
    public static void main(String[] args) {
        System.out.println(new A().foo(7));
    }
}

class A {
    public int foo(int p) {
        int x;
        boolean y;
        int z;
        int q;
        boolean w;
        int[] arr;
        arr = new int[10];
        q = (arr).length;
        q = arr[2];
        x = 3;
        z = 3;
        q = (3 * z) + 2;
        arr[x] = z;
        y = true;
        w = y || !y;
        z = 0;
        y = (true != w) && y;
        y = 3 != 7;
        while (x <= 14) {
            x = x + 1;
        }

        if (y) {
            z = (x + 1) + (x + 10);
            if (z != 3) {
                y = false;
            }
        }
        q = this.bar(3, 8);
        return z;
    }

    public int bar(int f, int x) {
        int a;
        int b;
        a = 10;
        return a;
    }
}

class B extends A {
}

class C {
    boolean f;
    int m;

    public int func() {
        int p;
        int q;
        int x;
        boolean r;
        D instance;
        p = q + 1;
        instance = new D();
        instance.a = 18;
        while (p <= 1) {
            x = x + 1;
        }
        return 0;
    }
}

class D {
    int a;
    boolean b;

    public int foo() {
        return 0;
    }
}