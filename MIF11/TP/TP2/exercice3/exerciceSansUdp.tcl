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

#couleurs
$ns color 0 Blue
$ns color 1 Red

#instantation de deux noeuds 
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

#instantiation d'une ligne de communication full duplex entre les noeuds n0 et n1
$ns duplex-link $n0 $n2 2Mb 5ms DropTail
$ns duplex-link $n1 $n2 2Mb 5ms DropTail

$ns duplex-link $n2 $n3 2Mb 5ms DropTail
#$ns duplex-link $n2 $n3 2Mb 5ms RED

#positionnement graphique des lignes de communication
$ns duplex-link-op $n1 $n2 orient right-up
$ns duplex-link-op $n0 $n2 orient right-down

$ns duplex-link-op $n2 $n3 orient right

#Taille des files d'attente
$ns queue-limit $n2 $n3 10

#positionnement graphique de la file
$ns duplex-link-op $n2 $n3 queuePos 0.5

#instantiation d'une connexions :
set tcp0 [new Agent/TCP]
$tcp0 set class_ 0

set tcp1 [new Agent/TCP]
$tcp1 set class_ 1

set sink0 [new Agent/TCPSink]
set sink1 [new Agent/TCPSink]

#on dirige les packets vers le bon agent
$ns attach-agent $n0 $tcp0
$ns attach-agent $n1 $tcp1

$ns attach-agent $n3 $sink0
$ns attach-agent $n3 $sink1

#on crée les connectionss
$ns connect $tcp0 $sink0
$ns connect $tcp1 $sink1

#tous les paquets TCP seront tagués par un 1, et les paquets UDP par 2
$tcp0 set fid_ 0
$tcp1 set fid_ 1

#instantiation des types des source FTP et CBR
set ftp0 [new Application/FTP]
$ftp0 set type_ FTP
$ftp0 set packetSize_ 1000
$ftp0 attach-agent $tcp0

set ftp1 [new Application/FTP]
$ftp1 set type_ FTP
$ftp1 set packetSize_ 1000
$ftp1 attach-agent $tcp1

#scénario de début et de fin de génération des paquets
$ns at 0.0 "$ftp0 start"
$ns at 10.0 "$ftp0 stop"

$ns at 0.0 "$ftp1 start"
$ns at 10.0 "$ftp1 stop"

#la simulation va durer 11 secondes de temps simulé
$ns at 10.0 "finish"

# début de la simulation
$ns run

#Exercice Coexistence de flots UDP et TCP
#1.
#----------------------------------------
#Débit moyen utile façon TP : 
#Débit moyen utile du flot 1 : 0.398400 Mbps 
#Débit moyen utile du flot 0 : 0.474400 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 0.872800 Mbps 

#----------------------------------------
#Rtt moyen façon TP : 
#Rtt moyen du flot 1 = 118.404764 ms 
#Rtt moyen du flot 0 = 113.427265 ms 

#------------------------------------------ 
#Rtt moyen façon TD : 
#Rtt moyen du flot 1 = 152.772527 ms 
#Rtt moyen du flot 0 = 141.998858 ms 

#------------------------------------------ 
#Taux de perte : 
#Taux de perte Flot 1 : 20.355731% 
#Taux de perte Flot 0 : 19.275124% 
#Débit moyen utile de TCP façon TD (only TCP) : 
#Débit moyen utile du flot 1 : 0.141599 Mbps 
#Débit moyen utile du flot 0 : 0.156555 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 0.298154 Mbps 

#2. Le débit moyen n'est pas le même entre la valeur mesurée et la valeur calculée.
#Le Rtt théorique est plus important que le mesuré, donc le débit théorique est plus petit que le débit mesuré.
