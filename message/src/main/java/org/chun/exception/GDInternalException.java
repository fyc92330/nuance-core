package org.chun.exception;

import org.chun.enums.GDApiStatusType;

public class GDInternalException extends GDBaseException{

  public GDInternalException(GDApiStatusType statusType) {

    super(statusType);
  }


  public GDInternalException(GDApiStatusType statusType, String message) {

    super(statusType, message);
  }
}
