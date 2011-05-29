set ns [new Simulator]
set filetrace [open out.tr w]
proc finish {} {
global ns flowmon fcl filetrace
close $filetrace
exit 0
}
proc record {} {
global ns flowmon bitsOutOld filetrace
set fid 1
#Set the time after which the procedure should be called again
set time 0.5
#How many bytes have been received by the traffic sinks?
set flow [[$flowmon classifier] lookup auto 0 0 $fid]
set bitsOut [$flow set bdepartures_]
#Get the current time
set now [$ns now]
#Calculate the bandwidth (in MBit/s) and write it to the files
puts $filetrace "$now [expr ($bitsOut-$bitsOutOld)/$time*8/1000000]"
#puts $filetrace "$now $drop"
set bitsOutOld $bitsOut
#Re-schedule the procedure
$ns at [expr $now+$time] "record"
}
##Création des noeuds
set n0 [$ns node]
set n1 [$ns node]
##Init du lien duplex
$ns duplex-link $n0 $n1 50.0Mb 200.0ms DropTail
$ns queue-limit $n0 $n1 20
##Init de la connexion TCP
set tcp0 [new Agent/TCP]
#Parametre du fluc TCP
$tcp0 set packetSize_ 1500
$tcp0 set fid_ 1
$ns attach-agent $n0 $tcp0
set null0 [new Agent/TCPSink]
$ns attach-agent $n1 $null0
$ns connect $tcp0 $null0
##flow monitor
set flowmon [$ns makeflowmon Fid]
#Attache le monitor au lien
set lien [$ns link $n0 $n1]
$ns attach-fmon $lien $flowmon

##Loss Model
set lossModel [new ErrorModel]
$lossModel unit packet
$lossModel set rate_ 0.1
$lossModel ranvar [new RandomVariable/Uniform]
$lossModel drop-target [new Agent/Null]
$ns lossmodel $lossModel $n0 $n1
##Scheduler
set bitsOutOld 0
puts "On envoie le fichier de 3000Ko"
$ns at 10.0 "$tcp0 send 3000000"
$ns at 11.0 "record"
$ns at 200.0 "finish"
$ns run
