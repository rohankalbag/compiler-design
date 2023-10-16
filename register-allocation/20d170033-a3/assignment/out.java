/*2*/
import static a3.Memory.*;
class TC02 {
	public static void main(String[] args) {
		Object r1;
		alloca(0);
		r1 = new TestTC02();
		r1 = ((TestTC02) r1).foo();
		System.out.println(((int) r1));
	}
}
class TestTC02 {
	public int foo() {
		Object r1;
		Object r2;
		alloca(1);
		store(0, 5);
		r1 = 6;
		r2 = ((int) load(0)) + ((int) r1);
		r1 = ((int) r2) + ((int) load(0));
		r2 = ((int) load(0)) - ((int) r2);
		r1 = ((int) r1) - ((int) r2);
		return ((int) r1);
	}
}
