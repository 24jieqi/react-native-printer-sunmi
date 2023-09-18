# react-native-printer-sunmi

## 特性

1. 基于行配置的打印方式，如果当前行只有一个单元格，内部调用 `service.printOriginalText`，否则内部调用表格打印 `service.printColumnsString`
2. 支持二维码打印，具体配置见示例
3. 支持标签打印

## 安装

`$ yarn add react-native-printer-sunmi --save`

## 使用方式

```javascript
import SunmiPrinter from 'react-native-printer-sunmi'
const content = {
    content: [
      // 单个文本
      {
        row: [
          {
            text: 'something to print',
            align: 0,
            fontSize: 20,
            bold: true
          },
        ]
        wrap: 1
      },
      // 表格打印
      {
        row: [
          {
            text: '1',
            align: 0,
            width: 1
          },
          {
            text: '2',
            align: 1,
            width: 1
          }
        ],
        fontSize: 14,
        bold: false,
        wrap: 4
      },
      // 二维码打印
      {
        data: `HJ_${options.orderId}`,
        modulesize: 9,
        errorlevel: 1,
        wrap: 1,
      },
    ]
  }
async function print() {
  try{
    await SunmiPrinter.connect()
    await SunmiPrinter.openPrinter(content, 0)
    await SunmiPrinter.disconnect()
    console.log('打印完成！')
  } catch() {}
}
```

### 常量

- `PrinterSunmi.DEVICES_NAME`设备标识
- `PrinterSunmi.SUPPORTED` 是否支持打印

### API

#### `SunmiPrinter.connect() => Promise<boolean>` 连接服务

#### `SunmiPrinter.getPrinterState() => Promise(PrinterState)` 获取打印机当前状态

#### `SunmiPrinter.openPrinter(options: IOption, mode: 0 | 1 | 2) => Promise` 开始打印（事务的方式）

> tips：`mode`传入打印模式，`0`正常模式 `1`黑标模式（暂不支持）`2`标签模式
> 使用标签模式之前，需要装入标签纸，并在"设置"=>"内置打印"修改打印模式为 `标签热敏`并点击标签学习后方可正常使用

#### `SunmiPrinter.disconnect() => Promise<boolean>` 服务断开连接

#### `SunmiPrinter.hasPrinter() => boolean` 检测是否已经连接服务

### 事件

- `onDisconnect` 打印机断开连接后触发事件（手动断开也会触发）

```js
React.useEffect(() => {
  const eventEmitter = new NativeEventEmitter(NativeModules.PrinterSunmi);
  const event = eventEmitter.addListener('onDisconnect', () => {
    console.log('打印机已断开连接！');
  });
  return () => {
    event.remove();
  };
}, []);
```

> 更多使用方式见 [example](https://github.com/hjfruit/react-native-printer-sunmi/tree/main/example)
