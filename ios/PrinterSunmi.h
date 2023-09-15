
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNPrinterSunmiSpec.h"

@interface PrinterSunmi : NSObject <NativePrinterSunmiSpec>
#else
#import <React/RCTBridgeModule.h>

@interface PrinterSunmi : NSObject <RCTBridgeModule>
#endif

@end
