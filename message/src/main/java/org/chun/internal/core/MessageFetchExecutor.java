package org.chun.internal.core;

import java.util.List;

public interface MessageFetchExecutor<T extends Message> {

  void execute(List<T> messages);
}
