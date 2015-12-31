# This file will read a csv stock data table and when supplied
# values for standard deviation mean and other necessary constants
# shall return an interactive probabilities prediction of movement magnitude
# per time (samples) in an attempt to predict swings and magnitudes
# by straightforward statistics. 
#
# Later updates may include simpler programatic interfacing to automatically
# call statistical evaluation script and a simple output api for development
# beyond this program.

my $file;
my $stddev;
my $mean;
my $samples;
my $magnitude;
my $magnitude_target;
my $probability;
my $significance;

print "Please enter a filename to read samples from [data table.csv]: ";
chomp($file = <STDIN>);
print "Please enter the calculated standard deviation []: ";
chomp($stddev = <STDIN>);
print "Please enter the calculated average []: ";
chomp($mean = <STDIN>);
print "Please enter the number of samples to use [50]: ";
chomp($samples = <STDIN>);
print "Please enter the magnitude target []: ";
chomp($magnitude_target = <STDIN>);
print "Please enter the target significance [0.05]: ";
chomp($significance = <STDIN>);

if ($file == null) { $file = "Data table.csv"; }
if ($samples == 0) { $samples = 50; }
if ($samples <= 30) { die "Too small sample size, statistical evaluations would be invalid.\n"; }
if ($significance == 0) { $significance = 0.05; }
if (($stddev * $mean * $magnitude_target) == 0) { die "Please enter values for standard deviation, average, and magnitude target.\n" }

