package org.chun.internal.core;

public interface MessageQueueFinder {

  MessageQueue<Message> getQueue();

  Class<MessageQueue<Message>> getQueueClass();

}
