 #instantiation du simulateur
set ns [new Simulator]

#instantiation du fichier de traces
set file1 [open out.tr w]
$ns trace-all $file1

#instantiation du fichier de traces pour NAM
set file2 [open out.nam w]
$ns namtrace-all $file2

#procédure pour lancer auto le visualisateur NAM à la fin de la simu
proc finish {} {
	global ns file1 file2
	$ns flush-trace
	close $file1
	close $file2
	exec nam out.nam &
	exit 0
}

#instantation de deux noeuds 
set n0 [$ns node]
set n1 [$ns node]

#instantiation d'une ligne de communication full duplex entre les noeuds n0 et n1
$ns duplex-link $n0 $n1 1Mb 10ms DropTail

#instantiation d'une connexion UDP
set udp [new Agent/UDP]
$ns attach-agent $n0 $udp
set null [new Agent/Null]
$ns attach-agent $n1 $null
$ns connect $udp $null
$udp set fid_ 1

#instantiation d'un trafic CBR (Constant Bite Rite) composé de paquets de 1000octets, générés toutes 5ms
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp
$cbr0 set packetSize_ 1000
$cbr0 set interval_ 0.005


#scénario de début et de fin de génération des paquets par cbr0
$ns at 0.5 "$cbr0 start"
$ns at 4.5 "$cbr0 stop"

#la simulation va durer 5 secondes de temps simulé
$ns at 5.0 "finish"

# début de la simulation
$ns run


#Exercice 1
#3.
#10ms propagation
#Taille paquet : 1000 octets par paquets | Connexion : 1MBps -> 1 000 000 bytes/s = 125ko/s = 125 o/ms donc 1000/125 = 8ms 
#interval de transmission entre chaque paquet = 5ms (inutile dans notre calcul)
#On obtient un délai de bout en bout de 10ms + 8ms = 18ms (temps de traitement toujours nul)
#On peut vérifier dans le fichier out.tr

#5. - 0.58 0 1 cbr 1000 ------- 1 0.0 1.0 10 10  ==> donc date de départ 0.58 ms
#6. Temps d'attente (entre le + et -) = 30ms
#   Temps de transmission + propagation =  (r 0.598 0 1) - (- 0.58 0 1) = 18ms
#   On sait que Temps  de propagation = 10ms, donc temps de transmission = 8ms (Ok, calculé dans 3.)

#7. On retouve bien le même résultat que le 3
