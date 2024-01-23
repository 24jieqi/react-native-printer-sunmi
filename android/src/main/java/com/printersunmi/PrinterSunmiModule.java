package com.printersunmi;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.sunmi.printerx.api.PrintResult;
import com.sunmi.printerx.api.QueryApi;
import com.sunmi.printerx.enums.Command;
import com.sunmi.printerx.enums.DividingLine;
import com.sunmi.printerx.enums.PrinterInfo;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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

  private void sendEvent(ReactContext ctx, String eventName, ReadableMap data) {
    ctx.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, data);
  }
  private void sendPrintErrorEvent(String method, String message) {
    WritableMap payload = Arguments.createMap();
    payload.putString("method", method);
    payload.putString("message", message);
    sendEvent(reactContext, "PRINT_ERROR", payload);
  }
  @ReactMethod
  public void initLine(ReadableMap option) {
    if(currentPrinter != null) {
      try {
        currentPrinter.lineApi().initLine(Utils.getBaseStyle(option));
      } catch (SdkException e) {
        sendPrintErrorEvent("initLine", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void addText(String text, ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().addText(text, Utils.getTextStyle(style));
      } catch (SdkException e) {
        sendPrintErrorEvent("addText", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void printText(String text, ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().printText(text, Utils.getTextStyle(style));
      } catch (SdkException e) {
        sendPrintErrorEvent("printText", e.getMessage());
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
        sendPrintErrorEvent("printTexts", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void printBarCode(String code, ReadableMap config) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().printBarCode(code, Utils.getBarCodeStyle(config));
      } catch (SdkException e) {
        sendPrintErrorEvent("printBarCode", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void printQrCode(String code, ReadableMap config) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().printQrCode(code, Utils.getQrCodeStyle(config));
      } catch (SdkException e) {
        sendPrintErrorEvent("printQrCode", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void printBitmap(ReadableMap config) {
    String url = config.getString("url");
    if (currentPrinter != null) {
      try {
        Bitmap image = Glide.with(reactContext).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).submit().get();
        if (image.isRecycled()) {
          return;
        }
        currentPrinter.lineApi().printBitmap(image, Utils.getBitmapStyle(config));
      } catch (SdkException | ExecutionException | InterruptedException e) {
        sendPrintErrorEvent("printBitmap", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void printDividingLine(String style, int offset) {
    if (currentPrinter != null) {
      try {currentPrinter.lineApi().printDividingLine(DividingLine.valueOf(style), offset);
      } catch (SdkException e) {
        sendPrintErrorEvent("printDividingLine", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void autoOut() {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().autoOut();
      } catch (SdkException e) {
        sendPrintErrorEvent("autoOut", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void enableTransMode(boolean enable) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lineApi().enableTransMode(enable);
      } catch (SdkException e) {
        sendPrintErrorEvent("enableTransMode", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void printTrans(Promise promise) {
    if (currentPrinter == null) {
      promise.reject("NO_PRINTER", "not get printer or get printer fail");
    }
    try {
      currentPrinter.lineApi().printTrans(new PrintResult() {
        @Override
        public void onResult(int resultCode, String message) throws RemoteException {
          if (resultCode == 0) {
            promise.resolve(resultCode);
          } else {
            promise.reject(String.valueOf(resultCode), message);
          }
        }
      });
    } catch (SdkException e) {
      promise.reject("COMMIT_TRANS_FAILED", e.getMessage());
    }
  }
  @ReactMethod
  public void initCanvas(ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.canvasApi().initCanvas(Utils.getBaseStyle(style));
      } catch (SdkException e) {
        sendPrintErrorEvent("initCanvas", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void renderText(String text, ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.canvasApi().renderText(text, Utils.getTextStyle(style));
      } catch (SdkException e) {
        sendPrintErrorEvent("renderText", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void renderBarCode(String code, ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.canvasApi().renderBarCode(code, Utils.getBarCodeStyle(style));
      } catch (SdkException e) {
        sendPrintErrorEvent("renderBarCode", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void renderQrCode(String code, ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.canvasApi().renderQrCode(code, Utils.getQrCodeStyle(style));
      } catch (SdkException e) {
        sendPrintErrorEvent("renderQrCode", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void renderBitmap(ReadableMap style) {
    String url = style.getString("url");
    if (currentPrinter != null) {
      try {
        Bitmap image = Glide.with(reactContext).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).submit().get();
        currentPrinter.canvasApi().renderBitmap(image, Utils.getBitmapStyle(style));
      } catch (SdkException | ExecutionException | InterruptedException e) {
        sendPrintErrorEvent("renderBitmap", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void renderArea(ReadableMap style) {
    if (currentPrinter != null) {
      try {
        currentPrinter.canvasApi().renderArea(Utils.getAreaStyle(style));
      } catch (SdkException e) {
        sendPrintErrorEvent("renderArea", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void printCanvas(int count, Promise promise) {
    if (currentPrinter == null) {
      promise.reject("NO_PRINTER", "not get printer or get printer fail");
    }
    try {
      currentPrinter.canvasApi().printCanvas(count, new PrintResult() {
        @Override
        public void onResult(int resultCode, String message) throws RemoteException {
          if (resultCode == 0) {
            promise.resolve(resultCode);
          } else {
            promise.reject(String.valueOf(resultCode), message);
          }
        }
      });
    } catch (SdkException e) {
      promise.reject("PRINT_CANVAS_FAILED", e.getMessage());
    }
  }
  @ReactMethod
  public void printFile(String uri, ReadableMap style, Promise promise) {
    if (currentPrinter == null) {
      promise.reject("NO_PRINTER", "not get printer or get printer fail");
    }
    try {
      currentPrinter.fileApi().printFile(uri, Utils.getFileStyle(style), new PrintResult() {
        @Override
        public void onResult(int resultCode, String message) throws RemoteException {
          if (resultCode == 0) {
            promise.resolve(resultCode);
          } else {
            promise.reject(String.valueOf(resultCode), message);
          }
        }
      });
    } catch (SdkException e) {
      promise.reject("PRINT_FILE_FAILED", e.getMessage());
    }
  }
  @ReactMethod
  public void sendEscCommand(String esc) {
    if (currentPrinter != null) {
      try {
        byte[] content = esc.getBytes("gb18030");
        currentPrinter.commandApi().sendEscCommand(content);
      } catch (SdkException | UnsupportedEncodingException e) {
        sendPrintErrorEvent("sendEscCommand", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void sendTsplCommand(String tspl) {
    if (currentPrinter != null) {
      byte[] content = tspl.getBytes(StandardCharsets.UTF_8);
      try {
        currentPrinter.commandApi().sendTsplCommand(content);
      } catch (SdkException e) {
        sendPrintErrorEvent("sendTsplCommand", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void openCashDrawer() {
    if (currentPrinter != null) {
      try {
        currentPrinter.cashDrawerApi().open(null);
      } catch (SdkException e) {
        sendPrintErrorEvent("openCashDrawer", e.getMessage());
      }
    }
  }
  @ReactMethod
  public boolean isCashDrawerOpen() {
    try {
      return currentPrinter.cashDrawerApi().isOpen();
    } catch (SdkException e) {
      sendPrintErrorEvent("isCashDrawerOpen", e.getMessage());
    }
    return false;
  }
  @ReactMethod
  public void lcdConfig(String config) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lcdApi().config(Command.valueOf(config));
      } catch (SdkException e) {
        sendPrintErrorEvent("lcdConfig", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void lcdShowText(String content, ReadableMap config) {
    if (currentPrinter != null) {
      try {
        int size = config.hasKey("size") ? config.getInt("size") : 32;
        boolean fill = config.hasKey("fill") && config.getBoolean("fill");
        currentPrinter.lcdApi().showText(content, size, fill);
      } catch (SdkException e) {
        sendPrintErrorEvent("lcdShowText", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void lcdShowTexts(ReadableArray texts) {
    if (currentPrinter != null) {
      TextsParams params = Utils.formatTexts(texts);
      try {
        int[] align = params.getColSpans();
        currentPrinter.lcdApi().showTexts(params.getTexts(), align);
      } catch (SdkException e) {
        sendPrintErrorEvent("lcdShowTexts", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void lcdShowBitmap(String url) {
    if (currentPrinter != null) {
      try {
        Bitmap image = Glide.with(reactContext).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).submit().get();
        if (image.isRecycled()) {
          return;
        }
        currentPrinter.lcdApi().showBitmap(image);
      } catch (SdkException | ExecutionException | InterruptedException e) {
        sendPrintErrorEvent("lcdShowBitmap", e.getMessage());
      }
    }
  }
  @ReactMethod
  public void lcdShowDigital(String digital) {
    if (currentPrinter != null) {
      try {
        currentPrinter.lcdApi().showDigital(digital);
      } catch (SdkException e) {
        sendPrintErrorEvent("lcdShowDigital", e.getMessage());
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
    currentPrinter = null;
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
      result.putString("distance", api.getInfo(PrinterInfo.DISTANCE));
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
