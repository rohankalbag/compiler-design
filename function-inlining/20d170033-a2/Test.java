class Test {
    public static void main(String[] arg) {
        Shape s;
        int ret;
        s = new Square(); // call to Circle.foo() inherited from Shape.
        ret = s.foo(s);
        System.out.println(ret);
    }
}

class Shape {
    int p;

    public int foo(Shape s) {
        Circle c;
        Square sq;
        int val;
        int ret;
        val = 10;
        ret = s.computeArea(val); // computeArea() is inlineable since only Circle is instantiated in the
                                  // hierarchy.
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
        t2 = 2;
        t3 = 3;
        radius = diameter / t2;
        t1 = radius * radius;
        area = t1 * t3;
        return area;
    }
}

class Square extends Shape {
    public int computeArea(int side) {
        int area;
        area = side * side;
        return area;
    }
}
