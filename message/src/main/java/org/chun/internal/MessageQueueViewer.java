package org.chun.internal;

import java.time.LocalDateTime;

public interface MessageQueueViewer {

  long count();// queue 內的數量

  long prefetchCount();// 一次取出訊息的數量

  long max();// 最大數量

  LocalDateTime expiredTime();

  void refreshLatestTime();
}
