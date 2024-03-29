/*
     File: AVCamDemoCaptureManager.m
 Abstract: Code that calls the AVCapture classes to implement the camera-specific features in the app such as recording, still image, camera exposure, white balance and so on.
  Version: 1.0
 
 Disclaimer: IMPORTANT:  This Apple software is supplied to you by Apple
 Inc. ("Apple") in consideration of your agreement to the following
 terms, and your use, installation, modification or redistribution of
 this Apple software constitutes acceptance of these terms.  If you do
 not agree with these terms, please do not use, install, modify or
 redistribute this Apple software.
 
 In consideration of your agreement to abide by the following terms, and
 subject to these terms, Apple grants you a personal, non-exclusive
 license, under Apple's copyrights in this original Apple software (the
 "Apple Software"), to use, reproduce, modify and redistribute the Apple
 Software, with or without modifications, in source and/or binary forms;
 provided that if you redistribute the Apple Software in its entirety and
 without modifications, you must retain this notice and the following
 text and disclaimers in all such redistributions of the Apple Software.
 Neither the name, trademarks, service marks or logos of Apple Inc. may
 be used to endorse or promote products derived from the Apple Software
 without specific prior written permission from Apple.  Except as
 expressly stated in this notice, no other rights or licenses, express or
 implied, are granted by Apple herein, including but not limited to any
 patent rights that may be infringed by your derivative works or by other
 works in which the Apple Software may be incorporated.
 
 The Apple Software is provided by Apple on an "AS IS" basis.  APPLE
 MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
 FOR A PARTICULAR PURPOSE, REGARDING THE APPLE SOFTWARE OR ITS USE AND
 OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.
 
 IN NO EVENT SHALL APPLE BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
 OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
 MODIFICATION AND/OR DISTRIBUTION OF THE APPLE SOFTWARE, HOWEVER CAUSED
 AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
 STRICT LIABILITY OR OTHERWISE, EVEN IF APPLE HAS BEEN ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.
 
 Copyright (C) 2010 Apple Inc. All Rights Reserved.
 
 */

#import "AVCamDemoCaptureManager.h"
#import <MobileCoreServices/UTCoreTypes.h>
#import <AssetsLibrary/AssetsLibrary.h>


@interface AVCamDemoCaptureManager (AVCaptureFileOutputRecordingDelegate) <AVCaptureFileOutputRecordingDelegate>
@end


@interface AVCamDemoCaptureManager ()

@property (nonatomic,retain) AVCaptureSession *session;
@property (nonatomic,retain) AVCaptureDeviceInput *videoInput;
@property (nonatomic,retain) AVCaptureDeviceInput *audioInput;
@property (nonatomic,retain) AVCaptureMovieFileOutput *movieFileOutput;
@property (nonatomic,retain) AVCaptureStillImageOutput *stillImageOutput;
@property (nonatomic,retain) AVCaptureVideoDataOutput *videoDataOutput;
@property (nonatomic,retain) AVCaptureAudioDataOutput *audioDataOutput;
@property (nonatomic,retain) id deviceConnectedObserver;
@property (nonatomic,retain) id deviceDisconnectedObserver;
@property (nonatomic,assign) UIBackgroundTaskIdentifier backgroundRecordingID;

@end

@interface AVCamDemoCaptureManager (Internal)

- (AVCaptureDevice *) cameraWithPosition:(AVCaptureDevicePosition)position;
- (AVCaptureDevice *) frontFacingCamera;
- (AVCaptureDevice *) backFacingCamera;
- (AVCaptureDevice *) audioDevice;
- (NSURL *) tempFileURL;

@end



@implementation AVCamDemoCaptureManager

