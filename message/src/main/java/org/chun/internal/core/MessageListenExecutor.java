package org.chun.internal.core;

import java.util.List;

public interface MessageListenExecutor<T extends Message, E extends MessageEvent<T>> {

  void execute(List<E> events);

  MessageFetcher<T> fetcher();
}
