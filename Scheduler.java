import java.util.*;

public class Scheduler {
    List<AppointmentRequest> requests;

    public Scheduler(List<AppointmentRequest> requests) {
        this.requests = requests;
    }

    public void fcfs() {
        int currentTime = 0;
        for (AppointmentRequest request : requests) {
            if (currentTime < request.arrivalTime) {
                currentTime = request.arrivalTime;
            }
            request.startTime = currentTime;
            request.finishTime = currentTime + request.burstTime;
            currentTime += request.burstTime;
        }
        calculateMetrics("FCFS");
    }

    public void sjf() {
        List<AppointmentRequest> remainingRequests = new ArrayList<>(requests);
        remainingRequests.sort(Comparator.comparingInt((AppointmentRequest r) -> r.burstTime)
            .thenComparingInt(r -> r.arrivalTime));

        int currentTime = 0;
        for (AppointmentRequest request : remainingRequests) {
            if (currentTime < request.arrivalTime) {
                currentTime = request.arrivalTime;
            }
            request.startTime = currentTime;
            request.finishTime = currentTime + request.burstTime;
            currentTime += request.burstTime;
        }
        calculateMetrics("SJF");
    }

    public void rr(int quantum) {
        Queue<AppointmentRequest> queue = new LinkedList<>(requests);
        int currentTime = 0;

        while (!queue.isEmpty()) {
            AppointmentRequest request = queue.poll();
            if (request.startTime == -1) {
                request.startTime = Math.max(currentTime, request.arrivalTime);
            }
            int timeSlice = Math.min(quantum, request.burstTime);
            currentTime += timeSlice;
            request.burstTime -= timeSlice;

            if (request.burstTime > 0) {
                queue.add(request);
            } else {
                request.finishTime = currentTime;
            }
        }
        calculateMetrics("Round Robin");
    }

    public void priorityScheduling() {
        List<AppointmentRequest> remainingRequests = new ArrayList<>(requests);
        remainingRequests.sort(Comparator.comparingInt((AppointmentRequest r) -> r.priority)
            .thenComparingInt(r -> r.arrivalTime));

        int currentTime = 0;
        for (AppointmentRequest request : remainingRequests) {
            if (currentTime < request.arrivalTime) {
                currentTime = request.arrivalTime;
            }
            request.startTime = currentTime;
            request.finishTime = currentTime + request.burstTime;
            currentTime += request.burstTime;
        }
        calculateMetrics("Priority");
    }

    public void multilevelFeedbackQueueScheduling() {
        Queue<AppointmentRequest> queue1 = new LinkedList<>();
        Queue<AppointmentRequest> queue2 = new LinkedList<>();
        int currentTime = 0;

        for (AppointmentRequest request : requests) {
            queue1.add(request);
        }

        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            AppointmentRequest request;
            if (!queue1.isEmpty()) {
                request = queue1.poll();
            } else {
                request = queue2.poll();
            }

            if (request.startTime == -1) {
                request.startTime = Math.max(currentTime, request.arrivalTime);
            }
            int timeSlice = (queue2.isEmpty()) ? 2 : Math.min(4, request.burstTime);
            request.burstTime -= timeSlice;
            currentTime += timeSlice;

            if (request.burstTime > 0) {
                queue2.add(request);
            } else {
                request.finishTime = currentTime;
            }
        }
        calculateMetrics("Multilevel Feedback Queue");
    }

    private void calculateMetrics(String algorithmName) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int n = requests.size();

        for (AppointmentRequest request : requests) {
            request.waitingTime = request.startTime - request.arrivalTime;
            request.turnaroundTime = request.finishTime - request.arrivalTime;
            totalWaitingTime += request.waitingTime;
            totalTurnaroundTime += request.turnaroundTime;
        }

        double avgWaitingTime = (double) totalWaitingTime / n;
        double avgTurnaroundTime = (double) totalTurnaroundTime / n;

        System.out.println("Algorithm: " + algorithmName);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("--------------------------------------------------");
    }
}