@synthesize session = _session;
@synthesize videoInput = _videoInput;
@synthesize audioInput = _audioInput;
@synthesize movieFileOutput = _movieFileOutput;
@synthesize stillImageOutput = _stillImageOutput;
@dynamic flashMode;
@dynamic torchMode;
@dynamic focusMode;
@dynamic exposureMode;
@dynamic whiteBalanceMode;
@synthesize delegate = _delegate;
@synthesize videoDataOutput = _videoDataOutput;
@synthesize audioDataOutput = _audioDataOutput;
@synthesize backgroundRecordingID = _backgroundRecordingID;
@synthesize deviceConnectedObserver = _deviceConnectedObserver;
@synthesize deviceDisconnectedObserver = _deviceDisconnectedObserver;

- (id) init
{
    self = [super init];
    if (self != nil) {
        void (^deviceConnectedBlock)(NSNotification *) = ^(NSNotification *notification) {
            AVCaptureSession *session = [self session];
            AVCaptureDeviceInput *newAudioInput = [[AVCaptureDeviceInput alloc] initWithDevice:[self audioDevice] error:nil];
            AVCaptureDeviceInput *newVideoInput = [[AVCaptureDeviceInput alloc] initWithDevice:[self backFacingCamera] error:nil];
            
            [session beginConfiguration];
            [session removeInput:[self audioInput]];
            if ([session canAddInput:newAudioInput]) {                
                [session addInput:newAudioInput];
            }
            [session removeInput:[self videoInput]];
            if ([session canAddInput:newVideoInput]) {
                [session addInput:newVideoInput];
            }
            [session commitConfiguration];
            
            [self setAudioInput:newAudioInput];
            [newAudioInput release];
            [self setVideoInput:newVideoInput];
            [newVideoInput release];
            
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(deviceCountChanged)]) {
                [delegate deviceCountChanged];
            }
            
            if (![session isRunning])
                [session startRunning];
        };
        void (^deviceDisconnectedBlock)(NSNotification *) = ^(NSNotification *notification) {
            AVCaptureSession *session = [self session];
            
            [session beginConfiguration];
            
            if (![[[self audioInput] device] isConnected])
                [session removeInput:[self audioInput]];
            if (![[[self videoInput] device] isConnected])
                [session removeInput:[self videoInput]];
            
            [session commitConfiguration];
            
            [self setAudioInput:nil];
            
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(deviceCountChanged)]) {
                [delegate deviceCountChanged];
            }
            
            if (![session isRunning])
                [session startRunning];
        };
        NSNotificationCenter *notificationCenter = [NSNotificationCenter defaultCenter];
        [self setDeviceConnectedObserver:[notificationCenter addObserverForName:AVCaptureDeviceWasConnectedNotification object:nil queue:nil usingBlock:deviceConnectedBlock]];
        [self setDeviceDisconnectedObserver:[notificationCenter addObserverForName:AVCaptureDeviceWasDisconnectedNotification object:nil queue:nil usingBlock:deviceDisconnectedBlock]];            
    }
    return self;
}

- (void) dealloc
{
    [[self session] stopRunning];
    [self setSession:nil];
    [self setVideoInput:nil];
    [self setAudioInput:nil];
    [self setMovieFileOutput:nil];
    [self setStillImageOutput:nil];
    [self setVideoDataOutput:nil];
    [self setAudioDataOutput:nil];
    [super dealloc];
}

