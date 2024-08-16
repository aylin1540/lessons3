public class Main {
    public static void main(String[] args) {

        double[] array = {2.5, 3.7, -1.2, 4.6, -3.4, 2.9, 5.1, -2.3, 4.4, 6.6, -7.8, 1.9, 3.3, -0.5, 8.0};


        int firstNegativeIndex = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0) {
                firstNegativeIndex = i;
                break;
            }
        }


        if (firstNegativeIndex != -1) {
            double sum = 0;
            int count = 0;


            for (int i = firstNegativeIndex + 1; i < array.length; i++) {
                if (array[i] > 0) {
                    sum += array[i];
                    count++;
                }
            }


            double average = count > 0 ? sum / count : 0;
            System.out.println("Среднее арифметическое положительных чисел после первого отрицательного: " + average);
        } else {
            System.out.println("Отрицательных чисел в массиве не");
        }
    }
}

