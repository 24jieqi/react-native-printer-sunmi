import * as React from 'react';

import {
  StyleSheet,
  View,
  Text,
  TouchableOpacity,
  NativeEventEmitter,
  NativeModules,
} from 'react-native';
import PrinterSunmi from 'react-native-printer-sunmi';

console.log(PrinterSunmi.DEVICES_NAME);
console.log(PrinterSunmi.SUPPORTED);

export default function App() {
  async function handlePress() {
    try {
      // connect service if not connected
      const success = await PrinterSunmi.connect();
      if (!success) {
        return;
      }
      const state = await PrinterSunmi.getPrinterState();
      console.log(state.state, state.desc);
      await PrinterSunmi.openPrinter(
        {
          content: [
            {
              row: [
                {
                  text: 'something to print',
                  align: 0,
                  fontSize: 20,
                  bold: true,
                },
              ],
              wrap: 1,
            },
            {
              data: '22',
              modulesize: 4,
              errorlevel: 1,
            },
          ],
        },
        2
      );
      PrinterSunmi.disconnect();
    } catch (error) {
      console.log('err', error);
    }
  }
  React.useEffect(() => {
    const eventEmitter = new NativeEventEmitter(NativeModules.PrinterSunmi);
    const event = eventEmitter.addListener('onDisconnect', () => {
      console.log('打印机已断开连接！');
    });
    return () => {
      event.remove();
    };
  }, []);
  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={handlePress}>
        <Text>Print</Text>
      </TouchableOpacity>
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
