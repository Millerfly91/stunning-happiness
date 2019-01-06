/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package misc.time;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jacob
 */
public class Stopwatch {

    private long startTime;
    private long elapsedTime;

    public Stopwatch() {
        startTime = -1;
        elapsedTime = -1;
    }

    public void start() {
        if (startTime <= 0) {
            startTime = getCurrentTimeInNanoseconds();
        } else {
            throw new RuntimeException("Stopwatch is already running.");
        }
    }

    public void stop() {
        updateTimes();
        startTime = -1;
    }

    public void reset() {
        startTime = getCurrentTimeInNanoseconds();
        elapsedTime = -1;
    }

    public long elapsed(TimeUnit unit) {
        updateTimes();
        switch (unit) {
            case NANOSECONDS:
                return elapsedTime;
            case MICROSECONDS:
                return elapsedTime / (1000);
            case MILLISECONDS:
                return elapsedTime / (1000 * 1000);
            case SECONDS:
                return elapsedTime / (1000 * 1000 * 1000);
            case MINUTES:
                return elapsedTime / (1000 * 1000 * 1000 * 60);
            case HOURS:
                return elapsedTime / (1000 * 1000 * 1000 * 60 * 60);
            case DAYS:
                return elapsedTime / (1000 * 1000 * 1000 * 60 * 60 * 24);
            default:
                throw new RuntimeException(
                        "Unable to provide elapsed time in unit: " + unit);
        }
    }

    private void updateTimes() {
        if (startTime != -1) {
            elapsedTime += (getCurrentTimeInNanoseconds() - startTime);
            startTime = getCurrentTimeInNanoseconds();
        }
    }

    private long getCurrentTimeInNanoseconds() {
        return System.nanoTime();
//        return System.currentTimeMillis() * (1000 * 1000);
    }

}