- (BOOL) setupSessionWithPreset:(NSString *)sessionPreset error:(NSError **)error
{
    BOOL success = NO;
    
    // Init the device inputs
    AVCaptureDeviceInput *videoInput = [[[AVCaptureDeviceInput alloc] initWithDevice:[self backFacingCamera] error:error] autorelease];
    [self setVideoInput:videoInput]; // stash this for later use if we need to switch cameras
    
    AVCaptureDeviceInput *audioInput = [[[AVCaptureDeviceInput alloc] initWithDevice:[self audioDevice] error:error] autorelease];
    [self setAudioInput:audioInput];
    
    // Setup the default file outputs
    AVCaptureStillImageOutput *stillImageOutput = [[[AVCaptureStillImageOutput alloc] init] autorelease];
    NSDictionary *outputSettings = [[NSDictionary alloc] initWithObjectsAndKeys:
                                    AVVideoCodecJPEG, AVVideoCodecKey,
                                    nil];
    [stillImageOutput setOutputSettings:outputSettings];
    [outputSettings release];
    [self setStillImageOutput:stillImageOutput];
    
    AVCaptureMovieFileOutput *movieFileOutput = [[AVCaptureMovieFileOutput alloc] init];
    [self setMovieFileOutput:movieFileOutput];
    [movieFileOutput release];
    
    // Setup and start the capture session
    AVCaptureSession *session = [[AVCaptureSession alloc] init];
    
    if ([session canAddInput:videoInput]) {
        [session addInput:videoInput];
    }
    if ([session canAddInput:audioInput]) {
        //[session addInput:audioInput];
    }
    if ([session canAddOutput:movieFileOutput]) {
        //[session addOutput:movieFileOutput];
    }
    if ([session canAddOutput:stillImageOutput]) {
        [session addOutput:stillImageOutput];
    }
    
    [session setSessionPreset:sessionPreset];
    [session startRunning];
    
    [self setSession:session];
    
    [session release];
    
    success = YES;
    
    return success;
}

- (NSUInteger) cameraCount
{
    return [[AVCaptureDevice devicesWithMediaType:AVMediaTypeVideo] count];
}

- (NSUInteger) micCount
{
    return [[AVCaptureDevice devicesWithMediaType:AVMediaTypeAudio] count];
}

- (BOOL) isRecording
{
    return [[self movieFileOutput] isRecording];
}

- (void) startRecording
{
    if ([[UIDevice currentDevice] isMultitaskingSupported]) {
        [self setBackgroundRecordingID:[[UIApplication sharedApplication] beginBackgroundTaskWithExpirationHandler:^{}]];
    }
    
    AVCaptureConnection *videoConnection = [AVCamDemoCaptureManager connectionWithMediaType:AVMediaTypeVideo fromConnections:[[self movieFileOutput] connections]];
    if ([videoConnection isVideoOrientationSupported]) {
        [videoConnection setVideoOrientation:AVCaptureVideoOrientationPortrait];
    }
    
    [[self movieFileOutput] startRecordingToOutputFileURL:[self tempFileURL]
                                        recordingDelegate:self];
}

- (void) stopRecording
{
    [[self movieFileOutput] stopRecording];
}

- (void) captureStillImage
{
    AVCaptureConnection *videoConnection = [AVCamDemoCaptureManager connectionWithMediaType:AVMediaTypeVideo fromConnections:[[self stillImageOutput] connections]];
    if ([videoConnection isVideoOrientationSupported]) {
        [videoConnection setVideoOrientation:AVCaptureVideoOrientationPortrait];
    }
    
    [[self stillImageOutput] captureStillImageAsynchronouslyFromConnection:videoConnection
                                                         completionHandler:^(CMSampleBufferRef imageDataSampleBuffer, NSError *error) {
                                                             if (imageDataSampleBuffer != NULL) {
                                                                 NSData *imageData = [AVCaptureStillImageOutput jpegStillImageNSDataRepresentation:imageDataSampleBuffer];
                                                                 UIImage *image = [[UIImage alloc] initWithData:imageData];                                                                 
                                                                 ALAssetsLibrary *library = [[ALAssetsLibrary alloc] init];
                                                                 [library writeImageToSavedPhotosAlbum:[image CGImage]
                                                                                           orientation:(ALAssetOrientation)[image imageOrientation]
                                                                                       completionBlock:^(NSURL *assetURL, NSError *error){
                                                                                           if (error) {
                                                                                               id delegate = [self delegate];
                                                                                               if ([delegate respondsToSelector:@selector(captureStillImageFailedWithError:)]) {
                                                                                                   [delegate captureStillImageFailedWithError:error];
                                                                                               }                                                                                               
                                                                                           }
                                                                                       }];
                                                                 [library release];
                                                                 [image release];
                                                             } else if (error) {
                                                                 id delegate = [self delegate];
                                                                 if ([delegate respondsToSelector:@selector(captureStillImageFailedWithError:)]) {
                                                                     [delegate captureStillImageFailedWithError:error];
                                                                 }
                                                             }
                                                         }];
}

