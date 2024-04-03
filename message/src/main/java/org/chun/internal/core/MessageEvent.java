package org.chun.internal.core;

public interface MessageEvent<T extends Message> {

  T get();
}
