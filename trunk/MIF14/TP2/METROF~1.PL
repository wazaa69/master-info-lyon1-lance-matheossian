/*appart vérifie si un élément donné appartient à une liste*/
appart(X,[X|_]).
appart(X,[_|Z]):-appart(X,Z).


%************************CONFIGURATION DU RESEAU LYONNAIS*************************

/*ligne indique la liste des stations qui appartiennent à une ligne
  Ce prédicat nous permet, lorsqu'on lui passe un nom de ligne en paramètre,de récupérer la liste de 
  stations correspondantes.*/
ligne(a,[perrache,ampere,bellecour,cordeliers,hotel_de_ville,foch,massena,charpennes,republique,gratte-ciel,flachet,cusset,laurent_bonnevay]).
ligne(b,[charpennes,brotteaux,part-dieu,place_guichard,saxe-gambetta,jean_mace,place_jean_jaures,debourg,stade_de_gerland]).
ligne(c,[hotel_de_ville,croix-paquet,croix-rousse,henon,cuire]).
ligne(d,[gare_de_vaise,valmy,gorge_de_loup,vieux_lyon,bellecour,guillotiere,saxe-gambetta,garibaldi,sans-souci,monplaisir,grange_blanche,lannec,mermoz_pinel,parilly,gare_de_venissieux]).


/*quelle_ligne permet de trouver sur quelle ligne se trouve une station donnée.*/
quelle_ligne(X,L):- ligne(L,B),appart(X,B).


/*meme_ligne vérifie que deux stations sont sur la meme ligne.*/
meme_ligne(X,Y,L):- ligne(L,A),appart(X,A),appart(Y,A).


/*rang indique la position d'une station donnée sur une ligne.*/    
rang(S,[S|_],1).
rang(S,[_|Z],P):- rang(S,Z,T),P is T+1.


/*
Direction indique le sens de déplacement sur une ligne donnée. 
Parametres: 
	- station1,
	- station2,
	- ligne à laquelle elles appartiennent,
	- direction,
	- le nombre de stations parcourues entre la station1 et la station2.
*/
direction(X,Y,L,D,C):- ligne(L,Ligne),rang(X,Ligne,P1),rang(Y,Ligne,P2),P2>P1,C is P2-P1,length(Ligne,N),rang(D,Ligne,N).
direction(X,Y,L,D,C):- ligne(L,Ligne),rang(X,Ligne,P1),rang(Y,Ligne,P2),P2<P1,C is P1-P2,rang(D,Ligne,1).


/*recherche croisement permet de trouver le permier croisement d'une ligne donnée a avec une autre.*/
recherche_croisement(L,SC):- ligne(L,Ligne),appart(SC,Ligne),ligne(L2,Lig2),L2\==L,appart(SC,Lig2).


%***************************CALCULS DE FIN DE PARCOURS*****************************


/*nbstation calcule la somme des entiers appartenant à une liste donnée.
  On initialise S à la fin de la récursivité et au fur et à mesure que l'on remonte la récursivité,on 
  incrémente S*/
nbstation(0,[]).
nbstation(S,[X|Z]):- nbstation(M,Z),S is M+X. 


/*nb_changement permet de calculer le nombre de changements de ligne effectués lors d'un voyage, et ce 
  pour chaque voyage possible d'une station a une autre. Ce prédicat peut etre utilisée après le findall.
  Nbb est une liste qui contiendra le nombre de changements effectués pour chaque trajet.*/
nb_changement(Nb,[],Nb).
nb_changement(Nb,[X|L],Nbb):- length(X,N),append(Nb,[N],T),nb_changement(T,L,Nbb).


%***********************************AFFICHAGE***************************************

/*Affichage_liste permet d'afficher tout le parcours du voyageur à partir de la liste I.*/
affichage_liste([]).
affichage_liste([[X,Y,Z]|II]):- 
write('Vous prenez la ligne '),
write(Y),
write(' en direction de '),
write(X),
write(' jusqu''à '),
write(Z),nl,
affichage_liste(II).


/*Affichage_lignes affiche l'ensemble des lignes du métro lyonnais*/
affichage_lignes:- write('*** Ligne A ***'),nl, 
		ligne(a,LA),
		write(LA),nl,nl,
		write('*** Ligne B ***'),nl,
		ligne(b,LB),
		write(LB),nl,nl,
		write('*** Ligne C ***'),nl, 
		ligne(c,LC),
		write(LC),nl,nl,		
		write('*** Ligne D ***'),nl,
		ligne(d,LD),
		write(LD),nl.


%************************CALCUL DU MININUM DANS UNE LISTE****************************

/*Recherche du plus petit élément dans une liste*/

min([L],L).
min([A,B|L],Re):- A>B,min([B|L],Re).
min([A,B|L],Re):- B>=A,min([A|L],Re).


%****************TRAJET DE METRO SIMPLE,ON RESTE SUR LA MEME LIGNE DE MÉTRO******************

