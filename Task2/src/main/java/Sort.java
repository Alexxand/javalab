 
public class Sort {
  private static double[] merge(double[] arr1, double[] arr2) {
    int l1 = arr1.length; 
    int l2 = arr2.length;
    double[] rez = new double[l1 + l2];
    int i = 0; 
    int j = 0;
    while (i < l1 && j < l2) {
      if (arr1[i] < arr2[j]) {
        rez[i + j] = arr1[i];
        ++i;
      } else {
        rez[i + j] = arr2[j];
        ++j;
      }  
    }
    if (i == l1) {
      for (; j < l2; ++j)
        rez[i + j] = arr2[j];
    } else {
      for (; i < l1; ++i)
        rez[i + j] = arr1[i];
    }
    return rez;
  }
  
  private static double[] newarr(double[] arr, int first, int length) {
    double[] rez = new double[length];
    for (int i = 0; i < length; ++i)
      rez[i] = arr[first + i];
    return rez;
  }
  
  private static double[] mergesort(double[] arr) {
    int l = arr.length;
    if (l != 1) {
      double[] arr1 = newarr(arr, 0, l / 2);
      double[] arr2 = newarr(arr, l / 2, (l + 1) / 2);
      arr1 = mergesort(arr1);
      arr2 = mergesort(arr2);
      return merge(arr1, arr2);
    } else {
      return arr;
    }
  }

  public static void main(String[] args) {
    int l = args.length;
    if (l != 0) {
      double[] arr = new double[l];
      for (int i = 0; i < args.length; ++i)
        arr[i] = Double.parseDouble(args[i]);
      double[] rez = mergesort(arr);
      for (double elem: rez)
        System.out.print(elem + " ");
      System.out.print('\n'); 
    }
  }
}
