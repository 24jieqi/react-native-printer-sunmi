package com.reactnativeprintersunmi;

public enum PrinterMode {

  NORMAL(0,"普通模式"),
  DARK(1,"黑标模式"),
  LABEL(2,"标签模式"),
  ;
  private final int code;
  private final String desc;
  PrinterMode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }
  public int getCode(){
    return this.code;
  }
  public String getDesc(){
    return this.desc;
  }
  public static String getDescByCode(int code) {
    for (PrinterMode value : PrinterMode.values()) {
      if (value.code == code) {
        return value.getDesc();
      }
    }
    return "";
  }
}
