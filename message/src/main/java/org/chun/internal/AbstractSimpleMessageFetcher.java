package org.chun.internal;

import java.util.List;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageFetchExecutor;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractSimpleMessageFetcher<T extends Message> implements MessageFetcher<T>, MessageFetchExecutor<T> {

  @Autowired
  private ApplicationContext applicationContext;


  public abstract void execute(List<T> messages);


  @Override
  public List<T> fetch(MessageQueue<T> messageQueue) {

    return messageQueue.get();
  }


  @Override
  public void consume(String queueId) {

    for (MessageQueue<?> queue : applicationContext.getBeansOfType(MessageQueue.class).values()) {
      if (queueId.equals(queue.id())) {
        List<T> messages = fetch((MessageQueue<T>) queue);
        execute(messages);
      }
    }
  }
}
