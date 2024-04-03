package org.chun.internal.core;

public interface MessageListener<T extends MessageEvent<Message>> {

  Message convert(T event);

  void produce(Message message);

  void call(String queueId); // call 對應的 fetcher
}
