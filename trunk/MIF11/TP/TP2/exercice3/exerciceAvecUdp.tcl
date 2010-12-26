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
$ns color 2 Green

#instantation de deux noeuds 
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]

#instantiation d'une ligne de communication full duplex entre les noeuds n0 et n1
$ns duplex-link $n0 $n3 2Mb 5ms DropTail
$ns duplex-link $n1 $n3 2Mb 5ms DropTail
$ns duplex-link $n2 $n3 0.4364Mb 5ms DropTail

#$ns duplex-link $n3 $n4 1Mb 20ms DropTail
$ns duplex-link $n3 $n4 1Mb 20ms RED

#positionnement graphique des lignes de communication
$ns duplex-link-op $n2 $n3 orient right-up
$ns duplex-link-op $n1 $n3 orient right
$ns duplex-link-op $n0 $n3 orient right-down

$ns duplex-link-op $n3 $n4 orient right

#Taille des files d'attente
$ns queue-limit $n3 $n4 10

#positionnement graphique de la file
$ns duplex-link-op $n3 $n4 queuePos 0.5

#instantiation d'une connexions :
set tcp0 [new Agent/TCP]
$tcp0 set class_ 0

set tcp1 [new Agent/TCP]
$tcp1 set class_ 1

set udp [new Agent/UDP]
$tcp1 set class_ 2

set sink0 [new Agent/TCPSink]
set sink1 [new Agent/TCPSink]
set null2 [new Agent/Null]

#on dirige les packets vers le bon agent
$ns attach-agent $n0 $tcp0
$ns attach-agent $n1 $tcp1
$ns attach-agent $n2 $udp

$ns attach-agent $n4 $sink0
$ns attach-agent $n4 $sink1
$ns attach-agent $n4 $null2

#on crée les connectionss
$ns connect $tcp0 $sink0
$ns connect $tcp1 $sink1
$ns connect $udp $null2

#tous les paquets TCP seront tagués par un 1, et les paquets UDP par 2
$tcp0 set fid_ 0
$tcp1 set fid_ 1
$udp set fid_ 2

#instantiation des types des source FTP et CBR
set ftp0 [new Application/FTP]
$ftp0 set type_ FTP
$ftp0 set packetSize_ 1000
$ftp0 attach-agent $tcp0

set ftp1 [new Application/FTP]
$ftp1 set type_ FTP
$ftp1 set packetSize_ 1000
$ftp1 attach-agent $tcp1

set cbr [new Application/Traffic/CBR]
$cbr attach-agent $udp
$cbr set packetSize_ 1000


#scénario de début et de fin de génération des paquets
$ns at 0.0 "$ftp0 start"
$ns at 10.0 "$ftp0 stop"

$ns at 0.0 "$ftp1 start"
$ns at 10.0 "$ftp1 stop"

$ns at 0.0 "$cbr start"
$ns at 10.0 "$cbr stop"

#la simulation va durer 11 secondes de temps simulé
$ns at 10.0 "finish"

# début de la simulation
$ns run

#Exercice Coexistence de flots UDP et TCP
#Suite
#3. On prend donc 0.4364Mbps  pour udp
#4.
#----------------------------------------
#Débit moyen utile façon TP : 
#Débit moyen utile du flot 1 : 0.276800 Mbps 
#Débit moyen utile du flot 0 : 0.263200 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 0.540000 Mbps 

#----------------------------------------
#Rtt moyen façon TP : 
#Rtt moyen du flot 1 = 131.104828 ms 
#Rtt moyen du flot 0 = 135.901617 ms 

#------------------------------------------ 
#Rtt moyen façon TD : 
#Rtt moyen du flot 1 = 173.433556 ms 
#Rtt moyen du flot 0 = 175.878519 ms 

#------------------------------------------ 
#Taux de perte : 
#Taux de perte Flot 1 : 24.719101% 
#Taux de perte Flot 0 : 23.582090% 
#Débit moyen utile de TCP façon TD (only TCP) : 
#Débit moyen utile du flot 1 : 0.113188 Mbps 
#Débit moyen utile du flot 0 : 0.114274 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 0.227462 Mbps

#4.Le RTT à augmenter de 20ms environ, le  débit à presque été divisé en 2 et les pertes son de 4 à 5% plus importantes.
#Pourquoi ?
#Flo : Suite à l'introduction du flux UDP, le file dans n3 est pleine plus souvent ce qui augmente le taux de perte,
#retarde l'envoie des ack et donc augmente le RTT moyen.

#5.Avec RED

#Sans UDP

#----------------------------------------
#Débit moyen utile façon TP : 
#Débit moyen utile du flot 1 : 0.859200 Mbps 
#Débit moyen utile du flot 0 : 0.854400 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 1.713600 Mbps 

#----------------------------------------
#Rtt moyen façon TP : 
#Rtt moyen du flot 1 = 41.157146 ms 
#Rtt moyen du flot 0 = 42.467534 ms 

#------------------------------------------ 
#Rtt moyen façon TD : 
#Rtt moyen du flot 1 = 53.391790 ms 
#Rtt moyen du flot 0 = 54.173987 ms 

#------------------------------------------ 
#Taux de perte : 
#Taux de perte Flot 1 : 23.708487% 
#Taux de perte Flot 0 : 23.902894% 
#Débit moyen utile de TCP façon TD (only TCP) : 
#Débit moyen utile du flot 1 : 0.375425 Mbps 
#Débit moyen utile du flot 0 : 0.368497 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 0.743922 Mbps 


#Avec UDP

#----------------------------------------
#Débit moyen utile façon TP : 
#Débit moyen utile du flot 1 : 0.245600 Mbps 
#Débit moyen utile du flot 0 : 0.302400 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 0.548000 Mbps 

#----------------------------------------
#Rtt moyen façon TP : 
#Rtt moyen du flot 1 = 110.237027 ms 
#Rtt moyen du flot 0 = 104.227786 ms 

#------------------------------------------ 
#Rtt moyen façon TD : 
#Rtt moyen du flot 1 = 152.011842 ms 
#Rtt moyen du flot 0 = 137.714330 ms 

#------------------------------------------ 
#Taux de perte : 
#Taux de perte Flot 1 : 27.564103% 
#Taux de perte Flot 0 : 26.892950% 
#Débit moyen utile de TCP façon TD (only TCP) : 
#Débit moyen utile du flot 1 : 0.122293 Mbps 
#Débit moyen utile du flot 0 : 0.136663 Mbps 
#Débit moyen utile du flot 1 + flot 0 : 0.258956 Mbps

#Le débit utile n'a pas beaucoup changé, les pertes ont augmentées de 3-4% et les RTT ont gagnés 20-30ms, ce qui n'est pas négligeable.
# A revoir :
#Le RTT à diminué alors que le taux de perte a augmenté, on peut penser que les paquets se suivent bien et ne sont pas perdu consécutivement.
