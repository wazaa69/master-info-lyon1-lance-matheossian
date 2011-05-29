BEGIN {
     loss0 = 0;
	  loss1 = 0;
     pkt_send0 = 0;
	  pkt_send1 = 0;
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

#   if(typepaquet == "cbr")
#	{
#		  if(FID == "1") pkt_send0++;
#		  if(FID == "2") pkt_send1++;
#
#        if((event == "d" || event == "e") && FID == "1")
#		  {
#                loss0++;
#        }
#        if((event == "d" || event == "e") && FID == "2")
#		  {
#                loss1++;
#        }
#   } 


   if(typepaquet == "cbr")
	{
		  pkt_send0++;

        if(event == "d" || event == "e")
		  {
                loss0++;
        }
   } 

   if(typepaquet == "tcp")
	{
		  pkt_send1++;

        if(event == "d" || event == "e")
		  {
                loss1++;
        }
   }

}


END {
		  printf("Nb paquets envoyés pour le flux 1 : %d\n", pkt_send0);
		  printf("Nb paquets perdus pour le flux 1 : %d\n", loss0);
		  printf("-------------------------------------------\n", pkt_send1);
		  printf("Nb paquets envoyés pour le flux 2 : %d\n", pkt_send1);
		  printf("Nb paquets perdus pour le flux 2 : %d\n", loss1);
}

