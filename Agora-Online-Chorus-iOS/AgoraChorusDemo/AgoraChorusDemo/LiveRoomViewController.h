//
//  LiveRoomViewController.h
//  AgoraChorusDemo
//
//  Created by GongYuhua on 2016/9/12.
//  Copyright © 2016年 Agora. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AgoraAudioKit.framework/Headers/AgoraRtcEngineKit.h"

@class LiveRoomViewController;
@protocol LiveRoomVCDelegate <NSObject>
- (void)liveVCNeedClose:(LiveRoomViewController *)liveVC;
@end

@interface LiveRoomViewController : UIViewController
@property (copy, nonatomic) NSString *roomName;
@property (assign, nonatomic) AgoraClientRole clientRole;
@property (assign, nonatomic) AgoraVideoProfile videoProfile;
@property (weak, nonatomic) id<LiveRoomVCDelegate> delegate;
@property (assign, nonatomic) NSInteger params;
@end
