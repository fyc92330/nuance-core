package org.chun.internal.core;

import java.util.Map;

public interface MessageQueueFinder<T extends Message> {

	MessageQueue<T>[] getQueues();

	Class<? extends MessageQueue<T>>[] getQueueClasses();

	Map<String, MessageQueue<T>> getQueueMap();

}
