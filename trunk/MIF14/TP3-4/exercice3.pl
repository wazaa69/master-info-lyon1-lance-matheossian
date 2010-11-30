/* Etat final et but */
initial(etat(0,0)).
final(etat(4,0)).
final(etat(3,1)).
final(etat(2,2)).
final(etat(1,3)).
final(etat(0,4)).

isFinal(X) :- final(X).

/* Valeur max des cruches */
cruche(1, 8).
cruche(2, 5).

/* Etats interdits */
interdit(etat(Cr1, _)) :- Cr1 > 8.
interdit(etat(_, Cr2)) :- Cr2 > 5.
interdit(etat(Cr1, _)) :- Cr1 < 0.
interdit(etat(_, Cr2)) :- Cr2 < 0.



/* Remplir cruche 1 */
etatsuivant(etat(_, Cr2), etat(8, Cr2)) :- not(interdit(etat(8, Cr2))).

/* Remplir cruche 2 */
etatsuivant(etat(Cr1, _), etat(Cr1, 5)) :- not(interdit(etat(Cr1, 5))).

/* Vider cruche 1 */
etatsuivant(etat(_, Cr2), etat(0, Cr2)) :- not(interdit(etat(0, Cr2))).

/* Vider cruche 2 */
etatsuivant(etat(Cr1, _), etat(Cr1, 0)) :- not(interdit(etat(Cr1, 0))).

/* Vider cruche 1 dans cruche 2 */
etatsuivant(etat(Cr1, Cr2), etat(Crtmp1, M2)) :-
    cruche(2, M2),
	/* On regarde si on peut effectivement transvaser une partie de la cruche 1 dans la cruche 2, on fait donc des comparaisons sur leurs contenus*/
    Max2 is M2 - Cr2,
    Cr1 >= Max2,
    Crtmp1 is Cr1 - Max2,
    not(interdit(etat(Crtmp1, M2))).
etatsuivant(etat(Cr1, Cr2), etat(0, Crtmp2)) :-
    cruche(2, M2),
    Max2 is M2 - Cr2,
    Cr1 < Max2,
    Crtmp2 is Cr1 + Cr2,
    not(interdit(etat(0, Crtmp2))).

/* Vider cruche 2 dans cruche 1 */
etatsuivant(etat(Cr1, Cr2), etat(M1, Crtmp2)) :-
    cruche(1, M1),
	/* On regarde si on peut effectivement transvaser une partie de la cruche 2 dans la cruche 1, on fait donc des comparaisons sur leurs contenus*/
    Max1 is M1 - Cr1,
    Cr2 >= Max1,
    Crtmp2 is Cr2 - Max1,
    not(interdit(etat(M1, Crtmp2))).
	
etatsuivant(etat(Cr1, Cr2), etat(Crtmp1, 0)) :-
    cruche(1, M1),
    Max1 is M1 - Cr1,
    Cr2 < Max1,
    Crtmp1 is Cr1 + Cr2,
    not(interdit(etat(Crtmp1, 0))).


	
/* Recherche en profondeur */
rechPf(EtatCourt, ListeEtatsSuccTmp, ListeEtatsSucc):- /* But atteint */
	isFinal(EtatCourt), /* l'état suivant ne doit pas être un état final pour pouvoir continuer */
	append(ListeEtatsSuccTmp,[],ListeEtatsSucc).
	
rechPf(EtatCourt, ListeEtatsSuccTmp, ListeEtatsSucc) :-
	etatsuivant(EtatCourt, EtatSuivt),
	not(member(EtatSuivt, ListeEtatsSuccTmp)),
	rechPf(EtatSuivt, [EtatSuivt | ListeEtatsSuccTmp], ListeEtatsSucc).
	
	
/* Affichage ligne par ligne */
afficherLigne(X,[]):- write(X), nl.
afficherLigne(X, [T|Q]):- write(X), nl, afficherLigne(T,Q).
afficher([T|Q]):- afficherLigne(T,Q).


/* Lancement de résolution du programme */
resolution(Ei, ListeEtatsSucc) :- rechPf(Ei, [Ei], ListeEtatsSucc).
resoudre :- initial(Ei), resolution(Ei, ListeEtatsSucc), reverse(ListeEtatsSucc, Resultat), nl, afficher(Resultat).