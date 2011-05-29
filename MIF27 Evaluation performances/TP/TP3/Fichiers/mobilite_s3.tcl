

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

$node_(0) set X_ 405.765792378058
$node_(0) set Y_ 47.861046822532
$node_(0) set Z_ 0.000000000000
$node_(1) set X_ 5.275660845762
$node_(1) set Y_ 393.632864783433
$node_(1) set Z_ 0.000000000000
$node_(2) set X_ 344.332158337601
$node_(2) set Y_ 107.076954590624
$node_(2) set Z_ 0.000000000000
$node_(3) set X_ 341.596957017241
$node_(3) set Y_ 377.928444535732
$node_(3) set Z_ 0.000000000000
$node_(4) set X_ 189.279657711780
$node_(4) set Y_ 442.719224790484
$node_(4) set Z_ 0.000000000000
$node_(5) set X_ 89.908095402849
$node_(5) set Y_ 428.765435198604
$node_(5) set Z_ 0.000000000000
$node_(6) set X_ 341.951806791121
$node_(6) set Y_ 303.669835956145
$node_(6) set Z_ 0.000000000000
$node_(7) set X_ 336.999538120791
$node_(7) set Y_ 220.683645713655
$node_(7) set Z_ 0.000000000000
$node_(8) set X_ 116.954301245626
$node_(8) set Y_ 252.363151077073
$node_(8) set Z_ 0.000000000000
$node_(9) set X_ 151.354992548432
$node_(9) set Y_ 335.040583843002
$node_(9) set Z_ 0.000000000000
$ns_ at 0.000000000000 "$node_(0) setdest 348.884654421827 366.315189305125 6.110059062692"
$ns_ at 0.000000000000 "$node_(1) setdest 250.835233885732 179.190002677851 7.730591084799"
$ns_ at 0.000000000000 "$node_(2) setdest 53.188354653115 240.590540143389 8.142026936063"
$ns_ at 0.000000000000 "$node_(3) setdest 292.229976012333 193.221915382929 9.805332566497"
$ns_ at 0.000000000000 "$node_(4) setdest 283.779118495541 34.351869857681 6.173160840761"
$ns_ at 0.000000000000 "$node_(5) setdest 391.973850010093 379.651068527583 9.627660944255"
$ns_ at 0.000000000000 "$node_(6) setdest 125.813054460826 324.374199570118 14.355248526179"
$ns_ at 0.000000000000 "$node_(7) setdest 415.775253235189 337.780226943616 7.594667290033"
$ns_ at 0.000000000000 "$node_(8) setdest 445.570845198523 394.559067433767 7.914811523231"
$ns_ at 0.000000000000 "$node_(9) setdest 238.631705622048 253.222939400089 1.925810924864"
$god_ set-dist 0 1 3
$god_ set-dist 0 2 1
$god_ set-dist 0 3 2
$god_ set-dist 0 4 3
$god_ set-dist 0 5 3
$god_ set-dist 0 6 2
$god_ set-dist 0 7 1
$god_ set-dist 0 8 2
$god_ set-dist 0 9 2
$god_ set-dist 1 2 3
$god_ set-dist 1 3 2
$god_ set-dist 1 4 1
$god_ set-dist 1 5 1
$god_ set-dist 1 6 2
$god_ set-dist 1 7 2
$god_ set-dist 1 8 1
$god_ set-dist 1 9 1
$god_ set-dist 2 3 2
$god_ set-dist 2 4 2
$god_ set-dist 2 5 3
$god_ set-dist 2 6 1
$god_ set-dist 2 7 1
$god_ set-dist 2 8 2
$god_ set-dist 2 9 2
$god_ set-dist 3 4 1
$god_ set-dist 3 5 2
$god_ set-dist 3 6 1
$god_ set-dist 3 7 1
$god_ set-dist 3 8 2
$god_ set-dist 3 9 1
$god_ set-dist 4 5 1
$god_ set-dist 4 6 1
$god_ set-dist 4 7 2
$god_ set-dist 4 8 1
$god_ set-dist 4 9 1
$god_ set-dist 5 6 2
$god_ set-dist 5 7 2
$god_ set-dist 5 8 1
$god_ set-dist 5 9 1
$god_ set-dist 6 7 1
$god_ set-dist 6 8 1
$god_ set-dist 6 9 1
$god_ set-dist 7 8 1
$god_ set-dist 7 9 1
$god_ set-dist 8 9 1

$ns_ at 0.501376721696 "$god_ set-dist 3 8 1"
$ns_ at 0.671226900217 "$god_ set-dist 3 5 1"
$ns_ at 1.399047522780 "$god_ set-dist 2 5 2"
$ns_ at 1.399047522780 "$god_ set-dist 5 6 1"
$ns_ at 1.612000097541 "$god_ set-dist 1 2 2"
$ns_ at 1.612000097541 "$god_ set-dist 2 8 1"
$ns_ at 1.624734218672 "$god_ set-dist 2 3 1"
$ns_ at 2.001144375366 "$god_ set-dist 0 4 2"
$ns_ at 2.001144375366 "$god_ set-dist 4 7 1"
$ns_ at 4.670613947897 "$god_ set-dist 1 6 1"



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
# Route Changes: 10
#
# Link Changes: 7
#
# Node | Route Changes | Link Changes
#    0 |             1 |            0
#    1 |             2 |            1
#    2 |             4 |            2
#    3 |             3 |            3
#    4 |             2 |            1
#    5 |             3 |            2
#    6 |             2 |            2
#    7 |             1 |            1
#    8 |             2 |            2
#    9 |             0 |            0
#
