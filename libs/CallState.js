import {
    requireNativeComponent,
    NativeModules,
    Platform,
    DeviceEventEmitter
} from 'react-native';

import React, {
    Component,
    PropTypes
} from 'react';

const _module = NativeModules.CallState;

export default {
    startListener() {
        return _module.startListener();
    },
    stopListener() {
        return _module.stopListener();
    }
};
