package com.example.sunjay.represent.shared.util;

public class BlockingQueue {
  private int pendingCount;
  private Runnable completionRunnable;

  public BlockingQueue(int pendingCount, Runnable completionRunnable) {
    if (pendingCount <= 0) {
      completionRunnable.run();
    }
    this.pendingCount = pendingCount;
    this.completionRunnable = completionRunnable;
  }

  public void down() {
    synchronized (this) {
      pendingCount--;
      if (pendingCount == 0) {
        new Thread(completionRunnable).start();
      }
    }
  }
}
