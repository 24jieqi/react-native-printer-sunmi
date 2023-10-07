import type { ImageSourcePropType } from 'react-native';

interface PrinterInfo {
  /**
   * 打印机实时状态
   */
  status: string;
  /**
   * 打印机硬件版本号
   */
  id: string;
  name: string;
  type: string;
  paper: string;
  /**
   * 打印机固件版本号
   */
  version: string;
  cutter: string;
  hot: string;
  density: string;
  distance: string;
}

type AlignType = 'LEFT' | 'RIGHT' | 'CENTER' | 'DEFAULT';
type RenderColorType = 'BLACK' | 'RED';
type RotateType = 'ROTATE_0' | 'ROTATE_90' | 'ROTATE_180' | 'ROTATE_270';
type HumanReadableType = 'HIDE' | 'POS_ONE' | 'POS_TWO' | 'POS_THREE';
type SymbologyType =
  | 'UPCA'
  | 'UPCE'
  | 'EAN13'
  | 'EAN8'
  | 'CODE39'
  | 'ITF'
  | 'CODABAR'
  | 'CODE93'
  | 'CODE128'
  | 'EAN128'
  | 'CODE128A'
  | 'ITF_C'
  | 'CODE39_S'
  | 'CODE39_C'
  | 'EAN13_2'
  | 'UPCA_2'
  | 'UPCA_5'
  | 'EAN13_5'
  | 'EAN8_2'
  | 'EAN8_5'
  | 'UPCE_2'
  | 'UPCE_5';

type ErrorLevelType = 'L' | 'M' | 'Q' | 'H';

type AlgorithmType = 'BINARIZATION' | 'DITHERING';

type DividingLineType = 'EMPTY' | 'SOLID' | 'DOTTED';

type ShapeStyleType =
  | 'RECT_FILL'
  | 'RECT_WHITE'
  | 'RECT_REVERSE'
  | 'BOX'
  | 'CIRCLE'
  | 'OVAL'
  | 'PATH';

type FileDuplexType = 'SINGLE' | 'DOUBLE_SHORT' | 'DOUBLE_LONG';
interface BaseStyle {
  align: AlignType;
  width: number;
  height: number;
  posX: number;
  posY: number;
  renderColor: RenderColorType;
}

interface CanvasBaseStyle
  extends Partial<Pick<BaseStyle, 'posX' | 'posY' | 'renderColor'>> {
  width: number;
  height: number;
}

interface TextStyle {
  textSize: number;
  textWidthRatio: number;
  textHeightRatio: number;
  textSpace: number;
  bold: boolean;
  underline: boolean;
  strikeThrough: boolean;
  italics: boolean;
  invert: boolean;
  antiColor: boolean;
  font: string;
  align: AlignType;
  height: number;
  width: number;
  posX: number;
  posY: number;
  rotate: RotateType;
}

interface BarCodeStyle {
  dotWidth: number;
  barHeight: number;
  readable: HumanReadableType;
  symbology: SymbologyType;
  align: AlignType;
  height: number;
  width: number;
  posX: number;
  posY: number;
  rotate: RotateType;
}

interface QrCodeStyle {
  dot: number;
  errorLevel: ErrorLevelType;
  align: AlignType;
  height: number;
  width: number;
  posX: number;
  posY: number;
  rotate: RotateType;
}

interface BitmapStyle {
  algorithm: AlgorithmType;
  value: number;
  align: AlignType;
  height: number;
  width: number;
  posX: number;
  posY: number;
}

interface ShapeStyle {
  style: ShapeStyleType;
  height: number;
  width: number;
  posX: number;
  posY: number;
  endX: number;
  endY: number;
  thick: number;
}

interface FileStyle {
  copies: number;
  duplex: FileDuplexType;
  collate: boolean;
  rotate: RotateType;
  start: number;
  end: number;
}

interface ColTextItem {
  text: string;
  span?: number;
  align?: AlignType;
}

export interface PrintErrorMessage {
  method: string;
  message: string;
}

export enum LCDCommod {
  INIT = 'INIT',
  WAKE = 'WAKE',
  SLEEP = 'SLEEP',
  CLEAR = 'CLEAR',
}

export interface LCDShowTextConfig {
  size: number;
  fill: boolean;
}

export interface LCDShowTextsItem {
  text: string;
  align: number;
}

export type PrinterSunmiType = {
  DEVICES_NAME: string;
  connect: () => Promise<boolean>;
  disconnect: () => void;
  getInfo: () => PrinterInfo;
  initLine: (
    option?: Partial<Pick<BaseStyle, 'align' | 'width' | 'height' | 'posX'>>
  ) => void;
  addText: (
    text: string,
    option?: Partial<
      Omit<TextStyle, 'align' | 'width' | 'height' | 'posX' | 'posY' | 'rotate'>
    >
  ) => void;
  printText: (
    text: string,
    option?: Partial<
      Omit<TextStyle, 'align' | 'width' | 'height' | 'posX' | 'posY' | 'rotate'>
    >
  ) => void;
  printTexts: (texts: ColTextItem[]) => void;
  printBarCode: (
    code: string,
    option?: Partial<
      Pick<
        BarCodeStyle,
        | 'dotWidth'
        | 'barHeight'
        | 'readable'
        | 'symbology'
        | 'align'
        | 'width'
        | 'height'
      >
    >
  ) => void;
  printQrCode: (
    code: string,
    option?: Partial<
      Pick<QrCodeStyle, 'dot' | 'errorLevel' | 'align' | 'width' | 'height'>
    >
  ) => void;
  printBitmap: (
    uri: string | ImageSourcePropType,
    option?: Partial<Omit<BitmapStyle, 'posX' | 'posY'>>
  ) => void;
  printDividingLine: (style: DividingLineType, offset: number) => void;
  autoOut: () => void;
  enableTransMode: (enable: boolean) => void;
  printTrans: () => Promise<number>;
  initCanvas: (option: CanvasBaseStyle) => void;
  renderText: (text: string, option?: Partial<TextStyle>) => void;
  renderBarCode: (code: string, option?: Partial<BarCodeStyle>) => void;
  renderQrCode: (text: string, option?: Partial<QrCodeStyle>) => void;
  renderBitmap: (uri: string, option?: Partial<BitmapStyle>) => void;
  renderArea: (option?: Partial<ShapeStyle>) => void;
  printCanvas: (count: number) => Promise<number>;
  printFile: (uri: string, option?: Partial<FileStyle>) => Promise<number>;
  sendEscCommand: (content: string) => void;
  sendTsplCommand: (content: string) => void;
  openCashDrawer: () => void;
  isCashDrawerOpen: () => boolean;
  lcdConfig: (command: LCDCommod) => void;
  lcdShowText: (content: string, config: LCDShowTextConfig) => void;
  lcdShowTexts: (texts: LCDShowTextsItem[]) => void;
  lcdShowBitmap: (uri: string) => void;
  lcdShowDigital: (digital: string) => void;
  watchError: (
    errorHandler: (payload: PrintErrorMessage) => void
  ) => () => void;
};
