/* Contenance max des cruches */
tailleCruche(1, 8). /* grande cruche = n°1 */
tailleCruche(2, 5). /* petite cruche = n°2 */


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



/* Remplir la grande cruche */
etatsuivant(etat(_, Cruche2), 'r1 = remplir la cruche 1' , etat(8, Cruche2)) :- not(interdit(etat(8, Cruche2))).

/* Remplir la petite cruche */
etatsuivant(etat(Cruche1, _), 'r2 = remplir la cruche 2', etat(Cruche1, 5)) :- not(interdit(etat(Cruche1, 5))).

/* Vider la grande cruche */
etatsuivant(etat(_, Cruche2), 'r3 = vider la cruche 1', etat(0, Cruche2)) :- not(interdit(etat(0, Cruche2))).

/* Vider la petite cruche */
etatsuivant(etat(Cruche1, _), 'r4 = vider la cruche 2', etat(Cruche1, 0)) :- not(interdit(etat(Cruche1, 0))).



/* On vide la grande cruche (n°1) dans la petite cruche (n°2) -------------------------------------> */

/* la grande cruche se vide dans la petite, la petite cruche est pleine mais il en reste dans la grande */
etatsuivant(etat(Cruche1, Cruche2), 'r5 = vider la cruche 1 dans la cruche 2 => la cruche 2 est pleine,  la cruche 1 est non vide', etat(CrucheTmp1, T2)) :-
    tailleCruche(2, T2),
    Diff2 is T2 - Cruche2,
    Cruche1 >= Diff2,
    CrucheTmp1 is Cruche1 - Diff2,  
    not(interdit(etat(CrucheTmp1, T2))).

/* la grande cruche se vide dans la petite, la petite cruche est pleine et la grande est vide */
etatsuivant(etat(Cruche1, Cruche2), 'r6 = vider la cruche 1 dans la cruche 2 => la cruche 2 est pleine, la cruche 1 est vide', etat(0, CrucheTmp2)) :-
    tailleCruche(2, T2),
    Diff2 is T2 - Cruche2,
    Cruche1 < Diff2,
    CrucheTmp2 is Cruche1 + Cruche2,
    not(interdit(etat(0, CrucheTmp2))).

	
/* On vide la petite cruche dans la grande cruche (inverse des précédants) -------------------------------------> */
etatsuivant(etat(Cruche1, Cruche2), 'r7 = vider la cruche 2 dans la cruche 1 => la cruche 1 est pleine,  la cruche 2 est non vide', etat(T1, CrucheTmp2)) :-
    tailleCruche(1, T1),
    Diff1 is T1 - Cruche1,
    Cruche2 >= Diff1,
    CrucheTmp2 is Cruche2 - Diff1,
    not(interdit(etat(T1, CrucheTmp2))).
	
etatsuivant(etat(Cruche1, Cruche2), 'r8 = vider la cruche 2 dans la cruche 1 => la cruche 1 est pleine, la cruche 2 est vide', etat(CrucheTmp1, 0)) :-
    tailleCruche(1, T1),
    Diff1 is T1 - Cruche1,
    Cruche2 < Diff1,
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


/* On résoud le problème */
resolution(Ei) :- rechPf(Ei, [Ei], []).
resoudre :- initial(Ei), resolution(Ei).