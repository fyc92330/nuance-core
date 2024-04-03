package org.chun.internal.core;

public interface MessageListener<T extends Message, E extends MessageEvent<T>> {

  T convert(E event);

  void produce(T message);

  void call(String queueId); // call 對應的 fetcher
}
