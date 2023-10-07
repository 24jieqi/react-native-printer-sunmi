import {
  Image,
  type ImageSourcePropType,
  NativeEventEmitter,
  NativeModules,
  Platform,
} from 'react-native';
import type { PrintErrorMessage, PrinterSunmiType } from './typing';

const LINKING_ERROR =
  `The package 'react-native-printer-sunmi' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const PrinterSunmi = NativeModules.PrinterSunmi
  ? NativeModules.PrinterSunmi
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

const PrinterAPIs: PrinterSunmiType = {
  connect: PrinterSunmi.connect,
  disconnect: PrinterSunmi.disconnect,
  getInfo: PrinterSunmi.getInfo,
  DEVICES_NAME: PrinterSunmi.DEVICES_NAME,
  initLine: (option = {}) => {
    PrinterSunmi.initLine(option);
  },
  addText(text, option = {}) {
    PrinterSunmi.addText(text, option);
  },
  printText(text, option = {}) {
    PrinterSunmi.printText(text, option);
  },
  printTexts: PrinterSunmi.printTexts,
  printBarCode(code, option = {}) {
    PrinterSunmi.printBarCode(code, option);
  },
  printQrCode(code, option = {}) {
    PrinterSunmi.printQrCode(code, option);
  },
  printBitmap(uri, option = {}) {
    const path = Image.resolveAssetSource(uri as ImageSourcePropType);
    PrinterSunmi.printBitmap({ ...option, url: path?.uri || uri });
  },
  printDividingLine: PrinterSunmi.printDividingLine,
  autoOut: PrinterSunmi.autoOut,
  enableTransMode: PrinterSunmi.enableTransMode,
  printTrans: PrinterSunmi.printTrans,
  initCanvas: PrinterSunmi.initCanvas,
  renderText(text, option = {}) {
    PrinterSunmi.renderText(text, option);
  },
  renderBarCode(code, option = {}) {
    PrinterSunmi.renderBarCode(code, option);
  },
  renderQrCode(text, option = {}) {
    PrinterSunmi.renderQrCode(text, option);
  },
  renderBitmap(uri, option) {
    const path = Image.resolveAssetSource(uri as ImageSourcePropType);
    PrinterSunmi.renderBitmap({ ...option, url: path?.uri || uri });
  },
  renderArea(option = {}) {
    PrinterSunmi.renderArea(option);
  },
  printCanvas: PrinterSunmi.printCanvas,
  printFile: PrinterSunmi.printFile,
  sendEscCommand: PrinterSunmi.sendEscCommand,
  sendTsplCommand: PrinterSunmi.sendTsplCommand,
  openCashDrawer: PrinterSunmi.openCashDrawer,
  isCashDrawerOpen: PrinterSunmi.isCashDrawerOpen,
  lcdConfig: PrinterSunmi.lcdConfig,
  lcdShowText: PrinterSunmi.lcdShowText,
  lcdShowTexts: PrinterSunmi.lcdShowTexts,
  lcdShowBitmap: (uri: string) => {
    const path = Image.resolveAssetSource(uri as ImageSourcePropType);
    PrinterSunmi.lcdShowBitmap(path);
  },
  lcdShowDigital: PrinterSunmi.lcdShowDigital,
  watchError(errorHandler) {
    const eventEmitter = new NativeEventEmitter(NativeModules.PrinterSunmi);
    const errorEvent = eventEmitter.addListener(
      'PRINT_ERROR',
      (payload: PrintErrorMessage) => {
        errorHandler(payload);
      }
    );
    return () => {
      errorEvent.remove();
    };
  },
};

export default PrinterAPIs;
