class t3 {
    public static void main(String[] args) {
        System.out.println((1+2)*3);
    }
}

class A{
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
        p = p + 2;
        x = x + 1;
        m[x] = 2*3; 
        x = (a.z) + 2; // 3 errors
        x = (m).length;
        x = (l).length;
        if (x <= (r + 2)){ 
            System.out.println(p);
        }

        if(!(y || (y && (!y)))){
            m[r] = 2 + 2; 
            m[r + 1] = x;
        }

        if (y != f){
            System.out.println((a.x) + (p + q));
        }
        return r;
    }
}

class B extends A{
    int u;
    boolean v;
    A cl;
    public int foo(){
        A k;
        k = new A();
        cl = new A();
        u = this.pool(1);
        u = u + x;
        if(!v){
            u = this.meow(); // 2 errors
        }
        else{
            x = this.func(2, 3, k, false);
            x = this.func(false, 3, cl, false); // 2 errors
        }
        x = (k).func(2, 2, cl, v);
        if(y != v){
            System.out.println(u);
        }
        return 0;
    }

    public int pool(int e){
        return e*2;
    }
}