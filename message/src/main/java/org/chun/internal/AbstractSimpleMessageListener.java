package org.chun.internal;

import java.util.List;
import lombok.NonNull;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageEvent;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageListenExecutor;
import org.chun.internal.core.MessageListener;
import org.chun.internal.core.MessageQueue;
import org.chun.internal.core.MessageQueueFinder;

public abstract class AbstractSimpleMessageListener<T extends Message, E extends MessageEvent<T>> implements MessageListener<T, E>, MessageListenExecutor<T, E>, MessageQueueFinder<T> {

  public abstract MessageQueue<T> getQueue();

  public abstract Class<MessageQueue<T>> getQueueClass();

  public abstract MessageFetcher<T> fetcher();


  @Override
  public void execute(List<E> events) {

    for (E event : events) {

      T message = convert(event);
      produce(message);
      call(getQueue().id());
    }
  }


  @Override
  public T convert(E event) {

    return event.get();
  }


  @Override
  public void produce(@NonNull T message) {

    getQueue().add(message);
  }


  @Override
  public void call(String queueId) {

    fetcher().consume(queueId);
  }
}
