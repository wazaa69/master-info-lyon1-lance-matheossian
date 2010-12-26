BEGIN {

  #------------question 1

  lastNumSeqUn = 0;
  lastNumSeqZero = 0;

  #------------question 5

  nbAckRecusUn = 0;
  RttTotalUn = 0;

  nbAckRecusZero = 0;
  RttTotalZero = 0;

  #------------question 6

  ISstartEstimatedRTTUn = 0;
  estimatedRTTUn = 1;
  sampleRTTUn = 0;

  ISstartEstimatedRTTZero = 0;
  estimatedRTTZero = 1;
  sampleRTTZero = 0;

  #------------question perso

  nb_paquetsEnvoyeUn = 0; #pour la question perso (en +)
  nb_paquetsEnvoyeZero = 0; #pour la question perso (en +)

}

{
  ligne = $0;
   event = $1;
   time = $2;
   fromNode = $3;
   toNode = $4 ;
   typepaquet = $5;
   sizepaquet = $6;
   flags = $7;
   FID = $8;
   src = $9
   dst = $10;
   numSequence = $11;
   idpaquet = $12;

   #-------------------------------------------------------question 1 - Calcul du nombre de parquets bien recu par le destinataire

   #On récupère le numéro de séquence du dernier ack = dernier paquet envoyé par 1
   if((event == "r") && (toNode == "1") && (typepaquet == "ack") && (FID == "1")){
	if(numSequence > lastNumSeqUn) lastNumSeqUn = numSequence;
   }

   #On récupère le numéro de séquence du dernier ack = dernier paquet envoyé par 0
   if((event == "r") && (toNode == "0") && (typepaquet == "ack") && (FID == "0")){
	if(numSequence > lastNumSeqZero) lastNumSeqZero = numSequence;
   } 



   #-------------------------------------------------------question 5 - Calcul du RTT moyen + le graph (façon TP, pour chaque flot)

   #--------------------------------- RTT façon TP - Pour le flot 1

   #Sauvegarde du départ de chaque paquets au noeud 1
   if((event == "+") && (fromNode == "1") && (typepaquet =="tcp") && (FID == "1") && tabEnvoieUn[numSequence] == "")
   {
	tabEnvoieUn[numSequence] = time;
	nb_paquetsEnvoyeUn++; #pour la question perso
   }

   #Si l'ack pour un numéro de séquence n'a pas déjà été reçu, on l'ajoute au tableau des Ack
   if((event == "r") && (toNode == "1") && (typepaquet =="ack") && (FID == "1") && tabAckUn[numSequence] == "")
   {
	tabAckUn[numSequence] = time;
	nbAckRecusUn++;
   }

   #--------------------------------- RTT façon TP - Pour le flot 0


   if((event == "+") && (fromNode == "0") && (typepaquet =="tcp") && (FID == "0") && tabEnvoieZero[numSequence] == "")
   {
	tabEnvoieZero[numSequence] = time;
	nb_paquetsEnvoyeZero++; #pour la question perso
   }

   if((event == "r") && (toNode == "0") && (typepaquet =="ack") && (FID == "0") && tabAckZero[numSequence] == "")
   {
	tabAckZero[numSequence] = time;
	nbAckRecusZero++;
   }



   #-------------------------------------------------------question 6 - Calcul du RTT moyen + le graph (façon TD, pour chaque flot)

   #--------------------------------- RTT façon TD - Pour le flot 1


	
   #Seulement à la reception du Ack sur le noeud 1, on "lisse" les valeurs du SampleRTT
   #On n'est pas obligé de mettre tabEnvoieZero[numSequence] != "", mais on voit une très légère diff pour le flot 0
   if((event == "r") && (toNode == "1") && (typepaquet =="ack") && (FID == "1") && tabEnvoieUn[numSequence] != "")
   {
	#calcul du nouveau sampleRTT (en ms) = temps mesuré entre la transmission du paquet et la réception du Ack
	#c'est ce qu'on a déjà fait dans la question 5 !
	newSampleRTTUn = time*1000 - tabEnvoieUn[numSequence]*1000; #tabEnvoieUn est déjà remplit

	#initialisation de estimatedRTTUn (en ms), lambda = 0.125
	#estimatedRTT = (1 - lambda)*estimatedRTT + lambda*sampleRTT
   	if(ISstartEstimatedRTTUn == 0){ISstartEstimatedRTTUn = 1; estimatedRTTUn = newSampleRTTUn;}
	else{estimatedRTTUn = ((1-0.125) * estimatedRTTUn) + (0.125 * newSampleRTTUn);}
	
	sommeEstimatedRttUn += estimatedRTTUn;

	#ajout du Temps actu(en secondes) + estimatedRTTUn(en ms) dans un fichier texte
	print time " " estimatedRTTUn > "estimatedRTTUn.txt"; #abscisse = temps, ordonnée = RTT estimé
   }



   #--------------------------------- RTT façon TD - Pour le flot 0


   if((event == "r") && (toNode == "0") && (typepaquet =="ack") && (FID == "0") && tabEnvoieZero[numSequence] != "")
   {
	newSampleRTTZero = time*1000 - tabEnvoieZero[numSequence]*1000;

   	if(ISstartEstimatedRTTZero == 0){ISstartEstimatedRTTZero = 1; estimatedRTTZero = newSampleRTTZero;}
	else{estimatedRTTZero = (0.875 * estimatedRTTZero) + (0.125 * newSampleRTTZero);}
	
	sommeEstimatedRttZero += estimatedRTTZero;

	print time " " estimatedRTTZero > "estimatedRTTZero.txt"; #abscisse = temps, ordonnée = RTT estimé
   }

   

   #-------------------------------------------------------question Perso - Calculer le débit moyen de TCP façon TD (flot 1 et 2)


   #calcul = 1.22*MSS / RTT*sqrt(Taux de Perte) voir slide 111 du poly 2
   #Maximum segment size = 1ko
   #on a déjà le RTT moyen de chaque flot mais pas les pertes.
   #on connait le nombre de packets bien reçus pour chaque flot =  lastNumSeqUn ou  lastNumSeqZero, on ajoute un compteur de paquet dans la question 5
   #reste plus qu'à faire le calcul dans le "END"



}


