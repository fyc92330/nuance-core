package org.chun.internal.core;

import java.util.List;

public interface MessageQueue<T extends Message> {

  String id();

  List<T> get();

  void add(T message);

  void add(T message, long timeout);
}
