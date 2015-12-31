#!c:/strawberry/perl/bin/perl.exe

my $p = 0.055;
my $s;
my $f;
my $i = 0;

my @probarr;
my @occurs;

do
{
  $s = 0;
  $f = 0;
  $run = 0;
  do
  {
    $outcome = rand(1000);
    if ($outcome >= (1-$p)*1000)
    {
      $s++;
    } else {
      $f++;
    }
    $run++;
  }
  while ($run < 500);

  if ($s > 35 || $s < 15)
  {
    $occurs[2]++;
  }
  else
  {
    $occurs[1]++;
  }
  $i++;
}
while ($i < 1000);

print "Success: $occurs[1]\nFails: $occurs[2]\nTrials: $run\n";
