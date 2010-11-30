/* Etats interdits sur Rive 0 */
interdit(etat(Fermier, Loup, Chevre, Chou)) :- Fermier = 1, Loup = 1, Chevre = 0, Chou = 0.
interdit(etat(Fermier, Loup, Chevre, Chou)) :- Fermier = 1, Loup = 0, Chevre = 0, Chou = 1.

/* Etats interdits sur Rive 1 */
interdit(etat(Fermier, Loup, Chevre, Chou)) :- Fermier = 0, Loup = 1, Chevre = 1, Chou = 0.
interdit(etat(Fermier, Loup, Chevre, Chou)) :- Fermier = 0, Loup = 0, Chevre = 1, Chou = 1.


/* Etats interdits non atteignable */
interdit(etat(Fermier, Loup, Chevre, Chou)) :- Fermier = 0, Loup = 1, Chevre = 1, Chou = 1.
interdit(etat(Fermier, Loup, Chevre, Chou)) :- Fermier = 1, Loup = 0, Chevre = 0, Chou = 0.


/* <----------------------------------------------------------------------------------->
	Déclaration de ce qui est doit être vrai dans toutes les situations.
	Création d'un nouvel Etat lors du Transport d'un protagoniste d'une Rive à une autre (si les interdits sont respectés).
*/
 
/* Transport Fermier + Chou */
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(0,0,Chevre,Loup)) :- Chou = 1, Fermier = 1, not(interdit(etat(0,0,Chevre,Loup))).
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(1,1,Chevre,Loup)) :- Chou = 0, Fermier = 0, not(interdit(etat(1,1,Chevre,Loup))).

/* Transport Fermier + Chèvre */
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(0,Chou,0,Loup)) :- Chevre = 1, Fermier = 1, not(interdit(etat(0,Chou,0,Loup))).
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(1,Chou,1,Loup)) :- Chevre = 0, Fermier = 0, not(interdit(etat(1,Chou,1,Loup))).

/* Transport Fermier + Loup */
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(0,Chou,Chevre,0)) :- Loup = 1, Fermier = 1, not(interdit(etat(0,Chou,Chevre,0))).
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(1,Chou,Chevre,1)) :- Loup = 0, Fermier = 0, not(interdit(etat(1,Chou,Chevre,1))).

/* Transport Fermier (exemple : chèvre de Rive 0 à Rive 1) */
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(0,Chou,Chevre,Loup)) :- Fermier = 1, not(interdit(etat(0,Chou,Chevre,Loup))).
etatsuivant(etat(Fermier,Chou,Chevre,Loup), etat(1,Chou,Chevre,Loup)) :- Fermier = 0, not(interdit(etat(1,Chou,Chevre,Loup))).


/* <----------------------------------------------------------------------------------->
	recherche des opérations permettant de passer de l’état initial à l’état final
*/

resolution(EtatCourt, EtatSuivant, ListeEtatsSuccTmp, ListeEtatsSucc):- /* But atteint */
	EtatCourt = EtatSuivant, /* EtatCourt = (1,1,1,1) et EtatSuivant = (1,1,1,1) */
	append(ListeEtatsSuccTmp,[],ListeEtatsSucc).
	
resolution(EtatCourt, EtatBut, ListeEtatsSuccTmp, ListeEtatsSucc) :-
	etatsuivant(EtatCourt, EtatSuivt),
	not(member(EtatSuivt, ListeEtatsSuccTmp)),
	resolution(EtatSuivt, EtatBut, [EtatSuivt | ListeEtatsSuccTmp], ListeEtatsSucc).


/* [EtatInit] la liste des états sucessif, jusqu'à la résolution */
transports(EtatInit, EtatBut, ListeEtatsSucc) :- resolution(EtatInit, EtatBut, [EtatInit], ListeEtatsSucc).


/* <----------------------------------------------------------------------------------->
	Lancement résolution et Affichage
*/

/* Affichage ligne par ligne */	
afficherLigne(X,[]):- write(X), nl.
afficherLigne(X, [T|Q]):- write(X), nl, afficherLigne(T,Q).
afficher([T|Q]):- afficherLigne(T,Q).


/* On fournit :
	- l'état initial,
	- le but,
	- et un ordre de Transport (initiallemen vide)
*/
resoudre :-
	transports(etat(0,0,0,0), etat(1,1,1,1), ListeEtatsSucc),
	reverse(ListeEtatsSucc,R),
	write('etat(F,Cu,Cr,L)'), nl, nl,
	afficher(R).