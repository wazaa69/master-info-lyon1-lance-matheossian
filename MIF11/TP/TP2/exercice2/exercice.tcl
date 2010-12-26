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
$ns duplex-link $n0 $n1 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 2Mb 10ms DropTail
$ns duplex-link $n2 $n3 2Mb 10ms DropTail

#positionnement graphique des lignes de communication
$ns duplex-link-op $n0 $n1 orient right
$ns duplex-link-op $n1 $n2 orient right
$ns duplex-link-op $n2 $n3 orient right


#Taille des files d'attente
$ns queue-limit $n1 $n2 10

#positionnement graphique de la file
$ns duplex-link-op $n1 $n2 queuePos 0.5

#instantiation d'une connexions :
set tcp0 [new Agent/TCP]
$tcp0 set class_ 0

set tcp1 [new Agent/TCP]
$tcp1 set class_ 1

set sink2 [new Agent/TCPSink]
set sink3 [new Agent/TCPSink]

#on dirige les packets TCP vers sink
$ns attach-agent $n0 $tcp0
$ns attach-agent $n1 $tcp1
$ns attach-agent $n2 $sink2
$ns attach-agent $n3 $sink3

#on crée les connectionss
$ns connect $tcp0 $sink3
$ns connect $tcp1 $sink2

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

#Exercice Flots TCP concurents
#1 et 2. débit moyen utile = (nb ack total * taille packet) / temps du flot

#3. Pas entierement, car les deux flot se font de la concurence

#4. Pour le flot 1 : cat out.tr | grep '\- 1' | grep ' 26 [0-9]*$'
# Le numéro de séquence délivré par le ack = dernier packet bien reçut.
# Le recepteur envoie un ack correspondant au dernier paquet bien reçu.

#5. RTT (Round Trip Time) = temps  entre l’envoi d’un paquet et la réception de son accusé
#Exemple pour le RTT du flot 0 :
# RTT -> premier (+) sur le noeud 0 et premier (r) de l'ack sur ce même noeud
# (1)-liste des (+) de type tcp de fid 0 qui partent du noeud 0 
# (2)-liste des (r) de type ack de fid 0 qui arrivent sur le noeud 0
# on parcour les 2 tableaux et on fait tempsTotal = (2)[n]-(1)[n]
# RTT = tempsTotal/numeroDeSéquence (dernier numSéquence = nb ack reçu = nb paquets bien reçu par le recepteur)
# Rtt moyen du flot 1 = 62.424581 ms 
# Rtt moyen du flot 0 = 104.259146 ms 
# Si certains packets sont perdus ou avec une erreur, on en tient pas compte => ce qui implique d'utiliser le n° de sequence des ack.

#7.Calcul avec formule du TD (les deux flots commencent et terminent en même temps) :
#Flot 1 : grand RTT au départ (220ms max) puis stabilisation vers RTT=85ms environ
#Flot 0 : grand RTT au départ (115ms max) puis stabilisation vers RTT=104ms environ
#Si le flot 1 part avant le 0 (ou inversement), le flot 0 à toujours un plus grand RTT car le destinataire est plus loin
#Comment mieux répatir ?
#"Si k connexiosn rivalisent sur le mm lien de goulet d'étranglement de capacité R, chacune devrait avoir un débit R/K""
#cela fonctionne lorsque les connexions ont les mm MSS et RTT
#les flux avec petit RTT sont agrssif vis a vis des autre flux
#Réponse : Donc on en déduit qu'il faut diminuer le RTT du flot qui a le plus petit débit
#et la seul facon de modifier le RTT, c'est d'augmenter la taille du lien et la taille de la fenetre de congestion

