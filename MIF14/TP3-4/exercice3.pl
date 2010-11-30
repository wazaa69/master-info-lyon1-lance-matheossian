/* Contenance max des cruches */
tailleCruche(1, 8).
tailleCruche(2, 5).


/* Etat initial et buts */
initial(etat(0,0)).
final(etat(4,0)).
final(etat(3,1)).
final(etat(2,2)).
final(etat(1,3)).
final(etat(0,4)).

isFinal(X) :- final(X).


/* Etats interdits */
interdit(etat(Cruche1, _)) :- Cruche1 > 8.
interdit(etat(_, Cruche2)) :- Cruche2 > 5.
interdit(etat(Cruche1, _)) :- Cruche1 < 0.
interdit(etat(_, Cruche2)) :- Cruche2 < 0.



/* Remplir cruche 1 */
etatsuivant(etat(_, Cruche2), r1, etat(8, Cruche2)) :- not(interdit(etat(8, Cruche2))).

/* Remplir cruche 2 */
etatsuivant(etat(Cruche1, _), r2, etat(Cruche1, 5)) :- not(interdit(etat(Cruche1, 5))).

/* Vider cruche 1 */
etatsuivant(etat(_, Cruche2), r3, etat(0, Cruche2)) :- not(interdit(etat(0, Cruche2))).

/* Vider cruche 2 */
etatsuivant(etat(Cruche1, _), r4, etat(Cruche1, 0)) :- not(interdit(etat(Cruche1, 0))).



/* On vide cruche 1 dans cruche 2 */
etatsuivant(etat(Cruche1, Cruche2), r5, etat(CrucheTmp1, T2)) :-
    tailleCruche(2, T2), /* on récupère la taille de la cruche 2 */
    Max2 is T2 - Cruche2, /* Max2 = place restante dans la cruche 2 */
    Cruche1 >= Max2, /* si la place restante est plus petite que le contenu de la cruche 1*/
    CrucheTmp1 is Cruche1 - Max2,  
    not(interdit(etat(CrucheTmp1, T2))).
	
etatsuivant(etat(Cruche1, Cruche2), r6, etat(0, CrucheTmp2)) :-
    tailleCruche(2, T2),
    Max2 is T2 - Cruche2,
    Cruche1 < Max2,
    CrucheTmp2 is Cruche1 + Cruche2,
    not(interdit(etat(0, CrucheTmp2))).

	
/* On vide cruche 2 dans cruche 1 */
etatsuivant(etat(Cruche1, Cruche2), r7, etat(T1, CrucheTmp2)) :-
    tailleCruche(1, T1),
    Max1 is T1 - Cruche1, /* transvaser une partie de la cruche 2 dans la cruche 1 => comparaisons sur leurs contenus */
    Cruche2 >= Max1,
    CrucheTmp2 is Cruche2 - Max1,
    not(interdit(etat(T1, CrucheTmp2))).
	
etatsuivant(etat(Cruche1, Cruche2), r8, etat(CrucheTmp1, 0)) :-
    tailleCruche(1, T1),
    Max1 is T1 - Cruche1,
    Cruche2 < Max1,
    CrucheTmp1 is Cruche1 + Cruche2,
    not(interdit(etat(CrucheTmp1, 0))).


	
/* Recherche en profondeur */
rechPf(EtatCourt, ListeEtatsSucc, ListeOperSucc):- /* But atteint */
	isFinal(EtatCourt), /* si l'état suivant est un état final, on s'arrête et on affiche les listes */
	write('Liste des étapes successives :'), nl, reverse(ListeEtatsSucc, L1), afficher(L1), nl,
	write('Liste des opérateurs successifs :'), nl, reverse(ListeOperSucc, L2), afficher(L2).
	
rechPf(EtatCourt, ListeEtatsSucc, ListeOperSucc) :-
	etatsuivant(EtatCourt, Topx, EtatSuivt),
	not(member(EtatSuivt, ListeEtatsSucc)),
	rechPf(EtatSuivt, [EtatSuivt | ListeEtatsSucc], [Topx | ListeOperSucc]).
	
	
	
/* Affichage ligne par ligne */
afficherLigne(X,[]):- write(X), nl.
afficherLigne(X, [T|Q]):- write(X), nl, afficherLigne(T,Q).
afficher([T|Q]):- afficherLigne(T,Q).


/* On résoud le programme */
resolution(Ei, ListeEtatsSucc, ListeOperSucc) :- rechPf(Ei, [Ei], []).
resoudre :- initial(Ei), resolution(Ei, ListeEtatsSucc, ListeOperSucc).