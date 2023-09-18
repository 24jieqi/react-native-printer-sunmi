import * as React from 'react';

import { ScrollView } from 'react-native';
import { Button, Card, Cell, Space } from '@fruits-chain/react-native-xiaoshu';
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
      PrinterSunmi.disconnect();
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
  async function handleRenderText() {
    PrinterSunmi.initCanvas({ width: 320, height: 200 });
    PrinterSunmi.renderArea({
      style: 'BOX',
      posX: 0,
      posY: 0,
      width: 320,
      height: 198,
    });
    PrinterSunmi.renderText('打印标题', {
      textSize: 32,
      bold: true,
      posX: 10,
      posY: 24,
    });
    PrinterSunmi.printCanvas(1);
  }
  function handleRenderBarCode() {
    PrinterSunmi.initCanvas({ width: 320, height: 200 });
    PrinterSunmi.renderBarCode('ABC-abc-1234', {
      align: 'CENTER',
    });
    PrinterSunmi.printCanvas(1);
  }
  function handleRenderQrCode() {
    PrinterSunmi.initCanvas({ width: 300, height: 200 });
    PrinterSunmi.renderQrCode('123', {
      dot: 12,
    });
    PrinterSunmi.printCanvas(1);
  }
  function handleRenderBitmap() {
    PrinterSunmi.initCanvas({ width: 300, height: 200 });
    PrinterSunmi.renderBitmap(require('./image.png'), {
      width: 300,
      height: 200,
    });
    PrinterSunmi.printCanvas(1);
  }
  function handleRenderArea() {
    PrinterSunmi.initCanvas({ width: 300, height: 200 });
    PrinterSunmi.renderArea({
      style: 'RECT_FILL',
      posX: 20,
      posY: 20,
      width: 50,
      height: 50,
    });
    PrinterSunmi.printCanvas(1);
  }
  function handleRenderLabel() {
    PrinterSunmi.initCanvas({ width: 330, height: 240 });
    PrinterSunmi.renderArea({
      style: 'BOX',
      posX: 0,
      posY: 0,
      width: 330,
      height: 240,
    });
    PrinterSunmi.renderText('这是标题', {
      posX: 165,
      posY: 5,
    });
    PrinterSunmi.renderQrCode('1234', {
      posX: 5,
      posY: 30,
      width: 130,
      height: 130,
    });
    PrinterSunmi.renderText('这是商品描述，多行的商品描述', {
      posX: 140,
      posY: 30,
      width: 180,
      height: 120,
    });
    PrinterSunmi.renderBarCode('ABC-abc-1234', {
      posX: 15,
      posY: 180,
      width: 300,
      height: 130,
    });
    PrinterSunmi.printCanvas(1);
  }
  return (
    <ScrollView>
      <Space direction="vertical">
        <Cell title="初始化状态" value={result ? '成功' : '失败'} />
        <Card title="热敏打印">
          <Space>
            <Button onPress={handlePrintTexts}>打印文本</Button>
            <Button onPress={handlePrintBarCode}>打印条形码</Button>
            <Button onPress={handlePrintQrCode}>打印二维码</Button>
            <Button onPress={handlePrintBitmap}>打印图片</Button>
          </Space>
        </Card>
        <Card title="标签打印">
          <Space>
            <Button onPress={handleRenderText}>绘制文本</Button>
            <Button onPress={handleRenderBarCode}>绘制条形码</Button>
            <Button onPress={handleRenderQrCode}>绘制二维码</Button>
            <Button onPress={handleRenderBitmap}>绘制图片</Button>
            <Button onPress={handleRenderArea}>绘制特殊图形</Button>
            <Button onPress={handleRenderLabel}>综合示例</Button>
          </Space>
        </Card>
      </Space>
    </ScrollView>
  );
}
