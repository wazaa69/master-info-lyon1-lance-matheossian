BEGIN {
	i = 0;
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


   #r : le paquet a été reçu à la sortie du lien (au noeud suivant)
   #+ : le paquet a été mis en attente dans une file (celle de l'inerface de sortie vers le neoud suivant)
   #- : le paquet a quitté la file d'attente (correspond au début de la transmission sur le lien)
   #d : le paquet a éré retiré de la file (correspond a une perte)
   #e : le paquet a subi une erreur (corresond à une erreur binaire)


   #on compte le nombre de lignes
   tabligne[i]=ligne;
   i ++;


   #ligne classique de recherche, comme la commande :
   #cat out.tr | grep '-' | grep '1 0 ack' | grep '\- 0' | grep ' 26 [0-9]*$
   if(event == "-" && fromNode == "1" && toNode == "0" && typepaquet == "ack" && FID == "0" && numSequence == "40"){
	printf("\n temps : %fsec -> numSequence : %d  \n", time, numSequence);
   }

}


END {
		
  for(i in tabligne){
    #printf("%s\n",  tabligne[i]);
  }

}
