package org.chun.enums;

public enum MessageExchangeType {

  DIRECT,// Exchange 綁定 Queue
  TOPIC,// Exchange 推送至規則符合的 Queue
  FANOUT,// Exchange 推送至所有的 Queue
  ;

}
