package com.reactnativeprintersunmi;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.sunmi.printerx.enums.Align;
import com.sunmi.printerx.enums.RenderColor;
import com.sunmi.printerx.style.BaseStyle;
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
      style.setPosX(option.getInt("posY"));
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
}