END {

	#-----------------Q1----------------

	#pour le TPDebitMoyenUtile :
	#en haut = on *8 pour avoir des bits
	#en bas = on /10 car le test se déroule sur 10 sec et on /1000 pour avoir des Mb => /10*1000

	TPDebitMoyenUtileUn = lastNumSeqUn*1*8/(10*1000);  #Mbps
	TPDebitMoyenUtileZero = lastNumSeqZero*1*8/(10*1000);  #Mbps


   	#-----------------Q5----------------RTT façon TP

	#on parcours le tableau des Ack ! Puisque des paquets ont pu être perdu
	for(i in tabAckUn){
		RttTotalUn += tabAckUn[i] - tabEnvoieUn[i];
	}

	for(i in tabAckZero){
		RttTotalZero += tabAckZero[i] - tabEnvoieZero[i];
	}


	if(nbAckRecusUn != 0)
		RttMoyenUn = (RttTotalUn/nbAckRecusUn)*1000; #en ms (le  *1000 pour mettre en ms)
	else
		RttMoyenUn = 1000000;

	if(nbAckRecusZero != 0)
		RttMoyenZero = (RttTotalZero/nbAckRecusZero)*1000; #en ms (le  *1000 pour mettre en ms)
	else
		RttMoyenZero = 1000000;



   	#-----------------Q6----------------RTT façon TD

	if(lastNumSeqUn != 0)
		MoyenneEstimatedRttUn = sommeEstimatedRttUn/lastNumSeqUn; #en ms
	else
		MoyenneEstimatedRttUn = 1000000;

	
	if(lastNumSeqZero != 0)
		MoyenneEstimatedRttZero = sommeEstimatedRttZero/lastNumSeqZero; #en ms
	else
		MoyenneEstimatedRttZero = 1000000;
	


	#-----------------QPerso----------------

	TauxPerteUn = (nb_paquetsEnvoyeUn - nbAckRecusUn)/nb_paquetsEnvoyeUn; #pourcentage
	TauxPerteZero = (nb_paquetsEnvoyeZero - nbAckRecusZero)/nb_paquetsEnvoyeZero; #pourcentage

	

	#version TD : Débit Moyen Utile = 1.22*MSS / RTT*sqrt(Taux de Perte)

	if(TauxPerteUn != 0) 
		TDDebitMoyenUtileUn = 1.22 * 0.001 * 8 /((MoyenneEstimatedRttUn/1000) * sqrt(TauxPerteUn)); #Mbps
	else
		TDDebitMoyenUtileUn = 1.22 * 0.001 * 8 /(MoyenneEstimatedRttUn/1000); #Mbps

	
	if(TauxPerteZero != 0) 
		TDDebitMoyenUtileZero = 1.22 * 0.001 * 8 /((MoyenneEstimatedRttZero/1000) * sqrt(TauxPerteZero)); #Mbps
	else
		TDDebitMoyenUtileZero = 1.22 * 0.001 * 8 /(MoyenneEstimatedRttZero/1000); #Mbps



   	#---------------------------------Bilan des questions 1,5,6, + quesion perso
	
	printf("----------------------------------------\n");
	printf("Débit moyen utile façon TP : \n");
	printf("Débit moyen utile du flot 1 : %f Mbps \n", TPDebitMoyenUtileUn);
	printf("Débit moyen utile du flot 0 : %f Mbps \n", TPDebitMoyenUtileZero);
	printf("Débit moyen utile du flot 1 + flot 0 : %f Mbps \n", TPDebitMoyenUtileUn + TPDebitMoyenUtileZero);

	printf("\n----------------------------------------\n");
	printf("Rtt moyen façon TP : \n");
	printf("Rtt moyen du flot 1 = %f ms \n", RttMoyenUn);
	printf("Rtt moyen du flot 0 = %f ms \n", RttMoyenZero);

	printf("\n------------------------------------------ \n");
	printf("Rtt moyen façon TD : \n");
	printf("Rtt moyen du flot 1 = %f ms \n", MoyenneEstimatedRttUn);
	printf("Rtt moyen du flot 0 = %f ms \n", MoyenneEstimatedRttZero);

	printf("\n------------------------------------------ \n");
	printf("Taux de perte : \n");
	printf("Taux de perte Flot 1 : %f% \n", TauxPerteUn*100);
	printf("Taux de perte Flot 0 : %f% \n", TauxPerteZero*100);
	printf("Débit moyen utile de TCP façon TD (only TCP) : \n");
	printf("Débit moyen utile du flot 1 : %f Mbps \n", TDDebitMoyenUtileUn);
	printf("Débit moyen utile du flot 0 : %f Mbps \n", TDDebitMoyenUtileZero);
	printf("Débit moyen utile du flot 1 + flot 0 : %f Mbps \n", TDDebitMoyenUtileUn + TDDebitMoyenUtileZero);

    }
