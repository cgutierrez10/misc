# This file will evaluate data from a csv stock market data file
# as if it existed to a particular point in time and then using predictions
# from probabilities evaluation script determine the accuracy from that
# point forward to other known samples within the file.
#
# This file should be able to determine if up to date information can
# meaningfully indicate future data as well as the possibilities
# of using simple statistical information to estimate future stock
# movements.
#

my $file;
my $readpoint1;
my $readpoint2;
my $window;
my $target;
my $prob;

# Data gathering
print "Please indicate the number for the starting datapoint: [0; >=0]";
chomp($readpoint1 = <STDIN>);
print "Please indicate the number for the ending datapoint: [1000; >{start point}]";
chomp($readpoint2 = <STDIN>);
print "Please indicate the time window of prediction: [5; >0]";
chomp($window = <STDIN>);
print "Please enter the expected movement: [ ; >0]";
chomp($target = <STDIN>);
print "Please enter the certainty of movement magnitude: [95%]";
chomp($prob = <STDIN>);



# Datachecking
if ($prob == null) { $prob = 0.95; }
if ($window == null) { $window = 5; }
if ($readpoint2 == null) { $readpoint2 = 1000; }
if ($readpoint1 < 0) { die "Error: Entry data point must be non-negative.\n"; }
if ($readpoint1 > $readpoint2) { die "Error: starting datapoint must be before ending datapoint.\n"; }
if ($window <= 0) { die "Error: window must be at least 1 datapoint.\n"; }
if ($target <= 0) { die "Error: Magnitude of movement too small.\n"; }


# Verify that future datapoints window meets or exceeds estimates


# Determine whether estimates are statistically significant deviations
