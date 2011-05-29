

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

$node_(0) set X_ 356.683629818772
$node_(0) set Y_ 296.828707933978
$node_(0) set Z_ 0.000000000000
$node_(1) set X_ 300.246045750374
$node_(1) set Y_ 128.650656659002
$node_(1) set Z_ 0.000000000000
$node_(2) set X_ 341.955871909582
$node_(2) set Y_ 340.366664458128
$node_(2) set Z_ 0.000000000000
$node_(3) set X_ 18.309813541929
$node_(3) set Y_ 125.075074894264
$node_(3) set Z_ 0.000000000000
$node_(4) set X_ 318.382391082947
$node_(4) set Y_ 57.864796879207
$node_(4) set Z_ 0.000000000000
$node_(5) set X_ 278.070315762569
$node_(5) set Y_ 243.744467105449
$node_(5) set Z_ 0.000000000000
$node_(6) set X_ 29.911066200934
$node_(6) set Y_ 115.972699451335
$node_(6) set Z_ 0.000000000000
$node_(7) set X_ 153.074184674637
$node_(7) set Y_ 214.859121686476
$node_(7) set Z_ 0.000000000000
$node_(8) set X_ 382.146081267014
$node_(8) set Y_ 48.421412490228
$node_(8) set Z_ 0.000000000000
$node_(9) set X_ 398.880931145333
$node_(9) set Y_ 309.497711697017
$node_(9) set Z_ 0.000000000000

$ns_ at 0.000000000000 "$node_(0) setdest 171.934485182253 209.359038934736 7.234871330404"
$ns_ at 0.000000000000 "$node_(1) setdest 2.016475719732 224.003978816985 5.975334295740"
$ns_ at 0.000000000000 "$node_(2) setdest 228.491522063556 439.761527725588 6.354849066541"
$ns_ at 0.000000000000 "$node_(3) setdest 288.157931898453 16.078297361798 1.951926028356"
$ns_ at 0.000000000000 "$node_(4) setdest 89.358018312247 111.148944536452 2.495704467666"
$ns_ at 0.000000000000 "$node_(5) setdest 135.062942291399 268.110055946021 4.483344306363"
$ns_ at 0.000000000000 "$node_(6) setdest 385.549422142627 160.173752244129 2.804756882458"
$ns_ at 0.000000000000 "$node_(7) setdest 4.032477861912 196.946147797815 4.562152814802"
$ns_ at 0.000000000000 "$node_(8) setdest 67.467440160277 192.012005413067 9.604977401401"
$ns_ at 0.000000000000 "$node_(9) setdest 248.405335440373 111.546030408138 2.180804359635"

$god_ set-dist 0 1 1
$god_ set-dist 0 2 1
$god_ set-dist 0 3 2
$god_ set-dist 0 4 1
$god_ set-dist 0 5 1
$god_ set-dist 0 6 2
$god_ set-dist 0 7 1
$god_ set-dist 0 8 1
$god_ set-dist 0 9 1
$god_ set-dist 1 2 1
$god_ set-dist 1 3 2
$god_ set-dist 1 4 1
$god_ set-dist 1 5 1
$god_ set-dist 1 6 2
$god_ set-dist 1 7 1
$god_ set-dist 1 8 1
$god_ set-dist 1 9 1
$god_ set-dist 2 3 2
$god_ set-dist 2 4 2
$god_ set-dist 2 5 1
$god_ set-dist 2 6 2
$god_ set-dist 2 7 1
$god_ set-dist 2 8 2
$god_ set-dist 2 9 1
$god_ set-dist 3 4 2
$god_ set-dist 3 5 2
$god_ set-dist 3 6 1
$god_ set-dist 3 7 1
$god_ set-dist 3 8 3
$god_ set-dist 3 9 3
$god_ set-dist 4 5 1
$god_ set-dist 4 6 2
$god_ set-dist 4 7 1
$god_ set-dist 4 8 1
$god_ set-dist 4 9 2
$god_ set-dist 5 6 2
$god_ set-dist 5 7 1
$god_ set-dist 5 8 1
$god_ set-dist 5 9 1
$god_ set-dist 6 7 1
$god_ set-dist 6 8 3
$god_ set-dist 6 9 3
$god_ set-dist 7 8 2
$god_ set-dist 7 9 2
$god_ set-dist 8 9 2

$ns_ at 2.344063786804 "$god_ set-dist 8 9 1"
$ns_ at 2.462274262284 "$god_ set-dist 1 6 1"
$ns_ at 2.462274262284 "$god_ set-dist 6 8 2"
$ns_ at 2.462274262284 "$god_ set-dist 6 9 2"
$ns_ at 4.314118212462 "$god_ set-dist 1 3 1"
$ns_ at 4.314118212462 "$god_ set-dist 3 8 2"
$ns_ at 4.314118212462 "$god_ set-dist 3 9 2"
$ns_ at 4.782261859889 "$god_ set-dist 5 6 1"

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
# Route Changes: 8
#
# Link Changes: 4
#
# Node | Route Changes | Link Changes
#    0 |             0 |            0
#    1 |             2 |            2
#    2 |             0 |            0
#    3 |             3 |            1
#    4 |             0 |            0
#    5 |             1 |            1
#    6 |             4 |            2
#    7 |             0 |            0
#    8 |             3 |            1
#    9 |             3 |            1
#
