# A 3-node example for ad-hoc simulation with AODV
# Define options
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
set val(x) 500 ;# X dimension of topography
set val(y) 300 ;# Y dimension of topography
set val(stop) 1 ;# time of simulation end
set ns [new Simulator]
set tracefd [open simple.tr w]
set windowVsTime2 [open win.tr w]
set namtrace [open simwrls.nam w]
$ns trace-all $tracefd
$ns namtrace-all-wireless $namtrace $val(x) $val(y)

#Coloration
$ns color 1 Blue
$ns color 2 Red

# set up topography object
set god_ [create-god $val(nn)]
set topo [new Topography]
$topo load_flatgrid $val(x) $val(y)
create-god $val(nn)

#
# Create nn mobilenodes [$val(nn)] and attach them to the channel.
#
# configure the nodes
$ns node-config -adhocRouting $val(rp) \
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
  set node_($i) [$ns node]
}



# Generation of movements
source "scene_ex1.tcl"

# Set a TCP connection between node_(0) and node_(1)
set tcp2 [new Agent/TCP/Newreno]
$tcp2 set class_ 1
set sink2 [new Agent/TCPSink]
$ns attach-agent $node_(0) $tcp2
$ns attach-agent $node_(5) $sink2
$ns connect $tcp2 $sink2
set ftp2 [new Application/FTP]
$ftp2 attach-agent $tcp2
$ftp2 set fid_ 2
$ns at 0.0 "$ftp2 start"

set tcp3 [new Agent/TCP/Newreno]
$tcp3 set class_ 2

set sink3 [new Agent/TCPSink]
$ns attach-agent $node_(2) $tcp3
$ns attach-agent $node_(9) $sink3
$ns connect $tcp3 $sink3

set ftp3 [new Application/FTP]
$ftp3 attach-agent $tcp3
$ns at 0.0 "$ftp3 start"

#Setup a UDP connection
set udp2 [new Agent/UDP]
$ns attach-agent $node_(8) $udp2

set null2 [new Agent/Null]
$ns attach-agent $node_(9) $null2
$ns connect $udp2 $null2

#appel de notre fonction
set traceCoord [open traceCoord.tr w]
$ns at 0.0 "appelRecord $traceCoord"

#Setup a CBR over UDP connection
set cbr2 [new Application/Traffic/CBR]
$cbr2 attach-agent $udp2
$cbr2 set type_ CBR
$ns at 0.0 "$cbr2 start"

# Define node initial position in nam
for {set i 0} {$i < $val(nn)} { incr i } {
  # 30 defines  the node size for nam
  $ns initial_node_pos $node_($i) 30
  
}

# Telling nodes when the simulation ends
for {set i 0} {$i < $val(nn) } { incr i } {
  $ns at $val(stop) "$node_($i) reset";
}

# ending nam and the simulation
$ns at $val(stop) "$ns nam-end-wireless $val(stop)"
$ns at $val(stop) "stop"
$ns at 10.01 "puts \"end simulation\" ; $ns halt"

proc stop {} {
  global ns tracefd namtrace
  $ns flush-trace
  close $tracefd
  close $namtrace
}

# Ecrit la coordonée des points pour chaque noeud au temps t
proc appelRecord {file} {
  global ns val node
  set time 1.0
  set now [$ns now]
  for {set i 0} {$i < $val(nn)} { incr i } {
    puts $file "$i [$node_($i) set X_] [$node_($i) set Y_]"
  }
  $ns at [expr $now+$time] "appelRecord $file"
}

$ns run