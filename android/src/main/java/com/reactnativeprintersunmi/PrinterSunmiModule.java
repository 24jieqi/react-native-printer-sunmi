package com.reactnativeprintersunmi;

import android.os.Build;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.sunmi.printerx.PrinterSdk;
import com.sunmi.printerx.SdkException;
import com.sunmi.printerx.api.LineApi;
import com.sunmi.printerx.api.QueryApi;
import com.sunmi.printerx.enums.Align;
import com.sunmi.printerx.enums.PrinterInfo;
import com.sunmi.printerx.enums.RenderColor;
import com.sunmi.printerx.style.BaseStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@ReactModule(name = PrinterSunmiModule.NAME)
public class PrinterSunmiModule extends ReactContextBaseJavaModule {
  private PrinterSdk.Printer currentPrinter = null;
  private final ReactApplicationContext reactContext;
  public static final String NAME = "PrinterSunmi";

  public PrinterSunmiModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }
  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  private void sendEvent(ReactContext ctx, String eventName) {
    ctx.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, null);
  }
  @ReactMethod
  public void initLine(ReadableMap option) {
    if(currentPrinter != null) {
      try {
        currentPrinter.lineApi().initLine(Utils.getBaseStyle(option));
      } catch (SdkException e) {
        sendEvent(reactContext, "INIT_LINE_ERROR");
      }
    }
  }
  @ReactMethod
  public void addText(String text, ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().addText(text, Utils.getTextStyle(style));
      } catch (SdkException e) {
        sendEvent(reactContext, "ADD_TEXT_ERROR");
      }
    }
  }
  @ReactMethod
  public void printText(String text, ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().printText(text, Utils.getTextStyle(style));
      } catch (SdkException e) {
        sendEvent(reactContext, "PRINT_TEXT_ERROR");
      }
    }
  }
  @ReactMethod
  public void printTexts(ReadableArray texts) {
    if (currentPrinter != null) {
      try {
        TextsParams params = Utils.formatTexts(texts);
        currentPrinter.lineApi().printTexts(params.getTexts(), params.getColSpans(), params.getTextStyles());
      } catch (SdkException e) {
        sendEvent(reactContext, "PRINT_TEXT_ERROR");
      }
    }
  }
  @ReactMethod
  public void connect(final Promise promise)  {
    try {
      PrinterSdk.getInstance().getPrinter(reactContext, new PrinterSdk.PrinterListen() {
        @Override
        public void onDefPrinter(PrinterSdk.Printer printer) {
          currentPrinter = printer;
          promise.resolve(true);
        }
        @Override
        public void onPrinters(List<PrinterSdk.Printer> list) {

        }
      });
    } catch (SdkException e) {
      promise.reject("CONNECTING_FAILED", e.getMessage());
    }
  }
  @ReactMethod
  public void disconnect() {
    PrinterSdk.getInstance().destroy();
  }
  @ReactMethod
  public WritableMap getInfo() {
    WritableMap result = Arguments.createMap();
    if (currentPrinter == null) {
      return  result;
    }
    try {
      QueryApi api = currentPrinter.queryApi();
      result.putString("status", api.getStatus().name());
      result.putString("id", api.getInfo(PrinterInfo.ID));
      result.putString("name", api.getInfo(PrinterInfo.NAME));
      result.putString("type", api.getInfo(PrinterInfo.TYPE));
      result.putString("paper", api.getInfo(PrinterInfo.PAPER));
      result.putString("version", api.getInfo(PrinterInfo.VERSION));
      result.putString("cutter", api.getInfo(PrinterInfo.CUTTER));
      result.putString("hot", api.getInfo(PrinterInfo.HOT));
      result.putString("density", api.getInfo(PrinterInfo.DENSITY));
    } catch (SdkException e) {
      e.printStackTrace();
    }
    return result;
  }
  // 对外暴露的常量
  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    final String devicesName = Build.SERIAL.toUpperCase(Locale.ENGLISH);
    constants.put("DEVICES_NAME", devicesName); // 当前设备名
    return constants;
  }
}
