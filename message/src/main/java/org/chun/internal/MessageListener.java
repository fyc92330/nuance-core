package org.chun.internal;

public interface MessageListener<T extends MessageEvent<Message>, R extends MessageQueue<Message>> {

  Message convert(T event);

  R produce(String queueId);

  void call(String queueId); // call 對應的 fetcher
}
