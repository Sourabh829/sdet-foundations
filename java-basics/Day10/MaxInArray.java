public class MaxInArray {
    public static void main(String[] args) {
        int[] arr = {23,5,12,35,90,12345,98576};
        int max =arr[0];
        for(int i=0;i<arr.length;i++){
            if (arr[i]>max){
                max=arr[i];
            }
        }
        System.out.println("The maximum element in the array is: " + max);
    }
}
