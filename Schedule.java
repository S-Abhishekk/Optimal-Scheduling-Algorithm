import java.util.*;

public class Schedule {
    private int[] appointmentOrder;
    private int frameSize;

    public Schedule(int[] appointmentOrder, int frameSize) {
        this.appointmentOrder = appointmentOrder;
        this.frameSize = frameSize;
    }

    public void optimalScheduling() {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < appointmentOrder.length; i++) {
            if (!frames.contains(appointmentOrder[i])) {
                if (frames.size() < frameSize) {
                    frames.add(appointmentOrder[i]);
                } else {
                    int farthest = i + 1;
                    int indexToReplace = 0;

                    for (int j = 0; j < frames.size(); j++) {
                        int k;
                        for (k = i + 1; k < appointmentOrder.length; k++) {
                            if (frames.get(j) == appointmentOrder[k]) {
                                if (k > farthest) {
                                    farthest = k;
                                    indexToReplace = j;
                                }
                                break;
                            }
                        }
                        if (k == appointmentOrder.length) {
                            indexToReplace = j;
                            break;
                        }
                    }
                    frames.set(indexToReplace, appointmentOrder[i]);
                }
                pageFaults++;
            }
        }
        System.out.println("Optimal Scheduling Page Faults: " + pageFaults);
    }

    public void lruScheduling() {
        List<Integer> frames = new ArrayList<>();
        int pageFaults = 0;
        Map<Integer, Integer> recentMap = new HashMap<>();

        for (int i = 0; i < appointmentOrder.length; i++) {
            if (!frames.contains(appointmentOrder[i])) {
                if (frames.size() < frameSize) {
                    frames.add(appointmentOrder[i]);
                } else {
                    int lru = Integer.MAX_VALUE;
                    int indexToReplace = -1;

                    for (int j = 0; j < frames.size(); j++) {
                        if (recentMap.get(frames.get(j)) < lru) {
                            lru = recentMap.get(frames.get(j));
                            indexToReplace = j;
                        }
                    }

                    frames.set(indexToReplace, appointmentOrder[i]);
                }
                pageFaults++;
            }
            recentMap.put(appointmentOrder[i], i);
        }
        System.out.println("LRU Scheduling Page Faults: " + pageFaults);
    }
}
