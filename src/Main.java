import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        int[] arr = new int[1_000_000];
        int[] arrTwo = new int[(int) (1024 * 1024 * 1024L / Long.BYTES)];
        generateIntArray(arr);
        generateIntArray(arrTwo);

        long start = System.currentTimeMillis();

        // Однопоточное решение
//        long[] result = sumAndAverageArrayElements(arrTwo);
//        System.out.println("Сумма: " + result[0]);
//        System.out.println("Среднее арифметическое: " + result[1]);
//        long end = System.currentTimeMillis();
//        long time = end - start;
//        System.out.println("Время выполнения задачи: " + time + " миллисекунд");

        // Многопоточное решение
        ForkJoinPool pool = new ForkJoinPool();
        SumAndAverageTask task = new SumAndAverageTask(arrTwo, 0, arrTwo.length);
        long[] result = pool.invoke(task);
        System.out.println("Сумма: " + result[0]);
        System.out.println("Среднее арифметическое: " + result[1]);
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("Время выполнения задачи: " + time + " миллисекунд");

    }

    public static int[] generateIntArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        return arr;
    }

    // Метод подсчета суммы значений элементов целочисленного массива
    public static long[] sumAndAverageArrayElements(int[] arr) {
        long sum = 0;

        for (int num : arr) {
            sum += num;
        }

        double average = (double) sum / arr.length;

        return new long[]{sum, (long) average};  // Возвращаем сумму и среднее арифметическое в виде массива
    }


}
