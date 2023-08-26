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
        int q;
        boolean w;
        int [] arr;
        arr = new int[10];
        q = (arr).length;
        q = arr[2];
        x = 10;
        z = 3;
        q = (3*z) + 2;
        arr[x] = z;
        y = true;
        w =  y || !y;
        z = 0;
        y = (true != w) && y;
        y = 3 != 7;
        while(x <= 14){
            x = x + 1;
        }
        
        if (y) {
            z = (x + 1) + (x + 10);
            if (z != 3) {
                y = false;
            }
        }
        //q = this.bar(); //TODO
        return z;
    }
    public int bar() {
        int a;
        int b;
        a = 10;
        return a;
    }
}
class B extends A {}
