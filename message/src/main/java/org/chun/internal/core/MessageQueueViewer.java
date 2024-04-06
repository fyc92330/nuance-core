package org.chun.internal.core;

import java.time.LocalDateTime;

public interface MessageQueueViewer {

  long count();// queue 內的數量

  int prefetchCount();// 一次取出訊息的數量

  long maxSize();// element 最大數量

  LocalDateTime expiredTime();

  void refreshLatestTime();

  long maxQueueCount();// queue 最大數量
}
