// SunmiPrinterModule.java

package com.reactnative.limoer.sunmiprinter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.RemoteException;
import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.InnerResultCallback;
import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.sunmi.peripheral.printer.WoyouConsts;

import java.util.ArrayList;
import java.util.Arrays;

public class SunmiPrinterModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private SunmiPrinterService sunmiPrinterService;

    public enum PrinterState {
        FINE("1","打印机⼯作正常"),
        PREPARE("2","打印机准备中"),
        CONNECT_EXCEPTION("3","通讯异常"),
        OUT_OF_PAPER("4","缺纸"),
        OVER_HEAT("5","过热"),
        LID_OPENED("6","开盖"),
        NO_PRIENTER("505","未检测到打印机");
        PrinterState(String code, String msg){
            this.code = code;
            this.msg = msg;
        }
        final private String code;
        final private String msg;
        private String getCode(){
            return this.code;
        }
        private String getMsg(){
            return this.msg;
        }

    }
    public SunmiPrinterModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "SunmiPrinter";
    }
    public void setGlobalPrinterStyle(final ReadableMap printerStyle) throws RemoteException {
        Boolean isInvert = printerStyle.hasKey("INVERT") ? printerStyle.getBoolean("INVERT") : null;
        Boolean isAntiWhite = printerStyle.hasKey("ANTI_WHITE") ? printerStyle.getBoolean("ANTI_WHITE") : null;
        boolean isBold = printerStyle.hasKey("BOLD") && printerStyle.getBoolean("BOLD");
        boolean isDoubleHeight = printerStyle.hasKey("DOUBLE_HEIGHT") && printerStyle.getBoolean("DOUBLE_HEIGHT");
        boolean isDoubleWidth = printerStyle.hasKey("DOUBLE_WIDTH") && printerStyle.getBoolean("DOUBLE_WIDTH");
        boolean ilalic = printerStyle.hasKey("ILALIC") && printerStyle.getBoolean("ILALIC");
        boolean isUnderline = printerStyle.hasKey("UNDERLINE") && printerStyle.getBoolean("UNDERLINE");
        Integer absPosition = printerStyle.hasKey("ABSOLUATE_POSITION") ? printerStyle.getInt("ABSOLUATE_POSITION") : null;
        Integer leftSpacing = printerStyle.hasKey("LEFT_SPACING") ? printerStyle.getInt("LEFT_SPACING") : null;
        Integer lineSpacing = printerStyle.hasKey("LINE_SPACING") ? printerStyle.getInt("LINE_SPACING") : null;
        Integer relativePosition = printerStyle.hasKey("LINE_SPACING") ? printerStyle.getInt("LINE_SPACING") : null;
        Integer rightSpacing = printerStyle.hasKey("TEXT_RIGHT_SPACING") ? printerStyle.getInt("TEXT_RIGHT_SPACING") : null;
        if (isInvert != null) {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_INVERT, isInvert ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        }
        if (isAntiWhite != null) {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_ANTI_WHITE, isAntiWhite ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        }
        sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_BOLD, isBold ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_DOUBLE_HEIGHT, isDoubleHeight ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_DOUBLE_WIDTH, isDoubleWidth ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_ILALIC, ilalic ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        sunmiPrinterService.setPrinterStyle(WoyouConsts.ENABLE_UNDERLINE, isUnderline ? WoyouConsts.ENABLE : WoyouConsts.DISABLE);
        if (absPosition != null) {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.SET_ABSOLUATE_POSITION, absPosition);
        }
        if (leftSpacing != null) {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.SET_LEFT_SPACING, leftSpacing);
        }
        if (lineSpacing != null) {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.SET_LINE_SPACING, lineSpacing);
        }
        if (relativePosition != null) {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.SET_RELATIVE_POSITION, relativePosition);
        }
        if (rightSpacing != null) {
            sunmiPrinterService.setPrinterStyle(WoyouConsts.SET_TEXT_RIGHT_SPACING, rightSpacing);
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private int[] toArray(Object[] arr) {
        int[] result = Arrays.stream(arr).mapToInt(e-> Integer.valueOf(e.toString()).intValue()).toArray();
        return  result;
    }

    @ReactMethod
    public void openPrinter(final ReadableMap options, final Promise promise) throws RemoteException {
        ReadableArray content = options.hasKey("content") ? options.getArray("content"): null;
        ReadableMap printerStyle = options.hasKey("printerStyle")? options.getMap("printerStyle") : null;
        // 设置全局打印机样式
//        setGlobalPrinterStyle(printerStyle);
        // 开始事务
        sunmiPrinterService.enterPrinterBuffer(true);
        for (int i = 0; i < content.size(); i++) {
            ReadableMap rowConfig = content.getMap(i);
            ReadableArray textRow = rowConfig.getArray("row");
            Integer fontSize = rowConfig.hasKey("fontSize") ? rowConfig.getInt("fontSize"): null;
            boolean bold = rowConfig.hasKey("bold") && rowConfig.getBoolean("bold");
            Integer wrap = rowConfig.hasKey("wrap") ? rowConfig.getInt("wrap") : null; // 打印完一行后走纸行数
            // 设置单行字体大小
            if (fontSize != null) {
                sunmiPrinterService.setFontSize(fontSize, null);
            }
            sunmiPrinterService.sendRAWData(bold ? new byte[]{0x1B,0x45,0x01} : new byte[]{0x1B,0x45,0x0}, null);
            // 绘制一般文本(单行)
            int size = textRow.size();
            if (size == 1) {
                ReadableMap item = textRow.getMap(0);
                String text = item.hasKey("text") ? item.getString("text") : "";
                int align = item.hasKey("align") ? item.getInt("align"): 0;
                Integer innerFontSize = item.hasKey("fontSize") ? item.getInt("fontSize"): null;
                boolean innerBold = item.hasKey("bold") && item.getBoolean("bold");
                sunmiPrinterService.setAlignment(align, null);
                if (innerFontSize != null) {
                    sunmiPrinterService.setFontSize(innerFontSize, null);
                }
                sunmiPrinterService.sendRAWData(innerBold ? new byte[]{0x1B,0x45,0x01} : new byte[]{0x1B,0x45,0x0}, null);
                sunmiPrinterService.printOriginalText(text + "\n", null);
            } else {
                // 绘制表格行
                ArrayList<String> textArr = new ArrayList<>();
                ArrayList<Integer> widthArr = new ArrayList<>();
                ArrayList<Integer> alignArr = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    ReadableMap atom = textRow.getMap(j); // 单行的一个元素
                    String text = atom.hasKey("text") ? atom.getString("text") : "";
                    int align = atom.hasKey("align") ? atom.getInt("align"): 0;
                    int width = atom.hasKey("width") ? atom.getInt("width"): 1;
                    textArr.add(text);
                    widthArr.add(width);
                    alignArr.add(align);
                }
                sunmiPrinterService.printColumnsString(textArr.toArray(new String[textArr.size()]), toArray(widthArr.toArray()), toArray(alignArr.toArray()), null);
            }
            // 走纸
            if (wrap != null) {
                sunmiPrinterService.lineWrap(wrap, null);
            }
        }
        sunmiPrinterService.commitPrinterBufferWithCallback(new InnerResultCallback() {
            @Override
            public void onRunResult(boolean b) throws RemoteException {

            }

            @Override
            public void onReturnString(String s) throws RemoteException {

            }

            @Override
            public void onRaiseException(int i, String s) throws RemoteException {

            }

            @Override
            public void onPrintResult(int code, String s) throws RemoteException {
                if (code == 0) {
                    promise.resolve(s);
                } else {
                    promise.reject(s);
                }
            }
        });
    }
    @ReactMethod
    public void selfCheck(final Promise promise) throws RemoteException {
        int code = sunmiPrinterService.updatePrinterState();
        switch (code) {
            case 3:
                promise.reject(PrinterState.CONNECT_EXCEPTION.code, PrinterState.CONNECT_EXCEPTION.msg);
                break;
            case 4:
                promise.reject(PrinterState.OUT_OF_PAPER.code, PrinterState.OUT_OF_PAPER.msg);
                break;
            case 5:
                promise.reject(PrinterState.OVER_HEAT.code, PrinterState.OVER_HEAT.msg);
                break;
            case 6:
                promise.reject(PrinterState.LID_OPENED.code, PrinterState.LID_OPENED.msg);
                break;
            case 505:
                promise.reject(PrinterState.NO_PRIENTER.code, PrinterState.NO_PRIENTER.msg);
                break;
            default:
                promise.resolve(null);

        }
    }
    @ReactMethod
    public void connect(final Promise promise) throws InnerPrinterException {
        InnerPrinterCallback printerCallback = new InnerPrinterCallback() {
            @Override
            protected void onConnected(SunmiPrinterService service) {
                sunmiPrinterService = service;
                promise.resolve(null);
            }

            @Override
            protected void onDisconnected() {
            }
        };
        // 绑定服务
        InnerPrinterManager.getInstance().bindService(reactContext, printerCallback);

    }
}