/*Condition d'arret: trajet d'une station à elle-meme*/
parcours(S1,S1,_,_,_,_,_).

parcours(S1,S2,I,I,J,Z,Z) :- 
	meme_ligne(S1,S2,Ligne),nl,
	direction(S1,S2,Ligne,Dir,C),
	append(J,[C],W),
	nbstation(S,W),Z is S, 
	append(I,[[Dir,Ligne,S2]],T),
	affichage_liste(T),nl,
	length(I,N),

	write('Nombre de stations à parcourir: '),write(Z),nl,
	write('Nombre de changements à effectuer:  '),write(N),nl,nl,
	write('			--------------------'),nl,nl.

/*Lors de la recherche d'un parcours entre deux stations données, le prédicat parcours teste d'abord
si les deux stations sont sur la meme ligne de métro.
Quelque soit le parcours effectué, les différents déplacements du voyageur entre les différents 
changements de lignes, sont stockés dans la liste I sous la forme: [Direction,Ligne,Station de Croisement].
Cette liste sera utilisée lors de l'affichage de tout le parcours du voyageur.*/ 


%******************************TRAJET DE MÉTRO AVEC CROISEMENT********************************
/*On doit rappeler en recursif le prédicat parcours en prenant comme station de départ le 
croisement trouvé*/



parcours(S1,S2,I,II,J,_,ZZ) :- 
	not(meme_ligne(S1,S2,Ligne)),
	quelle_ligne(S1,Ligne),
	recherche_croisement(Ligne,SC),
	direction(S1,SC,Ligne,Dir,C),
	append(J,[C],W),
	not(appart([_,_,SC],I)),
	not(appart([_,Ligne,_],I)),
	append(I,[[Dir,Ligne,SC]],T),
	parcours(SC,S2,T,II,W,_,ZZ).



/*I est une liste de listes, [Direction,ligne,Station de croisement].
  Avec le deuxieme not appart, à chaque rencontre de croisement, le voyageur change de ligne, cela évite
  au voyageur de revenir sur ses pas*/

%******************************************MENU********************************************

metro:- menu.

menu :- write('		      ************************************'),nl,
	write('		      **          METRO DE LYON	        **'),nl,
	write('		      ************************************'),nl,nl,nl,
	write('	----------'),nl,
	write('	|  MENU  |'),nl,
	write('	----------'),nl,nl,
	write('1- Afficher toutes les lignes de métro'),nl,
	write('2- Chercher un itinéraire particulier avec le nombre de stations à parcourir '),nl,
	write('3- Chercher un itinéraire particulier avec le nombre de changements à effectuer '),nl,
	write('4- Quitter ?'),nl,nl,
	write('Choix => '),
	read(Choix),
	fonction(Choix).
	
	
/*On traite la réponse donnée par le voyageur, et selon son choix, on lance les traitements correspondants*/
fonction(1):- affichage_lignes,nl,
		write('Avez-vous une autre demande ?(pour oui,tapez 1. / pour non tapez 2.)'),nl,
		write('Choix => '),read(Rep),
		reponse(Rep).

fonction(2):- write('Tapez votre station de départ : '),read(S1),
	write('Tapez votre station d''arrivée : '),read(S2),nl,nl,
	write('-------------------------PARCOURS POSSIBLES-------------------------'),nl,
	findall(ZZ,parcours(S1,S2,[],_,[],_,ZZ),Listezz),
	min(Listezz,Resultat),
	rang(Resultat,Listezz,R),
	write('LE PARCOURS LE PLUS COURT EST LE PARCOURS N°  '),write(R),nl,
	nl,write('Avez-vous une autre demande ?(pour oui,tapez 1. / pour non tapez 2.)'),nl,
	write('Choix => '),read(Rep),
	reponse(Rep).

fonction(3):- write('Tapez votre station de départ : '),read(S1),
	write('Tapez votre station d''arrivée : '),read(S2),nl,nl,
	write('-------------------------PARCOURS POSSIBLES-------------------------'),nl,
	findall(II,parcours(S1,S2,[],II,[],_,_),Listeii),
	nb_changement(_,Listeii,Nb),
	min(Nb,Resultat),
	rang(Resultat,Nb,R),
	write('LE PARCOURS CONTENANT LE MOINS DE CHANGEMENTS EST LE PARCOURS N°  '),write(R),nl,
	nl,write('Avez-vous une autre demande ?(pour oui,tapez 1. / pour non tapez 2.)'),nl,
	write('Choix => '),read(Rep),
	reponse(Rep).


fonction(4):- write('******* MERCI D''AVOIR CHOISI NOTRE RESEAU *******'),nl,
	write('**** Nous vous souhaitons un agréable trajet ****'),nl,nl,
	write(' Le Métro de Lyon vous souhaite une BONNE et HEUREUSE ANNEE!'). 


/*reponse permet de relancer le menu après une recherche ou de quitter le programme*/
reponse(1):- nl,nl,menu.
reponse(2):- nl,nl,fonction(4).





