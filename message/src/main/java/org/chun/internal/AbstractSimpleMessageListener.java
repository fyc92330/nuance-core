package org.chun.internal;

import lombok.NonNull;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageEvent;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageListenExecutor;
import org.chun.internal.core.MessageListener;
import org.chun.internal.core.MessageQueue;

import java.util.List;

public abstract class AbstractSimpleMessageListener<T extends Message, E extends MessageEvent<T>> extends AbstractQueueFinder<T> implements MessageListener<T, E>, MessageListenExecutor<T, E> {


	public abstract MessageFetcher<T> fetcher();


	@Override
	public void execute(List<E> events, String key) {

		for (E event : events) {

			T message = convert(event);
			produce(message, key);
			getQueues().stream().map(MessageQueue::id).forEach(this::call);
		}
	}


	@Override
	public T convert(E event) {

		return event.get();
	}


	@Override
	public void produce(@NonNull T message, String key) {

		for (MessageQueue<T> queue : getQueues()) {

			String id = queue.id();
			if (id.equals(key)) {
				queue.add(message);
			}
		}
	}

	@Override
	public void produceMultiple(T message, String[] keys) {

		for (String key : keys) {
			this.produce(message, key);
		}
	}


	@Override
	public void call(String queueId) {

		fetcher().consume(queueId);
	}

}
