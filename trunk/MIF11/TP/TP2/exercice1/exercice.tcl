 #instantiation du simulateur
set ns [new Simulator]

#instantiation du fichier de traces
set file1 [open out.tr w]
$ns trace-all $file1

#instantiation du fichier de traces pour NAM
set file2 [open out.nam w]
$ns namtrace-all $file2

#création ou ouverture du graph
set graphReno [open graphReno.txt w]
set graphTaho [open graphThao.txt w]

#procédure pour lancer auto le visualisateur NAM à la fin de la simu
proc finish {} {
	global ns file1 file2
	$ns flush-trace
	close $file1
	close $file2
	exec nam out.nam &
	exit 0
}


#Pour créer le fichier texte du graph de la fenêtre de congestion et du sstresh
proc plot_cwnd {tcp_src plot_file} {

	global ns
	set time_interval 0.1

	#création des points
	set now [$ns now]
	set cwnd [$tcp_src set cwnd_]
	set ssthresh [$tcp_src set ssthresh_]

	#écriture dans le fichier txt : colonne 1 = $now, colonne 2 = $ssthresh, colonne 3 = $cwnd
	puts $plot_file "$now $ssthresh $cwnd"

	$ns at [expr $now + $time_interval] "plot_cwnd $tcp_src $plot_file"
	
}


#couleurs
$ns color 1 Blue
$ns color 2 Red

#instantation de deux noeuds 
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

#instantiation d'une ligne de communication full duplex entre les noeuds n0 et n1
$ns duplex-link $n0 $n1 2Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 20ms DropTail

#positionnement graphique des lignes de communication
$ns duplex-link-op $n0 $n1 orient right
$ns duplex-link-op $n1 $n2 orient right

#Taille des files d'attente
$ns queue-limit $n0 $n1 10
$ns queue-limit $n1 $n2 10

#positionnement graphique de la file
$ns duplex-link-op $n0 $n1 queuePos 0.5
$ns duplex-link-op $n1 $n2 queuePos 0.5

#instantiation d'une connexions :
#pour n1
set tcp [new Agent/TCP/Newreno]
$tcp set class_ 1

#pour n2
set sink [new Agent/TCPSink]

#on dirige les packets TCP vers sink
$ns attach-agent $n0 $tcp
$ns attach-agent $n2 $sink

#on crée les connections
$ns connect $tcp $sink

#tous les paquets TCP seront tagués par un 1, et les paquets UDP par 2
$tcp set fid_ 1
#$udp set fid_ 2

#instantiation des types des source FTP et CBR
set ftp [new Application/FTP]
$ftp set type_ FTP
$ftp set packetSize_ 1000
$ftp attach-agent $tcp

#Pour TCP Taho
$ns at 0.1 "plot_cwnd $tcp $graphTaho"
$ns at 0.1 "plot_cwnd $tcp $graphReno"

#scénario de début et de fin de génération des paquets
$ns at 1.0 "$ftp start"
$ns at 5.0 "$ftp stop"

#$ns at 2.0 "$cbr start"
#$ns at 4.0 "$cbr stop"


#la simulation va durer 5 secondes de temps simulé
$ns at 6.0 "finish"

# début de la simulation
$ns run

#Exercice Evolution de la fenêtre de congestion
#2.
#Pour affiche une courbe :
#gnuplot -> plot 'graphTaho.txt' w linespoint 1

#3.
#Pour afficher plusieurs courbres 1:2  => colonne 1: colonne 2 du fichier texte :
#plot 'graphTaho.txt'using 1:2 with lines, 'graphTaho.txt' using 1:3 with lines;

#Pour sauvgarder en .ps (faire après la commande ns):
#set terminal postscript
#set output 'graphTaho.ps'
#plot 'graphTaho.txt'using 1:2 with lines, 'graphTaho.txt' using 1:3 with lines;

#Pour mettre en png
#set terminal png size 800,600 medium
#set output 'graphTaho.png'
#plot 'graphTaho.txt'using 1:2 with lines, 'graphTaho.txt' using 1:3 with lines;

#4. Tahoe => slow start
#http://eugen.dedu.free.fr/teaching/tcp/cours.pdf
#ssthresh = valeur arbitraire
#p25-26 du cour.pdf pour explication
# -> 1er pik --> quand on passe au dessus du seuil (juste un effet des mesures, ne pas s'ne préoccupé)
# -> perte d'un paquet => sstresh = cwd/2s puis cwd = 1
# -> deuxième pik, congestion avoidance à partir du trait rouge, puis perte d'un paquet
# -> donc division du sstresh pas rapport au pik vert courant 19/2=9.5, arrondi à sstresh = 9
# -> pareil pr le 3eme pik
# -> puis fin de transmission après le 4ème pik

#5.
#La fast recovery des paquets perdus est prise en compte par Newreno
# cwd = sstresh lors d'un paquet perdu
# retour au congestion avoidance