- (BOOL) cameraToggle
{
    BOOL success = NO;
    
    if ([self hasMultipleCameras]) {
        NSError *error;
        AVCaptureDeviceInput *videoInput = [self videoInput];
        AVCaptureDeviceInput *newVideoInput;
        AVCaptureDevicePosition position = [[videoInput device] position];
        if (position == AVCaptureDevicePositionBack) {
            newVideoInput = [[AVCaptureDeviceInput alloc] initWithDevice:[self frontFacingCamera] error:&error];
        } else if (position == AVCaptureDevicePositionFront) {
            newVideoInput = [[AVCaptureDeviceInput alloc] initWithDevice:[self backFacingCamera] error:&error];
        } else {
            goto bail;
        }
        
        AVCaptureSession *session = [self session];
        if (newVideoInput != nil) {
            [session beginConfiguration];
            [session removeInput:videoInput];
            if ([session canAddInput:newVideoInput]) {
                [session addInput:newVideoInput];
                [self setVideoInput:newVideoInput];
            } else {
                [session addInput:videoInput];
            }
            [session commitConfiguration];
            success = YES;
            [newVideoInput release];
        } else if (error) {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(someOtherError:)]) {
                [delegate someOtherError:error];
            }
        }
    }
    
bail:
    return success;
}

- (BOOL) hasMultipleCameras
{
    return [[AVCaptureDevice devicesWithMediaType:AVMediaTypeVideo] count] > 1 ? YES : NO;
}

- (BOOL) hasFlash
{
    return [[[self videoInput] device] hasFlash];
}

- (AVCaptureFlashMode) flashMode
{
    return [[[self videoInput] device] flashMode];
}

- (void) setFlashMode:(AVCaptureFlashMode)flashMode
{
    AVCaptureDevice *device = [[self videoInput] device];
    if ([device isFlashModeSupported:flashMode] && [device flashMode] != flashMode) {
        NSError *error;
        if ([device lockForConfiguration:&error]) {
            [device setFlashMode:flashMode];
            [device unlockForConfiguration];
        } else {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(acquiringDeviceLockFailedWithError:)]) {
                [delegate acquiringDeviceLockFailedWithError:error];
            }
        }    
    }
}

- (BOOL) hasTorch
{
    return [[[self videoInput] device] hasTorch];
}

- (AVCaptureTorchMode) torchMode
{
    return [[[self videoInput] device] torchMode];
}

- (void) setTorchMode:(AVCaptureTorchMode)torchMode
{
    AVCaptureDevice *device = [[self videoInput] device];
    if ([device isTorchModeSupported:torchMode] && [device torchMode] != torchMode) {
        NSError *error;
        if ([device lockForConfiguration:&error]) {
            [device setTorchMode:torchMode];
            [device unlockForConfiguration];
        } else {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(acquiringDeviceLockFailedWithError:)]) {
                [delegate acquiringDeviceLockFailedWithError:error];
            }
        }
    }
}

- (BOOL) hasFocus
{
    AVCaptureDevice *device = [[self videoInput] device];
    
    return  [device isFocusModeSupported:AVCaptureFocusModeLocked] ||
            [device isFocusModeSupported:AVCaptureFocusModeAutoFocus] ||
            [device isFocusModeSupported:AVCaptureFocusModeContinuousAutoFocus];
}

- (AVCaptureFocusMode) focusMode
{
    return [[[self videoInput] device] focusMode];
}

- (void) setFocusMode:(AVCaptureFocusMode)focusMode
{
    AVCaptureDevice *device = [[self videoInput] device];
    if ([device isFocusModeSupported:focusMode] && [device focusMode] != focusMode) {
        NSError *error;
        if ([device lockForConfiguration:&error]) {
            [device setFocusMode:focusMode];
            [device unlockForConfiguration];
        } else {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(acquiringDeviceLockFailedWithError:)]) {
                [delegate acquiringDeviceLockFailedWithError:error];
            }
        }    
    }
}

