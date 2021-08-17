import { NativeModules } from 'react-native';

// 按行打印内容相关配置
interface IText {
  text: string;
  width?: number;
  align?: 0 | 1 | 2;
  fontSize?: number;
  bold?: boolean;
}

interface IQRCodeRowContent {
  data: string;
  modulesize: 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16;
  errorlevel: 0 | 1 | 2 | 3;
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

type IRow = IRowContent | IQRCodeRowContent;
export interface IConfig {
  printerStyle?: IPrinterStyle;
  content: IRow[];
}
export interface PrinterState {
  state: string;
  desc: string;
}

type PrinterSunmiType = {
  DEVICES_NAME: string;
  SUPPORTED: boolean;
  connect: () => Promise<boolean>;
  openPrinter: (config: IConfig) => Promise<any>;
  getPrinterState: () => PrinterState;
};

const { PrinterSunmi } = NativeModules;

export default PrinterSunmi as PrinterSunmiType;
