class Test2 {
    public static void main(String[] arg) {
        Shape s;
        int ret;
        s = new Circle();
        ret = s.foo(s);
        System.out.println(ret);
    }
}

class Shape {
    public int func1(int x) {
        return x;
    }

    public int func2(int y) {
        int r;
        r = y * y;
        return r;
    }

    public int func3(int z) {
        Shape p;
        p = new Circle();
        z = p.func2(z);
        return z;
    }

    public int foo(Shape s) {
        Circle c;
        Square sq;
        int value;
        int ret;
        value = 10;
        ret = s.computeArea(value);
        return ret;
    }

    public int computeArea(int val) {
        return val;
    }
}

class Circle extends Shape {
    public int computeArea(int diameter) {
        int radius;
        int area;
        int t1;
        int t2;
        int t3;
        A smile;
        boolean flag;
        smile = new B();
        t2 = 2;
        t3 = 3;
        flag = t2 <= t3;
        if (flag) {
            System.out.println(t2);
        } else {
            System.out.println(t3);
        }
        t2 = smile.func1(t3);
        radius = diameter / t2;
        t1 = radius * radius;
        area = t1 * t3;
        return area;
    }
}

class Square extends Shape {

    public int func1(int x) {
        int p;
        p = x;
        return p;
    }

    public int func2(int y) {
        int r;
        boolean k;
        k = true;
        r = y + y;
        if (k) {
            r = y * y;
        } else {
            r = r + y;
        }
        return r;
    }

    public int bar(int p) {
        int k;
        Shape c;
        c = new Circle();
        k = c.func1(p);
        k = c.func1(k);
        return k;
    }

    public int computeArea(int side) {
        int area;
        area = side * side;
        return area;
    }
}

class A {
    public int func1(int x) {
        return x;
    }
}

class B extends A {

    public int foo(int p) {
        A q;
        q = new B();
        p = q.func1(p);
        return p;
    }

    public int func1(int x) {
        return x;
    }

    public int meow(int x, int y, float z) {
        y = x + y;
        return y;
    }
}