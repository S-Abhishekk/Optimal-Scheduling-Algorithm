 import java.util.*;

 class MainClass {
   
    public static void main(String[] args) {
        List<AppointmentRequest> requests = new ArrayList<>();
        requests.add(new AppointmentRequest(1, 0, 5, 3, 10));
        requests.add(new AppointmentRequest(2, 2, 3, 1, 7));
        requests.add(new AppointmentRequest(3, 4, 6, 4, 8));
        requests.add(new AppointmentRequest(4, 6, 2, 2, 5));
        requests.add(new AppointmentRequest(5, 8, 4, 5, 12));

        Scheduler scheduler = new Scheduler(requests);
        scheduler.fcfs();
        scheduler.sjf();
        scheduler.rr(2);
        scheduler.priorityScheduling();
        scheduler.multilevelFeedbackQueueScheduling();

        int[] appointmentOrder = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        int frameSize = 3;
        Schedule schedule = new Schedule(appointmentOrder, frameSize);
        schedule.optimalScheduling();
        schedule.lruScheduling();
    }
}
