#!/usr/bin/perl -w
use warnings;
#read(STDIN, $FormInfo, $ENV{'CONTENT_LENGTH'});
print "Content-type:text/html\n\n";#content-type: text/xml
if (length ($ENV{'QUERY_STRING'}) > 0){
      $bufferString = $ENV{'QUERY_STRING'};
      @pairs = split(/&/, $bufferString);
      foreach $pair (@pairs){
           ($name, $value) = split(/=/, $pair);
           $value =~ s/%([a-fA-F0-9][a-fA-F0-9])/pack("C", hex($1))/eg;
           $input{$name} = $value; 
      }
 }
$searchZipCode = $input{'rrzip'};
#$cuisineType=$input{'cuisine'};
#print "$searchZipCode\n";
use LWP::Simple;
if(eval{require LWP::Simple;})
{
}
$url=join("","http://www.yelp.com/search?find_desc=best+restaurants&find_loc=",$searchZipCode);
$url=join("",$url,"&ns=1");

$content = LWP::Simple::get($url);
#print $content;
$file='apple.txt';
open(INFO,">$file");
print INFO $content;
close(INFO);
open(INFO,$file);
@restaurantName=();
while($LINE=<INFO>)
{

	if($LINE =~ m/bizTitleLink/)
	{

		
		$LINE=~ m#<a[\d\D]*?id="[^"]*?"[\d\D]*?href="[^"]*?"[^>]*?>[^.]*?\.([^<&\n]*)#i;
		
		$name = $1;
		
		push(@restaurantName,"$name");
	
	}


}
close(INFO);
$arraySize = @restaurantName;
for($i=0;$i<$arraySize;$i++)
{
	if (!$restaurantName[$i]eq"")
	{
		print $restaurantName[$i];
		print "\n";
	}
	
}