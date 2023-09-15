import * as React from 'react';

import { StyleSheet, View, Text } from 'react-native';
import { Button, Space } from '@fruits-chain/react-native-xiaoshu';
import PrinterSunmi from 'react-native-printer-sunmi';

export default function App() {
  const [result, setResult] = React.useState<boolean>(false);

  React.useEffect(() => {
    PrinterSunmi.connect().then(() => {
      setResult(true);
    });
    const close = PrinterSunmi.watchError((payload) => {
      console.log(payload.method, payload.message);
    });
    return () => {
      close();
    };
  }, []);
  function handlePrintTexts() {
    if (!result) {
      return;
    }
    PrinterSunmi.enableTransMode(true);
    PrinterSunmi.initLine({ align: 'CENTER' });
    PrinterSunmi.printText('测试小票打印', { textSize: 32, bold: true });
    PrinterSunmi.addText('购物列表\n', { bold: true });
    PrinterSunmi.initLine();
    PrinterSunmi.addText('小计：3件\n');
    PrinterSunmi.printTexts([
      { text: '商品', span: 2 },
      { text: '价格', span: 1, align: 'RIGHT' },
      { text: '数量', span: 1, align: 'RIGHT' },
    ]);
    PrinterSunmi.printTexts([
      { text: '鲜榴莲', span: 2 },
      { text: '22', span: 1, align: 'RIGHT' },
      { text: 'X3', span: 1, align: 'RIGHT' },
    ]);
    PrinterSunmi.printDividingLine('EMPTY', 24);
    PrinterSunmi.printDividingLine('SOLID', 2);
    PrinterSunmi.printDividingLine('EMPTY', 24);
    PrinterSunmi.printTrans();
  }
  function handlePrintBarCode() {
    if (!result) {
      return;
    }
    PrinterSunmi.enableTransMode(true);
    PrinterSunmi.printBarCode('ABC-abc-1234', { align: 'CENTER' });
    PrinterSunmi.printTrans();
  }
  function handlePrintQrCode() {
    PrinterSunmi.enableTransMode(true);
    PrinterSunmi.printQrCode('123', { align: 'CENTER', dot: 12 });
    PrinterSunmi.printTrans();
  }
  function handlePrintBitmap() {
    PrinterSunmi.enableTransMode(true);
    PrinterSunmi.printBitmap(require('./image.png'), {
      width: 300,
      height: 300,
      align: 'CENTER',
    });
    PrinterSunmi.printTrans();
  }
  return (
    <View style={styles.container}>
      <Text>初始化状态: {result ? '成功' : '失败'}</Text>
      <Space>
        <Button onPress={handlePrintTexts}>打印文本</Button>
        <Button onPress={handlePrintBarCode}>打印条形码</Button>
        <Button onPress={handlePrintQrCode}>打印二维码</Button>
        <Button onPress={handlePrintBitmap}>打印图片</Button>
      </Space>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
