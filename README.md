# react-native-printer-sunmi `v2`

> 基于商米新版 `Printx` SDK 实现的打印原生模块

### v1.x 基于旧版 SDK 的实现仍然可用，使用方式见[文档](./README.old.md)

## 安装

```sh
npm install react-native-printer-sunmi
```

## Usage

```js
import PinterSunmi from 'react-native-printer-sunmi';

// ...
async function print() {
  await PrinterSunmi.connect();
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
```

## API

> ⚠️ 钱箱控制接口&LCD 客显控制接口暂未测试

### connect: () => Promise`<boolean>` 连接/获取打印机

### disconnect: () => void 断开连接/释放 SDK

### watchError(errorHandler: (payload: PrintErrorMessage) => void) => () => void 异常监听

### getInfo: () => PrinterInfo 获取打印机信息

### 钱箱控制接口

#### openCashDrawer: () => void 开启钱箱

#### isCashDrawerOpen: () => boolean 获取钱箱状态

### LCD 客显控制接口

#### lcdConfig: (command: LCDCommod) => void 控制客显屏的状态

#### lcdShowText: (content: string, config: LCDShowTextConfig) => void 显示指定大小的文本内容

#### lcdShowTexts: (texts: LCDShowTextsItem[]) => void 显示多行文本内容

#### lcdShowBitmap: (uri: string) => void 显示位图图像

#### lcdShowDigital: (digital: string) => void 显示价格内容

> 其余用作打印的 `API`同[商米内置打印机服务文档](https://developer.sunmi.com/docs/zh-CN/xeghjk491/maceghjk502)，更多示例见[example](./example/src/App.tsx)

### 接下来

- [x] 打印文件接口
- [x] 指令集打印
- [x] 钱箱控制
- [x] LCD 客显控制接口
- [ ] 配置式 API

## License

MIT

---
