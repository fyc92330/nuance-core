package org.chun.internal.core;

public interface MessageQueueFinder<T extends Message> {

  MessageQueue<T> getQueue();

  Class<? extends MessageQueue<T>> getQueueClass();

}
