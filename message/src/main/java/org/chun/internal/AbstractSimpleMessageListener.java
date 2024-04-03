package org.chun.internal;

import java.util.List;
import lombok.NonNull;
import org.chun.internal.core.Message;
import org.chun.internal.core.MessageEvent;
import org.chun.internal.core.MessageFetcher;
import org.chun.internal.core.MessageListenExecutor;
import org.chun.internal.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractSimpleMessageListener<T extends MessageEvent<Message>> implements MessageListener<T>, MessageListenExecutor<T> {

  @Autowired
  private SimpleMessageQueue<Message> queue;


  public abstract MessageFetcher<?> fetcher();


  @Override
  public void execute(List<T> events) {

    for (T event : events) {

      Message message = convert(event);
      produce(message);
      call(queue.id());
    }
  }


  @Override
  public Message convert(T event) {

    return event.get();
  }


  @Override
  public void produce(@NonNull Message message) {

    queue.add(message);
  }


  @Override
  public void call(String queueId) {

    fetcher().consume(queueId);
  }
}
