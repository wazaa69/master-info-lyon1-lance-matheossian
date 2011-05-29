
set ns_ [new Simulator]

# nodes: 10, pause: 0.00, max speed: 10.00, max x: 450.00, max y: 450.00
set val(chan) Channel/WirelessChannel ;# channel type
set val(prop) Propagation/TwoRayGround ;# radio-propagation model
set val(netif) Phy/WirelessPhy ;# network interface type
set val(mac) Mac/802_11 ;# MAC type
set val(ifq) Queue/DropTail/PriQueue ;# interface queue type
set val(ll) LL ;# link layer type
set val(ant) Antenna/OmniAntenna ;# antenna model
set val(ifqlen) 50 ;# max packet in ifq
set val(nn) 10 ;# number of mobilenodes
set val(rp) AODV ;# routing protocol
set val(x) 450 ;# X dimension of topography
set val(y) 450 ;# Y dimension of topography
set val(stop) 10;# time of simulation end
set tracefd [open simple.tr w]
set fichier [open record.pos w]
set windowVsTime2 [open win.tr w]
set namtrace [open simwrls.nam w]

$ns_ trace-all $tracefd
$ns_ namtrace-all-wireless $namtrace $val(x) $val(y)

# Le fichier trace
set f [open out.tr w]
$ns_ trace-all $f

#Open the NAM trace file
set nf [open out.nam w]
$ns_ namtrace-all $nf

#Setup topography
set topo [new Topography]
$topo load_flatgrid $val(x) $val(y)
set god_ [create-god $val(nn)]

# configure the nodes
$ns_ node-config -adhocRouting $val(rp) \
-llType $val(ll) \
-macType $val(mac) \
-ifqType $val(ifq) \
-ifqLen $val(ifqlen) \
-antType $val(ant) \
-propType $val(prop) \
-phyType $val(netif) \
-channelType $val(chan) \
-topoInstance $topo \
-agentTrace ON \
-routerTrace ON \
-macTrace OFF \
-movementTrace ON
for {set i 0} {$i < $val(nn) } { incr i } {
        set node_($i) [$ns_ node]
}


#Procédure Record() qui nous permet d'enregistrer dans un fichier les coordonnées X et Y de chaque noeud

proc record {} {
       global ns_ node_ fichier val

       set time 1
       set now [$ns_ now]

       for {set i 0} {$i < $val(nn) } { incr i } {
               puts $fichier "$i [$node_($i) set X_] [$node_($i) set Y_]"
              
       }

       $ns_ at [expr $now+$time] "record"
}

$node_(0) set X_ 374.166268186314
$node_(0) set Y_ 49.843012673628
$node_(0) set Z_ 0.000000000000
$node_(1) set X_ 339.111513687576
$node_(1) set Y_ 259.789285048882
$node_(1) set Z_ 0.000000000000
$node_(2) set X_ 162.870997790519
$node_(2) set Y_ 433.626663823227
$node_(2) set Z_ 0.000000000000
$node_(3) set X_ 121.097644974550
$node_(3) set Y_ 321.140017767233
$node_(3) set Z_ 0.000000000000
$node_(4) set X_ 111.472568576293
$node_(4) set Y_ 95.890759233217
$node_(4) set Z_ 0.000000000000
$node_(5) set X_ 239.026727345204
$node_(5) set Y_ 368.644259213006
$node_(5) set Z_ 0.000000000000
$node_(6) set X_ 338.967207482825
$node_(6) set Y_ 390.442259973378
$node_(6) set Z_ 0.000000000000
$node_(7) set X_ 271.832924939517
$node_(7) set Y_ 328.689552952402
$node_(7) set Z_ 0.000000000000
$node_(8) set X_ 310.328814840967
$node_(8) set Y_ 418.516110454535
$node_(8) set Z_ 0.000000000000
$node_(9) set X_ 401.777415063629
$node_(9) set Y_ 356.529243851100
$node_(9) set Z_ 0.000000000000

$ns_ at 0.000000000000 "$node_(0) setdest 309.439405813673 200.611608714614 1.686378382325"
$ns_ at 0.000000000000 "$node_(1) setdest 70.933330991336 166.352229495827 8.581179798903"
$ns_ at 0.000000000000 "$node_(2) setdest 148.745572378179 445.335415652899 2.087468845233"
$ns_ at 0.000000000000 "$node_(3) setdest 19.241523522007 275.281746233488 2.923601259386"
$ns_ at 0.000000000000 "$node_(4) setdest 49.006729303719 102.488252978669 1.074022068851"
$ns_ at 0.000000000000 "$node_(5) setdest 360.444480465365 316.327371179614 1.914256403261"
$ns_ at 0.000000000000 "$node_(6) setdest 78.745373671185 334.738935582735 10.863921330822"
$ns_ at 0.000000000000 "$node_(7) setdest 127.397841191560 326.034002871037 5.583077281779"
$ns_ at 0.000000000000 "$node_(8) setdest 102.072051861553 330.744551691429 8.100873030485"
$ns_ at 0.000000000000 "$node_(9) setdest 425.126214983466 180.345194847742 5.763801675958"

