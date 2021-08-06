# react-native-printer-sunmi

## Getting started

`$ yarn add react-native-printer-sunmi --save`

## 使用方式

> 使用基于行配置的打印方式，如果当前行只有一个单元格，则调用`service.printOriginalText`，否则调用表格打印`service.printColumnsString`，表格行打印模式下，字体大小和粗细只能设置到行。

```javascript
import SunmiPrinter from 'react-native-printer-sunmi'

SunmiPrinter.connect().then(() => {
  SunmiPrinter.openPrinter({
    printerStyle: {
      // LEFT_SPACING: 50,
      // TEXT_RIGHT_SPACING: 50,
    },
    content: [
      // single text
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
      {
        row: [
          {
            text: '1',
            align: 0,
            width: 1
          }
          {
            text: '2',
            align: 1,
            width: 1
          }
        ],
        fontSize: 14,
        bold: false,
        wrap: 4
      }
    ]
  })
})
```

### 常量

- `PrinterSunmi.DEVICES_NAME`设备标识
- `PrinterSunmi.SUPPORTED` 是否支持打印

### API

#### `SunmiPrinter.connect() => Promise<boolean>` 连接服务

#### `SunmiPrinter.getPrinterState() => PrinterState` 获取打印机当前状态

#### `SunmiPrinter.openPrinter(options: IOption) => Promise` 开始打印（事务的方式）

### 事件

- `onDisconnect` 打印机断开连接后触发事件

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
