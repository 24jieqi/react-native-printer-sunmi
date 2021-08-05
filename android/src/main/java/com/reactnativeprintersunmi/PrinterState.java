package com.reactnativeprintersunmi;

public enum PrinterState {
  FINE("1","打印机⼯作正常"),
  PREPARE("2","打印机准备中"),
  CONNECT_EXCEPTION("3","通讯异常"),
  OUT_OF_PAPER("4","缺纸"),
  OVER_HEAT("5","过热"),
  LID_OPENED("6","开盖"),
  NO_PRINTER("505","未检测到打印机");
  PrinterState(String code, String msg){
    this.code = code;
    this.msg = msg;
  }
  final private String code;
  final private String msg;
  public String getCode(){
    return this.code;
  }
  public String getMsg(){
    return this.msg;
  }
}
