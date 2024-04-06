package org.chun.internal.core;

import java.util.List;
import java.util.Map;

public interface MessageQueueFinder<T extends Message> {

	List<MessageQueue<T>> getQueues();

	List<Class<? extends MessageQueue<T>>> getQueueClasses();

	Map<String, MessageQueue<T>> getQueueMap();

}
