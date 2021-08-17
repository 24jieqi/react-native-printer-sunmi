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
  function handlePress() {
    PrinterSunmi.connect()
      .then((success) => {
        console.log('success', success);
        PrinterSunmi.openPrinter({
          content: [
            {
              data: '22',
              modulesize: 4,
              errorlevel: 1,
            },
          ],
        });
      })
      .catch((err) => {
        console.log('err', err);
      });
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
