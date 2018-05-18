//
//  SettingsViewController.m
//  AgoraChorusDemo
//
//  Created by GongYuhua on 2016/9/12.
//  Copyright © 2016年 Agora. All rights reserved.
//

#import "SettingsViewController.h"
#import "ProfileCell.h"
#import "AgoraAudioKit.framework/Headers/AgoraRtcEngineKit.h"

@interface SettingsViewController () <UITableViewDataSource, UITableViewDelegate>
@property (weak, nonatomic) IBOutlet UITableView *profileTableView;
@property (strong, nonatomic) NSArray *profiles;
@end

@implementation SettingsViewController
- (NSArray *)profiles {
    if (!_profiles) {
        _profiles = @[@(AgoraVideoProfilePortrait120P),
                      @(AgoraVideoProfilePortrait180P),
                      @(AgoraVideoProfilePortrait240P),
                      @(AgoraVideoProfilePortrait360P),
                      @(AgoraVideoProfilePortrait480P),
                      @(AgoraVideoProfilePortrait720P)];
    }
    return _profiles;
}

- (void)setVideoProfile:(AgoraVideoProfile)videoProfile {
    _videoProfile = videoProfile;
    [self.profileTableView reloadData];
}

- (IBAction)doConfirmPressed:(UIButton *)sender {
    if ([self.delegate respondsToSelector:@selector(settingsVC:didSelectProfile:)]) {
        [self.delegate settingsVC:self didSelectProfile:self.videoProfile];
    }
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.profiles.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    ProfileCell *cell = [tableView dequeueReusableCellWithIdentifier:@"profileCell" forIndexPath:indexPath];
    AgoraVideoProfile selectedProfile = [self.profiles[indexPath.row] integerValue];
    [cell updateWithProfile:selectedProfile isSelected:(selectedProfile == self.videoProfile)];
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    AgoraVideoProfile selectedProfile = [self.profiles[indexPath.row] integerValue];
    self.videoProfile = selectedProfile;
}
@end
