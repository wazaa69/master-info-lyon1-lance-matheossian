#Create a simulator object
set ns [new Simulator]

#Tell the simulator to use dynamic routing
$ns rtproto DV

#Open the nam trace file
set nf [open out.nam w]
$ns namtrace-all $nf

#Define a ’finish’ procedure
proc finish {} {
	global ns nf
	$ns flush-trace

	#Close the trace file
	close $nf

	#Execute nam on the trace file
	exec nam out.nam &
	exit 0
}

#RING TOPOLOGY (Topologie Anneau)
# IMPORTANT : 1- Remarquez que les noeuds sont d ́finis sur un tableau

#
#2- ESSAYEZ DE REFAIRE LA MEME BOUCLE AVEC UN WHILE

#Create eight nodes
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]
set n7 [$ns node]
set n8 [$ns node]
set n9 [$ns node]

#Create links between the nodes
$ns duplex-link $n1 $n2 1Mb 10ms DropTail
$ns duplex-link $n4 $n5 1Mb 10ms DropTail
$ns duplex-link $n7 $n8 1Mb 10ms DropTail

$ns duplex-link $n2 $n3 1Mb 10ms DropTail
$ns duplex-link $n5 $n6 1Mb 10ms DropTail
$ns duplex-link $n8 $n9 1Mb 10ms DropTail

$ns duplex-link $n1 $n4 1Mb 10ms DropTail
$ns duplex-link $n2 $n5 1Mb 10ms DropTail
$ns duplex-link $n3 $n6 1Mb 10ms DropTail

$ns duplex-link $n4 $n7 1Mb 10ms DropTail
$ns duplex-link $n5 $n8 1Mb 10ms DropTail
$ns duplex-link $n6 $n9 1Mb 10ms DropTail

$ns duplex-link $n1 $n3 1Mb 10ms DropTail
$ns duplex-link $n4 $n6 1Mb 10ms DropTail
$ns duplex-link $n7 $n9 1Mb 10ms DropTail
$ns duplex-link $n1 $n7 1Mb 10ms DropTail
$ns duplex-link $n2 $n8 1Mb 10ms DropTail
$ns duplex-link $n3 $n9 1Mb 10ms DropTail

#Create a UDP agent and attach it to node n(0)
set udp0 [new Agent/UDP]
$ns attach-agent $n1 $udp0

# Create a CBR traffic source and attach it to udp0
set cbr0 [new Application/Traffic/CBR]
$cbr0 set packetSize_ 500
$cbr0 set interval_ 0.005
$cbr0 attach-agent $udp0

#Create a Null agent (a traffic sink) and attach it to node n(3)
set null0 [new Agent/Null]
$ns attach-agent $n9 $null0

#Connect the traffic source with the traffic sink
$ns connect $udp0 $null0

#Schedule events for the CBR agent and the network dynamics
$ns at 0.5 "$cbr0 start"
$ns rtmodel-at 1.0 down $n1 $n2
$ns rtmodel-at 2.0 up $n1 $n2
$ns at 4.5 "$cbr0 stop"

#Call the finish procedure after 5 seconds of simulation time
$ns at 5.0 "finish"

#Run the simulation
$ns run

