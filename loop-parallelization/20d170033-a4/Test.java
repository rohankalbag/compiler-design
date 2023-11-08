class Test {
    public static void main(String[] arg) {
        Operator operator;
        int[] arr1;
        int[] arr2;
        int[] res;
        int m;
        int x;
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

        for (j = 0; j < 100; j = j + 1) {
            m = i + 1;
            m = 3 + j;
            m = 2 * m;
            // i = i + 1;
            arr3[j] = arr3[m];
        }

        for (i = 0; i < 100; i = i + 1) {
            for (j = 0; j < 100; j = j + 1) {
                m = i + 1;
                arr3[j] = arr3[m];
            }
        }

        for (i = 0; i < 10; i = i + 1) {
            arr3[j] = arr3[i] + arr3[j];
        }

        return arr4;
    }
}