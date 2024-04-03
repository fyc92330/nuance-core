package org.chun.internal.core;

import java.util.List;
import java.util.Queue;

public interface MessageFetcher<T extends Message> {

  List<T> fetch(MessageQueue<T> messageQueue);// 從指定的 queue 取得 message

  void consume(String queueId);// 取得指定的queue 進行fetch
}
