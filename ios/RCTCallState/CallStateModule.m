//
//  GoelocationModule.m
//  RCTCallState
//
//  Created by lovebing on 2016/10/28.
//  Copyright © 2016年 lovebing.org. All rights reserved.
//

#import "CallStateModule.h"


@implementation CallStateModule
{
    CTCallCenter* _callCenter;
}

RCT_EXPORT_MODULE(CallState);

RCT_EXPORT_METHOD(startListener) {
    NSLog(@"startListener");
    if (_callCenter == nil) {
        _callCenter = [[CTCallCenter alloc] init];
    }
  
    _callCenter.callEventHandler = ^(CTCall *call) {
        NSLog(@"---------------callStateUpdated-------%@",call.callState);
        NSDictionary *eventNameMap = @{
                                       CTCallStateConnected    : @"Connected",
                                       CTCallStateDialing      : @"Dialing",
                                       CTCallStateDisconnected : @"Disconnected",
                                       CTCallStateIncoming     : @"Incoming"
                                       };
        [self sendEvent:@"callStateUpdated" body:[eventNameMap objectForKey: call.callState]];
    };
}

RCT_EXPORT_METHOD(stopListener) {
    NSLog(@"stopListener");
    _callCenter = nil;

}

@end
