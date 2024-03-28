package org.chun.exception;

import org.chun.enums.GDApiStatusType;

public class GDCacheException extends GDBaseException {

  public GDCacheException(Throwable e) {

    super(GDApiStatusType.SERVER_ERROR, e);
  }
}
