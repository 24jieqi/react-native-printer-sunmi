# react-native-sunmi-printer

## Getting started

`$ yarn add react-native-sunmi-printer --save`

## 使用方式

> 使用基于行配置的打印方式，如果当前行只有一个单元格，则调用`service.printOriginalText`，否则调用表格打印`service.printColumnsString`，表格行打印模式下，字体大小和粗细只能设置到行。

```javascript
import SunmiPrinter from 'react-native-sunmi-printer'

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

## API

### `SunmiPrinter.connect() => Promise` 连接服务

### `SunmiPrinter.selfCheck() => Promise` 启动自检

### `SunmiPrinter.openPrinter(options) => Promise` 打印

```js
// 按行打印内容相关配置（表格模式下不支持指定单个单元格字体大小和加粗）
  interface IText {
    text: string // 打印的字符串内容
    width?: number // 在表达打印模式下指定每列的相对宽度
    align?: 0 | 1 | 2 // 文本居中方式 0：居左 1：居中 2：居右
    fontSize?: number
    bold?: boolean // 是否加粗
  }
  interface IContent {
    row: IText[] // 打印行文本
    fontSize?: number // 指定单行字体
    bold?: boolean
    wrap?: number // 本行打印完后换行数
  }
  // 全局打印机相关配置（暂未实现）
  export interface IPrinterStyle {
    INVERT?: boolean
    ANTI_WHITE?: boolean
    BOLD?: Boolean
    DOUBLE_HEIGHT?: boolean
    DOUBLE_WIDTH?: boolean
    ILALIC?: boolean
    STRIKETHROUGH?: boolean
    UNDERLINE?: boolean
    ABSOLUATE_POSITION?: number
    LEFT_SPACING?: number
    LINE_SPACING?: number
    RELATIVE_POSITION?: number
    STRIKETHROUGH_STYLE?: any
    TEXT_RIGHT_SPACING?: number
  }
  export interface IOption {
    printerStyle?: IPrinterStyle
    content: IContent[]
  }
```
