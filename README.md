### Installation
npm install react-native-call-state --save

### Installation (Android)
- settings.gradle `
include ':react-native-call-state'
project(':react-native-call-state').projectDir = new File(settingsDir, '../node_modules/@remobile/react-native-call-state/android')`

- build.gradle `compile project(':react-native-call-state')`

- MainApplication`new CallStatePackage()`

### Installation (iOS)
- Project navigator->Libraries->Add Files to 选择 @remobile/react-native-call-state/ios/RCTCallState.xcodeproj
- Project navigator->Build Phases->Link Binary With Libraries 加入 libRCTCallState.a

### Usage 使用方法

    const CallState =  require('react-native-call-state');
    componentWillMount() {
        CallState.startListener();
        this.subscription = DeviceEventEmitter.addListener('callStateUpdated', data => { console.warn(JSON.stringify(data)); });
    }
    componentWillUnmount() {
        CallState.stopListener();
    }
