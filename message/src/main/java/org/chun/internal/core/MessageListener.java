package org.chun.internal.core;

public interface MessageListener<T extends Message, E extends MessageEvent<T>> {

  T convert(E event);

  void produce(T message, String key);

  void produceMultiple(T message, String[] keys);

  void call(String queueId); // call 對應的 fetcher
}
