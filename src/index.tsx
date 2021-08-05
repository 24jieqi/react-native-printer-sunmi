import { NativeModules } from 'react-native';

// 按行打印内容相关配置
interface IText {
  text: string;
  width?: number;
  align?: 0 | 1 | 2;
  fontSize?: number;
  bold?: boolean;
}
export interface IRowContent {
  row: IText[]; // 打印行文本
  fontSize?: number; // 指定单行字体
  bold?: boolean;
  wrap?: number; // 本行打印完后换行数
}
// 打印机相关配置
export interface IPrinterStyle {
  INVERT?: boolean;
  ANTI_WHITE?: boolean;
  BOLD?: Boolean;
  DOUBLE_HEIGHT?: boolean;
  DOUBLE_WIDTH?: boolean;
  ILALIC?: boolean;
  STRIKETHROUGH?: boolean;
  UNDERLINE?: boolean;
  ABSOLUATE_POSITION?: number;
  LEFT_SPACING?: number;
  LINE_SPACING?: number;
  RELATIVE_POSITION?: number;
  STRIKETHROUGH_STYLE?: any;
  TEXT_RIGHT_SPACING?: number;
}
export interface IConfig {
  printerStyle?: IPrinterStyle;
  content: IRowContent[];
}

type PrinterSunmiType = {
  connect: () => Promise<boolean>;
  openPrinter: (config: IConfig) => Promise<any>;
  selfCheck: () => Promise<any>;
};

const { PrinterSunmi } = NativeModules;

export default PrinterSunmi as PrinterSunmiType;
