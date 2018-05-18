//
//  ViewController.m
//  AgoraChooseSongDemo
//
//  Created by zhanxiaochao on 2018/3/6.
//  Copyright © 2018年 zhanxiaochao. All rights reserved.
//

#import "ViewController.h"


#import "AgoraAudioKit.framework/Headers/AgoraRtcEngineKit.h"
@interface ViewController ()<AgoraRtcEngineDelegate>

@property (weak, nonatomic) IBOutlet UIButton *start_voice;
@property(nonatomic, strong) AgoraRtcEngineKit *rtcEngine;

@end


@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.title = @"Agora 点歌台";
    [[UIApplication sharedApplication] setIdleTimerDisabled: YES];
    [self loadEngineKit];
    [self.start_voice addTarget:self action:@selector(voice_btn_click) forControlEvents:UIControlEventTouchUpInside];
}
-(void)voice_btn_click
{
    NSString *path = [[NSBundle mainBundle] pathForResource:@"456.mp3" ofType:nil];
    [self.rtcEngine startAudioMixing:path loopback:false replace:true cycle:100];
    [self.rtcEngine adjustAudioMixingVolume:10];
}

-(void)loadEngineKit{
    
    self.rtcEngine = [AgoraRtcEngineKit sharedEngineWithAppId:@"aab8b8f5a8cd4469a63042fcfafe7063" delegate:self];
    [self.rtcEngine enableAudio];
    [self.rtcEngine disableVideo];
    [self.rtcEngine muteAllRemoteAudioStreams:true];
    [self.rtcEngine muteAllRemoteVideoStreams:true];
    [self.rtcEngine setDefaultAudioRouteToSpeakerphone:true];
    [self.rtcEngine setVideoProfile:AgoraVideoProfileLandscape360P_4 swapWidthAndHeight:true];
    [self.rtcEngine setParameters:@"{\"che.audio.lowlatency\":true}"];
    [self.rtcEngine setParameters:@"{\"rtc.lowlatency\":1}"];
    [self.rtcEngine joinChannelByToken:nil channelId:@"hyy"  info:nil uid:0 joinSuccess:^(NSString * _Nonnull channel, NSUInteger uid, NSInteger elapsed) {
    }];
    [self.rtcEngine setEnableSpeakerphone:true];
    [self.rtcEngine startPreview];
    

}
-(void)rtcEngine:(AgoraRtcEngineKit *)engine didJoinChannel:(NSString *)channel withUid:(NSUInteger)uid elapsed:(NSInteger)elapsed{
    NSLog(@"%@,%lu,%lu",channel,(unsigned long)uid,elapsed);
    
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine didJoinedOfUid:(NSUInteger)uid elapsed:(NSInteger)elapsed {
    
}

- (void)rtcEngine:(AgoraRtcEngineKit *)engine firstLocalVideoFrameWithSize:(CGSize)size elapsed:(NSInteger)elapsed {
    


}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}



- (IBAction)btn_click:(id)sender {
    
    NSString *path = [[NSBundle mainBundle] pathForResource:@"234.mp3" ofType:nil];
    [self.rtcEngine startAudioMixing:path loopback:false replace:true cycle:100];
    [self.rtcEngine adjustAudioMixingVolume:10];
    
}






- (IBAction)pauseClick:(id)sender {
    [self.rtcEngine pauseAudioMixing];
}

- (IBAction)stopClick:(id)sender {
    [self.rtcEngine stopAudioMixing];
}
- (IBAction)resumeClick:(id)sender {
    [self.rtcEngine resumeAudioMixing];
}



@end
