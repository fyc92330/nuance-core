package org.chun.internal;

import org.chun.internal.core.Message;
import org.chun.internal.core.MessageFetchExecutor;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class AbstractSimpleMessageFetcher<T extends Message> extends AbstractQueueFinder<T> implements MessageFetcher<T>, MessageFetchExecutor<T> {

	@Autowired
	private ApplicationContext applicationContext;


	public abstract void execute(List<T> messages);


	@Override
	public List<T> fetch(MessageQueue<T> messageQueue) {

		return messageQueue.get();
	}


	@Override
	public void consume(String queueId) {

		getQueueClasses().stream()
				.map(applicationContext::getBean)
				.filter(queue -> queueId.equals(queue.id()))
				.map(this::fetch)
				.forEach(this::execute);
	}

}
