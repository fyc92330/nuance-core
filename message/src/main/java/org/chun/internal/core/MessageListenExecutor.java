package org.chun.internal.core;

import java.util.List;

public interface MessageListenExecutor<T extends MessageEvent<Message>> {

  void execute(List<T> events);

  MessageFetcher<?> fetcher();
}
