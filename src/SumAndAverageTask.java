import java.util.concurrent.RecursiveTask;

public class SumAndAverageTask extends RecursiveTask<long[]> {

    private int[] array;
    private int start;
    private int end;

    public SumAndAverageTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected long[] compute() {
        long sum = 0;
        double average = 0.0;

        // Базовый случай: если размер массива меньше или равен пороговому значению, выполняем вычисления напрямую
        if (end - start <= 1000) {
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            average = (double) sum / (end - start);
            return new long[]{sum, (long) average};
        } else {
            // Рекурсивный случай: разделяем массив на две части и создаем две подзадачи
            int mid = (end - start) / 2 + start;
            SumAndAverageTask task1 = new SumAndAverageTask(array, start, mid);
            SumAndAverageTask task2 = new SumAndAverageTask(array, mid, end);

            // Выполняем подзадачи параллельно с помощью ForkJoinPool
            invokeAll(task1, task2);

            // Присоединяемся к подзадачам и суммируем их результаты
            long[] result1 = task1.join();
            long[] result2 = task2.join();
            sum = result1[0] + result2[0];
            average = (result1[1] + result2[1]) / 2;

            return new long[]{sum, (long) average};
        }
    }
}
