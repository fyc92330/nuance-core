package org.chun.internal;

public interface MessageEvent<T extends Message> {

  T get();
}
