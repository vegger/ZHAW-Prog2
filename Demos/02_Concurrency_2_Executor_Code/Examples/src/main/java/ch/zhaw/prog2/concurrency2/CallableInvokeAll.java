package ch.zhaw.prog2.concurrency2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class CallableInvokeAll {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> task1 = () -> {
            Thread.sleep(2000);
            //throw new NullPointerException("Error in Task1");
            return "Result of Task1";
        };

        Callable<String> task2 = () -> {
            Thread.sleep(1000);
            throw new NullPointerException("Error in Task2");
            //return "Result of Task2";
        };

        Callable<String> task3 = () -> {
            Thread.sleep(5000);
            //throw new NullPointerException("Error in Task3");
            return "Result of Task3";
        };

        List<Callable<String>> taskList = Arrays.asList(task1, task2, task3);

        long startTime = System.currentTimeMillis();

        System.out.println("Waiting for all results...");
        // This will block until all tasks have completes (returned a result or  have thrown an exception)
        List<Future<String>> futureList = executorService.invokeAll(taskList);
        System.out.println("Results are in:");

        for(Future<String> future: futureList) {
            // The result is printed only after all the futures are done. (i.e. after 5 seconds)
            try {
                System.out.println(future.get() + " after " + (System.currentTimeMillis() - startTime) + "ms");
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();  // extract the real exception thrown within the Callable
                System.out.println("Task throws " + cause.getClass().getName() + " : " + cause.getMessage());
            }
        }

        executorService.shutdown();
    }
}
