import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import PrinterSunmi from 'react-native-printer-sunmi';

export default function App() {
  function handlePress() {
    PrinterSunmi.connect()
      .then((success) => {
        console.log('success', success);
        PrinterSunmi.openPrinter({
          content: [
            {
              row: [
                {
                  text: 'hello world',
                },
              ],
            },
          ],
        });
      })
      .catch((err) => {
        console.log('err', err);
      });
  }
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
