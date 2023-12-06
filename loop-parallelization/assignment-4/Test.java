class Test {
    public static void main(String[] arg) {
        Operator operator;
        int[] arr1;
        int[] arr2;
        int[] res;
        operator = new Operator();
        arr1 = new int[250];
        arr2 = new int[250];
        res = operator.operate(arr1, arr2);
    }
}

class Operator {
    public int[] operate(int[] arr1, int[] arr2) {
        int[] arr3;
        int[] arr4;
        int i;
        int j;
        int k;
        int m;
        arr3 = new int[100];
        arr4 = new int[1000];
        m = 3;
        arr2[0] = 3 + 2;
        // Trivially Parallelizable loop.
        for (i = 1; i <= 200; i = i + 1) {
            arr4[i] = i;
        }
        // Parallelizable loop using GCD Test
        for (i = 1; i <= 10; i = i + 1) {
            // m = i;
            k = i * 2;
            j = i * 10;
            j = j + 51;
            // j = j * 2;
            arr3[j] = arr4[k];
            arr4[i] = j;
            // arr3[i] = arr4[i] + arr3[i];
            // arr4[3] = arr4[m];
        }
        // Non-Parallelizable loop using GCD Test.
        for (i = 0; i < 200; i = i + 1) {
            j = 200 - m;
            j = j - 1;
            arr4[i] = arr4[j];
        }
        arr3 = arr2;
        arr4 = arr1;
        // Non-Parallelizable loop. (arr3 and arr4 can be aliases)
        for (i = 0; i < 99; i = i + 1) {
            j = i * 6;
            k = i * 2;
            arr3[j] = arr4[k];
        }
        return arr4;
    }
}