- (BOOL) hasExposure
{
    AVCaptureDevice *device = [[self videoInput] device];
    
    return  [device isExposureModeSupported:AVCaptureExposureModeLocked] ||
            [device isExposureModeSupported:AVCaptureExposureModeAutoExpose] ||
            [device isExposureModeSupported:AVCaptureExposureModeContinuousAutoExposure];
}

- (AVCaptureExposureMode) exposureMode
{
    return [[[self videoInput] device] exposureMode];
}

- (void) setExposureMode:(AVCaptureExposureMode)exposureMode
{
    if (exposureMode == 1) {
        exposureMode = 2;
    }
    AVCaptureDevice *device = [[self videoInput] device];
    if ([device isExposureModeSupported:exposureMode] && [device exposureMode] != exposureMode) {
        NSError *error;
        if ([device lockForConfiguration:&error]) {
            [device setExposureMode:exposureMode];
            [device unlockForConfiguration];
        } else {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(acquiringDeviceLockFailedWithError:)]) {
                [delegate acquiringDeviceLockFailedWithError:error];
            }
        }
    }
}

- (BOOL) hasWhiteBalance
{
    AVCaptureDevice *device = [[self videoInput] device];
    
    return  [device isWhiteBalanceModeSupported:AVCaptureWhiteBalanceModeLocked] ||
            [device isWhiteBalanceModeSupported:AVCaptureWhiteBalanceModeAutoWhiteBalance];
}

- (AVCaptureWhiteBalanceMode) whiteBalanceMode
{
    return [[[self videoInput] device] whiteBalanceMode];
}

- (void) setWhiteBalanceMode:(AVCaptureWhiteBalanceMode)whiteBalanceMode
{
    if (whiteBalanceMode == 1) {
        whiteBalanceMode = 2;
    }    
    AVCaptureDevice *device = [[self videoInput] device];
    if ([device isWhiteBalanceModeSupported:whiteBalanceMode] && [device whiteBalanceMode] != whiteBalanceMode) {
        NSError *error;
        if ([device lockForConfiguration:&error]) {
            [device setWhiteBalanceMode:whiteBalanceMode];
            [device unlockForConfiguration];
        } else {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(acquiringDeviceLockFailedWithError:)]) {
                [delegate acquiringDeviceLockFailedWithError:error];
            }
        }
    }
}

- (void) focusAtPoint:(CGPoint)point
{
    AVCaptureDevice *device = [[self videoInput] device];
    if ([device isFocusPointOfInterestSupported] && [device isFocusModeSupported:AVCaptureFocusModeAutoFocus]) {
        NSError *error;
        if ([device lockForConfiguration:&error]) {
            [device setFocusPointOfInterest:point];
            [device setFocusMode:AVCaptureFocusModeAutoFocus];
            [device unlockForConfiguration];
        } else {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(acquiringDeviceLockFailedWithError:)]) {
                [delegate acquiringDeviceLockFailedWithError:error];
            }
        }        
    }
}

- (void) exposureAtPoint:(CGPoint)point
{
    AVCaptureDevice *device = [[self videoInput] device];
    if ([device isExposurePointOfInterestSupported] && [device isExposureModeSupported:AVCaptureExposureModeContinuousAutoExposure]) {
        NSError *error;
        if ([device lockForConfiguration:&error]) {
            [device setExposurePointOfInterest:point];
            [device setExposureMode:AVCaptureExposureModeContinuousAutoExposure];
            [device unlockForConfiguration];
        } else {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(acquiringDeviceLockFailedWithError:)]) {
                [delegate acquiringDeviceLockFailedWithError:error];
            }
        }
    }    
}

- (void) setConnectionWithMediaType:(NSString *)mediaType enabled:(BOOL)enabled;
{
    [[AVCamDemoCaptureManager connectionWithMediaType:mediaType fromConnections:[[self movieFileOutput] connections]] setEnabled:enabled];
}

