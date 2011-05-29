set ns [new Simulator]

# Le fichier trace
set tf [open out.tr w]
$ns trace-all $tf

# paramèetre des lois exponentielles
set lambda 30.0
set mu 33.0

# K = taille de la file
set qsize 5

# Duréee de simulation
set duration 200

# créetion du réeseau
set n1 [$ns node]
set n2 [$ns node]
set link [$ns simplex-link $n1 $n2 100kb 0ms DropTail]
$ns queue-limit $n1 $n2 $qsize

# Génération aléatoire des intervalles de temps et des tailles de paquets.
set InterArrivalTime [new RandomVariable/Exponential]
$InterArrivalTime set avg_[expr 1/$lambda] #si on a une infinité de de valeur aléatoire, on aura une moyenne  de 1/$lambda

# Puisque les tailles des paquets seront arrondies à des valeurs entières,
# nous devons avoir une tailles max importante afin de diminuer l'erreur d'arrondi.
set pktSize [new RandomVariable/Exponential]
$pktSize set avg_ [expr 100000.0/(8*$mu)]

# création et attachement des agents de transport.
set src [new Agent/UDP]
$src set packetSize_ 100000
$ns attach-agent $n1 $src

set sink [new Agent/Null]
$ns attach-agent $n2 $sink
$ns connect $src $sink

# Procédure d'envoi des paquets
proc sendpacket {} {
	global ns src InterArrivalTime pktSize
	set time [$ns now]
	$ns at [expr $time + [$InterArrivalTime value]] "sendpacket"
	# la fonction round permet l'arrondi en valeur entière
	set bytes [expr round ([$pktSize value])]
	$src send $bytes
}

# queue monitoring : moniteur de la file d'attente
# le fichier qm.out contiendra la trace correspondante.
# le format de cette trace est :
# Time From To qSize qSize Departed Arrived Dropped Departed Arrived Dropped
set qmon [$ns monitor-queue $n1 $n2 [open qm.out w] 0.1]
$link queue-sample-timeout
proc finish {} {
	global ns tf
	$ns flush-trace
	close $tf
	exit 0
}
$ns at 0.0001 "sendpacket"
$ns at $duration "finish"
$ns run
