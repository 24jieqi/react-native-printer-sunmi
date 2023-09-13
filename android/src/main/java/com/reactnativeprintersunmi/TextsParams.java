package com.reactnativeprintersunmi;

import com.sunmi.printerx.style.TextStyle;

import java.util.ArrayList;
import java.util.Arrays;

public class TextsParams {
  private ArrayList<String> texts;
  private ArrayList<Integer> colSpans;
  private ArrayList<TextStyle> textStyles;

  public int[] getColSpans() {
    int[] res = new int[colSpans.size()];
    int i = 0;
    for (Integer num: colSpans) {
      res[i++] = num;
    }
    return res;
  }

  public String[] getTexts() {
    String[] arr = new String[texts.size()];
    return texts.toArray(arr);
  }

  public TextStyle[] getTextStyles() {
    TextStyle[] res = new TextStyle[textStyles.size()];
    int i = 0;
    for (TextStyle item: textStyles) {
      res[i++] = item;
    }
    return res;
  }

  public void setColSpans(ArrayList<Integer> colSpans) {
    this.colSpans = colSpans;
  }

  public void setTexts(ArrayList<String> texts) {
    this.texts = texts;
  }

  public void setTextStyles(ArrayList<TextStyle> textStyles) {
    this.textStyles = textStyles;
  }
}
