//
//  ALL_STARDetailViewController.m
//  AllStar
//
//  Created by David Boschwitz on 2/15/15.
//  Copyright (c) 2015 CPR E 186 Dream Team All-Stars. All rights reserved.
//

#import "ALL_STARDetailViewController.h"

@interface ALL_STARDetailViewController ()
@property (weak, nonatomic) NSMutableArray *states;
@property (strong, nonatomic) UIPopoverController *masterPopoverController;
- (void)configureView;
@end

@implementation ALL_STARDetailViewController

#pragma mark - Managing the detail item

- (void)setDetailItem:(id)newDetailItem
{
    if (_detailItem != newDetailItem) {
        _detailItem = newDetailItem;
        
        // Update the view.
        [self configureView];
    }

    if (self.masterPopoverController != nil) {
        [self.masterPopoverController dismissPopoverAnimated:YES];
    }        
}

- (void)configureView
{
    // Update the user interface for the detail item.

    if (self.detailItem) {
        self.detailDescriptionLabel.text = [self.detailItem description];
    }
}

/*
- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    [self configureView];
}*/
- (void)viewDidLoad {
    [super viewDidLoad];
    UILocalizedIndexedCollation *theCollation = [UILocalizedIndexedCollation currentCollation];
    self.states = [NSMutableArray arrayWithCapacity:1];
    
    NSString *thePath = [[NSBundle mainBundle] pathForResource:@"Servers" ofType:@"plist"];
    NSArray *tempArray;
    NSMutableArray *serversTemp;
    if (thePath && (tempArray = [NSArray arrayWithContentsOfFile:thePath]) ) {
        serversTemp = [NSMutableArray arrayWithCapacity:1];
        for (NSDictionary *stateDict in tempArray) {
            ALLSTAR_ServerListing *server = [[ALLSTAR_ServerListing alloc] init];
            server.name = [stateDict objectForKey:@"Name"];
            server.serverIP = [stateDict objectForKey:@"Server IP"];
            server.port = [stateDict objectForKey:@"Port (Default 2727)"];
            [serversTemp addObject:server];
        }
    } else  {
        return;
    }    // (1)
    for (ALLSTAR_ServerListing *theState in serversTemp) {
        NSInteger sect = [theCollation sectionForObject:theState collationStringSelector:@selector(name)];
        theState.sectionNumber = sect;
    }
    // (2)
    NSInteger highSection = [[theCollation sectionTitles] count];
    NSMutableArray *sectionArrays = [NSMutableArray arrayWithCapacity:highSection];
    for (int i = 0; i < highSection; i++) {
        NSMutableArray *sectionArray = [NSMutableArray arrayWithCapacity:1];
        [sectionArrays addObject:sectionArray];
    }
    // (3)
    for (ALLSTAR_ServerListing *theState in serversTemp) {
        [(NSMutableArray *)[sectionArrays objectAtIndex:theState.sectionNumber] addObject:theState];
    }
    // (4)
    for (NSMutableArray *sectionArray in sectionArrays) {
        NSArray *sortedSection = [theCollation sortedArrayFromArray:sectionArray
                                            collationStringSelector:@selector(name)];
        [self.states addObject:sortedSection];
    }
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Split view

- (void)splitViewController:(UISplitViewController *)splitController willHideViewController:(UIViewController *)viewController withBarButtonItem:(UIBarButtonItem *)barButtonItem forPopoverController:(UIPopoverController *)popoverController
{
    barButtonItem.title = NSLocalizedString(@"Master", @"Master");
    [self.navigationItem setLeftBarButtonItem:barButtonItem animated:YES];
    self.masterPopoverController = popoverController;
}

- (void)splitViewController:(UISplitViewController *)splitController willShowViewController:(UIViewController *)viewController invalidatingBarButtonItem:(UIBarButtonItem *)barButtonItem
{
    // Called when the view is shown again in the split view, invalidating the button and popover controller.
    [self.navigationItem setLeftBarButtonItem:nil animated:YES];
    self.masterPopoverController = nil;
}

@end
