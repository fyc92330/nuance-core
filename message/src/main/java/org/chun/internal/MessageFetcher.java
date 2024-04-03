package org.chun.internal;

public interface MessageFetcher<T extends Message, R extends MessageQueue<T>> {

  T fetch(String queueId);

  T fetchById(String messageId, String queueId);

  R queue(String queueId); // 取得指定的queue, 加入 queue中直接執行

  void consume(T message);
}