$god_ set-dist 0 1 1
$god_ set-dist 0 2 2
$god_ set-dist 0 3 2
$god_ set-dist 0 4 3
$god_ set-dist 0 5 2
$god_ set-dist 0 6 2
$god_ set-dist 0 7 2
$god_ set-dist 0 8 2
$god_ set-dist 0 9 2
$god_ set-dist 1 2 1
$god_ set-dist 1 3 1
$god_ set-dist 1 4 2
$god_ set-dist 1 5 1
$god_ set-dist 1 6 1
$god_ set-dist 1 7 1
$god_ set-dist 1 8 1
$god_ set-dist 1 9 1
$god_ set-dist 2 3 1
$god_ set-dist 2 4 2
$god_ set-dist 2 5 1
$god_ set-dist 2 6 1
$god_ set-dist 2 7 1
$god_ set-dist 2 8 1
$god_ set-dist 2 9 2
$god_ set-dist 3 4 1
$god_ set-dist 3 5 1
$god_ set-dist 3 6 1
$god_ set-dist 3 7 1
$god_ set-dist 3 8 1
$god_ set-dist 3 9 2
$god_ set-dist 4 5 2
$god_ set-dist 4 6 2
$god_ set-dist 4 7 2
$god_ set-dist 4 8 2
$god_ set-dist 4 9 3
$god_ set-dist 5 6 1
$god_ set-dist 5 7 1
$god_ set-dist 5 8 1
$god_ set-dist 5 9 1
$god_ set-dist 6 7 1
$god_ set-dist 6 8 1
$god_ set-dist 6 9 1
$god_ set-dist 7 8 1
$god_ set-dist 7 9 1
$god_ set-dist 8 9 1


$ns_ at 4.121865252516 "$god_ set-dist 0 4 2"
$ns_ at 4.121865252516 "$god_ set-dist 1 4 1"
$ns_ at 4.121865252516 "$god_ set-dist 4 9 2"


# Flux FTP a 3 Sauts entre noeud 8 et 9
set tcp1 [new Agent/TCP/Newreno]
$tcp1 set class_ 1
set sink [new Agent/TCPSink]
$ns_ attach-agent $node_(8) $tcp1
$ns_ attach-agent $node_(9) $sink
$ns_ connect $tcp1 $sink
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1
$ns_ at 2.0 "$ftp1 start"

# Flux FTP a 2 sauts entre noeud 5 et 9
set tcp2 [new Agent/TCP/Newreno]
$tcp2 set class_ 1
set sink [new Agent/TCPSink]
$ns_ attach-agent $node_(5) $tcp2
$ns_ attach-agent $node_(9) $sink
$ns_ connect $tcp2 $sink
set ftp2 [new Application/FTP]
$ftp2 attach-agent $tcp2
$ns_ at 2.0 "$ftp2 start"


#Setup a UDP connection
set udp1 [new Agent/UDP]
$ns_ attach-agent $node_(7) $udp1	

set null1 [new Agent/Null]
$ns_ attach-agent $node_(1) $null1
$ns_ connect $udp1 $null1

#Setup a CBR over UDP connection
set cbr1 [new Application/Traffic/CBR]
$cbr1 attach-agent $udp1
$cbr1 set type_ CBR
$cbr1 set packet_size_ 1000
$cbr1 set rate_ 0.4mb

#Schedule events for the CBR and FTP agents
$ns_ at 0.1 "$cbr1 start"
$ns_ at 0.5 "record"



# Define node initial position in nam

for {set i 0} {$i < $val(nn)} { incr i } {

#Defines the node size for nam

$ns_ initial_node_pos $node_($i) 10

}

# Telling nodes when the simulation ends
for {set i 0} {$i < $val(nn) } { incr i } {

        $ns_ at $val(stop) "$node_($i) reset";

}

# ending nam and the simulation
$ns_ at $val(stop) "$ns_ nam-end-wireless $val(stop)"
$ns_ at $val(stop) "stop"
$ns_ at 10.01 "puts \"end simulation\" ; $ns_ halt"

# Procedure Stop
proc stop {} {

        global ns_ tracefd namtrace fichier
        $ns_ flush-trace
        close $tracefd
        close $namtrace
	close $fichier 
}

 
$ns_ run

#
# Destination Unreachables: 0
#
# Route Changes: 3
#
# Link Changes: 1
#
# Node | Route Changes | Link Changes
#    0 |             1 |            0
#    1 |             1 |            1
#    2 |             0 |            0
#    3 |             0 |            0
#    4 |             3 |            1
#    5 |             0 |            0
#    6 |             0 |            0
#    7 |             0 |            0
#    8 |             0 |            0
#    9 |             1 |            0
#
