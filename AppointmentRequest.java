public class AppointmentRequest {
    int id;
    int arrivalTime;
    int burstTime;
    int originalBurstTime;
    int priority;
    int deadline;
    int startTime;
    int finishTime;
    int waitingTime;
    int turnaroundTime;

    public AppointmentRequest(int id, int arrivalTime, int burstTime, int priority, int deadline) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.originalBurstTime = burstTime;
        this.priority = priority;
        this.deadline = deadline;
        this.startTime = -1;
        this.finishTime = -1;
    }
}
