import { NativeModules } from 'react-native';

type PrinterSunmiType = {
  multiply(a: number, b: number): Promise<number>;
};

const { PrinterSunmi } = NativeModules;

export default PrinterSunmi as PrinterSunmiType;
