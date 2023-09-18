package com.printersunmi;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.sunmi.printerx.enums.Align;
import com.sunmi.printerx.enums.ErrorLevel;
import com.sunmi.printerx.enums.FileDuplex;
import com.sunmi.printerx.enums.HumanReadable;
import com.sunmi.printerx.enums.ImageAlgorithm;
import com.sunmi.printerx.enums.RenderColor;
import com.sunmi.printerx.enums.Rotate;
import com.sunmi.printerx.enums.Shape;
import com.sunmi.printerx.enums.Symbology;
import com.sunmi.printerx.style.AreaStyle;
import com.sunmi.printerx.style.BarcodeStyle;
import com.sunmi.printerx.style.BaseStyle;
import com.sunmi.printerx.style.BitmapStyle;
import com.sunmi.printerx.style.FileStyle;
import com.sunmi.printerx.style.QrStyle;
import com.sunmi.printerx.style.TextStyle;

import java.util.ArrayList;

public class Utils {
  public static BaseStyle getBaseStyle(ReadableMap option) {
    BaseStyle style = BaseStyle.getStyle();
    if (option.hasKey("align")) {
      style.setAlign(Align.valueOf(option.getString("align")));
    }
    if (option.hasKey("height")) {
      style.setHeight(option.getInt("height"));
    }
    if (option.hasKey("width")) {
      style.setWidth(option.getInt("width"));
    }
    if (option.hasKey("posX")) {
      style.setPosX(option.getInt("posX"));
    }
    if (option.hasKey("posY")) {
      style.setPosY(option.getInt("posY"));
    }
    if (option.hasKey("renderColor")) {
      style.setRenderColor(RenderColor.valueOf(option.getString("renderColor")));
    }
    return style;
  }
  public static TextStyle getTextStyle(ReadableMap option) {
    TextStyle style = TextStyle.getStyle();
    if (option.hasKey("textSize")) {
      style.setTextSize(option.getInt("textSize"));
    }
    if (option.hasKey("textWidthRatio")) {
      style.setTextWidthRatio(option.getInt("textWidthRatio"));
    }
    if (option.hasKey("textHeightRatio")) {
      style.setTextHeightRatio(option.getInt("textWidthRatio"));
    }
    if (option.hasKey("textSpace")) {
      style.setTextSpace(option.getInt("textSpace"));
    }
    if (option.hasKey("bold")) {
      style.enableBold(option.getBoolean("bold"));
    }
    if (option.hasKey("underline")) {
      style.enableUnderline(option.getBoolean("underline"));
    }
    if (option.hasKey("strikeThrough")) {
      style.enableStrikethrough(option.getBoolean("strikeThrough"));
    }
    if (option.hasKey("italics")) {
      style.enableItalics(option.getBoolean("italics"));
    }
    if (option.hasKey("invert")) {
      style.enableInvert(option.getBoolean("invert"));
    }
    if (option.hasKey("antiColor")) {
      style.enableAntiColor(option.getBoolean("antiColor"));
    }
    if (option.hasKey("font")) {
      style.setFont(option.getString("font"));
    }
    if (option.hasKey("align")) {
      style.setAlign(Align.valueOf(option.getString("align")));
    }
    if (option.hasKey("height")) {
      style.setHeight(option.getInt("height"));
    }
    if (option.hasKey("width")) {
      style.setWidth(option.getInt("width"));
    }
    if (option.hasKey("posX")) {
      style.setPosX(option.getInt("posX"));
    }
    if (option.hasKey("posY")) {
      style.setPosY(option.getInt("posY"));
    }
    if (option.hasKey("rotate")) {
      style.setRotate(Rotate.valueOf(option.getString("rotate")));
    }
    return style;
  }
  public static TextsParams formatTexts(ReadableArray row) {
    ArrayList<String> texts = new ArrayList<>();
    ArrayList<Integer> colSpans = new ArrayList<>();
    ArrayList<TextStyle> textStyles = new ArrayList<>();
    int size = row.size();
    for (int i=0; i<size; i++) {
      ReadableMap item = row.getMap(i);
      String text = item.getString("text");
      int colSpan = item.hasKey("span") ? item.getInt("span") : 1;
      String align = item.hasKey("align") ? item.getString("align") : "LEFT";
      texts.add(text);
      colSpans.add(colSpan);
      textStyles.add(TextStyle.getStyle().setAlign(Align.valueOf(align)));
    }
    TextsParams params = new TextsParams();
    params.setTexts(texts);
    params.setColSpans(colSpans);
    params.setTextStyles(textStyles);
    return params;
  }
  public static BarcodeStyle getBarCodeStyle(ReadableMap option) {
    BarcodeStyle style = BarcodeStyle.getStyle();
    if (option.hasKey("dotWidth")) {
      style.setDotWidth(option.getInt("dotWidth"));
    }
    if (option.hasKey("barHeight")) {
      style.setBarHeight(option.getInt("barHeight"));
    }
    if (option.hasKey("readable")) {
      style.setReadable(HumanReadable.valueOf(option.getString("readable")));
    }
    if (option.hasKey("symbology")) {
      style.setSymbology(Symbology.valueOf(option.getString("symbology")));
    }
    if (option.hasKey("align")) {
      style.setAlign(Align.valueOf(option.getString("align")));
    }
    if (option.hasKey("width")) {
      style.setWidth(option.getInt("width"));
    }
    if (option.hasKey("height")) {
      style.setHeight(option.getInt("height"));
    }
    if (option.hasKey("posX")) {
      style.setPosX(option.getInt("posX"));
    }
    if (option.hasKey("posY")) {
      style.setPosY(option.getInt("posY"));
    }
    if (option.hasKey("rotate")) {
      style.setRotate(Rotate.valueOf(option.getString("rotate")));
    }
    return style;
  }
  public static QrStyle getQrCodeStyle(ReadableMap option) {
    QrStyle style = QrStyle.getStyle();
    if (option.hasKey("dot")) {
      style.setDot(option.getInt("dot"));
    }
    if (option.hasKey("errorLevel")) {
      style.setErrorLevel(ErrorLevel.valueOf(option.getString("errorLevel")));
    }
    if (option.hasKey("align")) {
      style.setAlign(Align.valueOf(option.getString("align")));
    }
    if (option.hasKey("width")) {
      style.setWidth(option.getInt("width"));
    }
    if (option.hasKey("height")) {
      style.setHeight(option.getInt("height"));
    }
    if (option.hasKey("posX")) {
      style.setPosX(option.getInt("posX"));
    }
    if (option.hasKey("posY")) {
      style.setPosY(option.getInt("posY"));
    }
    if (option.hasKey("rotate")) {
      style.setRotate(Rotate.valueOf(option.getString("rotate")));
    }
    return style;
  }
  public static BitmapStyle getBitmapStyle (ReadableMap option) {
    BitmapStyle style = BitmapStyle.getStyle();
    if (option.hasKey("algorithm")) {
      style.setAlgorithm(ImageAlgorithm.valueOf(option.getString("algorithm")));
    }
    if (option.hasKey("value")) {
      style.setValue(option.getInt("value"));
    }
    if (option.hasKey("align")) {
      style.setAlign(Align.valueOf(option.getString("align")));
    }
    if (option.hasKey("width")) {
      style.setWidth(option.getInt("width"));
    }
    if (option.hasKey("height")) {
      style.setHeight(option.getInt("height"));
    }
    if (option.hasKey("posX")) {
      style.setPosX(option.getInt("posX"));
    }
    if (option.hasKey("posY")) {
      style.setPosY(option.getInt("posY"));
    }
    return style;
  }
  public static AreaStyle getAreaStyle(ReadableMap option) {
    AreaStyle style = AreaStyle.getStyle();
    if (option.hasKey("style")) {
      style.setStyle(Shape.valueOf(option.getString("style")));
    }
    if (option.hasKey("width")) {
      style.setWidth(option.getInt("width"));
    }
    if (option.hasKey("height")) {
      style.setHeight(option.getInt("height"));
    }
    if (option.hasKey("posX")) {
      style.setPosX(option.getInt("posX"));
    }
    if (option.hasKey("posY")) {
      style.setPosY(option.getInt("posY"));
    }
    if (option.hasKey("endX")) {
      style.setEndX(option.getInt("endX"));
    }
    if (option.hasKey("endY")) {
      style.setEndY(option.getInt("endY"));
    }
    if (option.hasKey("thick")) {
      style.setThick(option.getInt("thick"));
    }
    return style;
  }
  public static FileStyle getFileStyle(ReadableMap option) {
    FileStyle style = FileStyle.getStyle();
    if (option.hasKey("copies")) {
      style.setFileCopies(option.getInt("copies"));
    }
    if (option.hasKey("duplex")) {
      style.setFileDuplex(FileDuplex.valueOf(option.getString("duplex")));
    }
    if (option.hasKey("collate")) {
      style.setFileCollate(option.getBoolean("collate"));
    }
    if (option.hasKey("rotate")) {
      style.setFileRotate(Rotate.valueOf(option.getString("rotate")));
    }
    if (option.hasKey("start")) {
      style.setFileStart(option.getInt("start"));
    }
    if (option.hasKey("end")) {
      style.setFileEnd(option.getInt("end"));
    }
    return style;
  }
}
