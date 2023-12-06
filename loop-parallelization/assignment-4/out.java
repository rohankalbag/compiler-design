class Test {
	public static void main ( String [] arg ) {
		Operator operator ; 
		int [] arr1 ; 
		int [] arr2 ; 
		int [] res ; 
		operator = new Operator ( ) ; 
		arr1 = new int [250] ; 
		arr2 = new int [250] ; 
		res = operator . operate ( arr1 , arr2 ) ; 
		}
	}
class Operator {
	public int [] operate ( int [] arr1 , int [] arr2 ) {
		int [] arr3 ; 
		int [] arr4 ; 
		int i ; 
		int j ; 
		int k ; 
		arr3 = new int [100] ; 
		arr4 = new int [1000] ; 
		for ( i = 1 ; i <= 200 ; i = i + 1 ) {
			arr4 [i] = i ; 
			}
		for ( i = 1 ; i <= 10 ; i = i + 1 ) {
			k = i * 2 ; 
			j = i * 10 ; 
			j = j + 51 ; 
			arr3 [i] = arr4 [k] ; 
			arr4 [j] = k ; 
			}
		for ( i = 0 ; i < 200 ; i = i + 1 ) {
			j = 200 - i ; 
			j = j - 1 ; 
			arr4 [i] = arr4 [j] ; 
			}
		arr3 = arr2 ; 
		arr4 = arr1 ; 
		for ( i = 0 ; i < 99 ; i = i + 1 ) {
			j = i + 1 ; 
			arr3 [i] = arr4 [j] ; 
			}
		return arr4 ; 
		}
	}
 