+ (AVCaptureConnection *)connectionWithMediaType:(NSString *)mediaType fromConnections:(NSArray *)connections;
{
	for ( AVCaptureConnection *connection in connections ) {
		for ( AVCaptureInputPort *port in [connection inputPorts] ) {
			if ( [[port mediaType] isEqual:mediaType] ) {
				return [[connection retain] autorelease];
			}
		}
	}
	return nil;
}

@end

@implementation AVCamDemoCaptureManager (Internal)

- (AVCaptureDevice *) cameraWithPosition:(AVCaptureDevicePosition) position
{
    NSArray *devices = [AVCaptureDevice devicesWithMediaType:AVMediaTypeVideo];
    for (AVCaptureDevice *device in devices) {
        if ([device position] == position) {
            return device;
        }
    }
    return nil;
}

- (AVCaptureDevice *) frontFacingCamera
{
    return [self cameraWithPosition:AVCaptureDevicePositionFront];
}

- (AVCaptureDevice *) backFacingCamera
{
    return [self cameraWithPosition:AVCaptureDevicePositionBack];
}

- (AVCaptureDevice *) audioDevice
{
    NSArray *devices = [AVCaptureDevice devicesWithMediaType:AVMediaTypeAudio];
    if ([devices count] > 0) {
        return [devices objectAtIndex:0];
    }
    return nil;
}

- (NSURL *) tempFileURL
{
    NSString *outputPath = [[NSString alloc] initWithFormat:@"%@%@", NSTemporaryDirectory(), @"output.mov"];
    NSURL *outputURL = [[NSURL alloc] initFileURLWithPath:outputPath];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    if ([fileManager fileExistsAtPath:outputPath]) {
        NSError *error;
        if ([fileManager removeItemAtPath:outputPath error:&error] == NO) {
            id delegate = [self delegate];
            if ([delegate respondsToSelector:@selector(someOtherError:)]) {
                [delegate someOtherError:error];
            }            
        }
    }
    [outputPath release];
    return [outputURL autorelease];
}

@end


@implementation AVCamDemoCaptureManager (AVCaptureFileOutputRecordingDelegate)

- (void)             captureOutput:(AVCaptureFileOutput *)captureOutput
didStartRecordingToOutputFileAtURL:(NSURL *)fileURL
                   fromConnections:(NSArray *)connections
{
    id delegate = [self delegate];
    if ([delegate respondsToSelector:@selector(recordingBegan)]) {
        [delegate recordingBegan];
    }
}

- (void)              captureOutput:(AVCaptureFileOutput *)captureOutput
didFinishRecordingToOutputFileAtURL:(NSURL *)outputFileURL
                    fromConnections:(NSArray *)connections
                              error:(NSError *)error
{
    id delegate = [self delegate];
    if (error && [delegate respondsToSelector:@selector(someOtherError:)]) {
        [delegate someOtherError:error];
    }
    
    ALAssetsLibrary *library = [[ALAssetsLibrary alloc] init];
    if ([library videoAtPathIsCompatibleWithSavedPhotosAlbum:outputFileURL]) {
        [library writeVideoAtPathToSavedPhotosAlbum:outputFileURL
                                    completionBlock:^(NSURL *assetURL, NSError *error){
                                        if (error && [delegate respondsToSelector:@selector(assetLibraryError:forURL:)]) {
                                            [delegate assetLibraryError:error forURL:assetURL];
                                        }
                                    }];
    } else {
        if ([delegate respondsToSelector:@selector(cannotWriteToAssetLibrary)]) {
            [delegate cannotWriteToAssetLibrary];
        }
    }
    [library release];    
    
    if ([[UIDevice currentDevice] isMultitaskingSupported]) {
        [[UIApplication sharedApplication] endBackgroundTask:[self backgroundRecordingID]];
    }
    
    if ([delegate respondsToSelector:@selector(recordingFinished)]) {
        [delegate recordingFinished];
    }
}

@end
