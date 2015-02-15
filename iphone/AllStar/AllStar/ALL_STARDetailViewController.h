//
//  ALL_STARDetailViewController.h
//  AllStar
//
//  Created by David Boschwitz on 2/15/15.
//  Copyright (c) 2015 CPR E 186 Dream Team All-Stars. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ALL_STARDetailViewController : UIViewController <UISplitViewControllerDelegate>

@property (strong, nonatomic) id detailItem;

@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@end
