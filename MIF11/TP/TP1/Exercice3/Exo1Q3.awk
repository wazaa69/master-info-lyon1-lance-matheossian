BEGIN {

	if((ARGC !=1)) {
			printf("Usage : out.tr | awk -f Script.awk -v flow_id=2/n");
			exit 1;
			}
	loss = 0;
	pkt_send = 0;
}

{
   event = $1;
   time = $2;
   fromNode = $3;
   bNode = $4 ;
   typepaquet = $5;
   sizepaquet = $6;
   Flags = $7;
   FID = $8;
   src = $9
   dst = $10;
   NumSequence = $11;
   idpaquet = $12;
}

{
   #printf("On affiche l'id du paquet\n", idpaquet);
   if((typepaquet == "cbr") && (time >= 1) == (time <= 2) && (NumSequence%2 == 0)) {
	pkt_send++;
	if(event == "d" || event == "e") {
		loss++;
        }
   } 
}


END {
	if((ARGC == 1)) {
		printf("le taux de perte vaut %g, en effet %d paquets ont ete perdus parmi les %d envoyes\n", loss/pkt_send, loss, pkt_send);
    	}
    }
