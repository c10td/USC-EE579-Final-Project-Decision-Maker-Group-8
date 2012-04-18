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
$searchZipCode = $input{'search'};
#print "$searchZipCode\n";
use LWP::Simple;
if(eval{require LWP::Simple;})
{
}
$url=join("","http://community.flixster.com/showtimes/",$searchZipCode);
#$url = "http://www.flixster.com/showtimes/90007";
$content = LWP::Simple::get($url);
#print $content;
$file='movie.txt';
open(INFO,">$file");
print INFO $content;
close(INFO);
open(INFO,$file);
$foundTheater=0;
$foundAllMovie=0;
$foundAllTime=0;
@movieName=();
@duration=();
@times=();
@pic=();
@movieURL=();
while($LINE=<INFO>)
{
	if(($LINE =~ /<div class=\"theater\">/)&&$foundTheater==0)
	{
		$foundTheater=1;
		
		$LINE=<INFO>;
		$LINE=<INFO>;
		$LINE=<INFO>;
		$LINE=~ s/\<.+?\>//sg;
		$theater=$LINE;
		$LINE=<INFO>;
		$LINE=<INFO>;
		$LINE=~ s/\<.+?\>//sg;
		$LINE=~ s/^\s+//;
		$theaterAddress=$LINE;
		while($LINE=<INFO>)
		{
			$foundAllTime=0;
			$time="";
			if($LINE =~ /<div class=\"theater\">/)
			{
				$foundAllMovie=1;
			}
			if(($LINE =~ /<div class=\"mtitle\">/)&&$foundAllMovie==0)
			{
				
				$picUrl="";
				$picFOUND=0;
				for($j=1;$j<=58;$j++)
				{
				$LINE=<INFO>;
				}
				@pairs=split(/\"/,$LINE);
				$picUrl=join("","http://community.flixster.com",$pairs[1]);
				$picPage = LWP::Simple::get($picUrl);
				$picFile='pic.txt';
				push(@movieURL, "$picUrl");
				open(PIC,">$picFile");
				print PIC $picPage;
				close(PIC);
				open(PIC,$picFile);
				while(($picLine=<PIC>)&&($picFOUND==0))
				{
					if($picLine =~ /image_src/)
					{
						
						$picFOUND=1;
						@picpairs=split(/\"/,$picLine);
						push(@pic," $picpairs[3]");
					}
				}
				close(PIC);
				$LINE=~ s/\<.+?\>//sg;
				$LINE=~ s/^\s+//;
				$LINE=~ s/&/&amp;/g;#$LINE=~ s/&/&amp;amp;/g;############
				push(@movieName, "$LINE");
				$LINE=<INFO>;
				$LINE=~ s/\<.+?\>//sg;
				$LINE=~ s/^\s+//;
				push(@duration, "$LINE");
				$LINE=<INFO>;
				$LINE=<INFO>;
				while(($LINE=<INFO>)&&($foundAllTime==0))
				{
					#if(($LINE =~ /<a/)&&($foundAllTime==0))
					#{
 #					  $time = join(' ', $time,$LINE);
	#					  $LINE=<INFO>;
	#					  $LINE=~ s/\<.+?\>//sg;
	#					  $LINE=~ s/^\s+//;
	#					  $time = join('', $time,"$LINE</a>");
	#					
	#				}
					if(($LINE =~ /[AP]M/)&&($foundAllTime==0))
					{
						  $LINE=~ s/\<.+?\>//sg;
						  $LINE=~ s/^\s+//;
						  $time = join(' ', $time,$LINE);
					}
					elsif($LINE =~ /<\/div>/)
					{
						$foundAllTime=1;
					}
				}
				push(@times, "$time");
			}
		
		}
	
	}
}
close(INFO);

$arraySize = @movieName;
for($i=0;$i<$arraySize;$i++)
{
	if (!$movieName[$i]eq"")
	{
		print $movieName[$i];
		print "\n";
	}
	
}
