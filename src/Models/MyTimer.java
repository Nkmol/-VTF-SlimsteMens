package Models;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {
  private final Timer t = new Timer();

  public TimerTask schedule(final Runnable r, long delay) {
     final TimerTask task = new TimerTask() { public void run() { r.run(); }};
     t.schedule(task, delay);
     return task;
  }
}