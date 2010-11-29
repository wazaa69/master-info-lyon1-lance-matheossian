/* Etat initial et final */
initial( [h,h,h,b,b,b] ).
final([b,b,b,h,h,h] ).
nbMaxDiff(6). /* nombre maximum de différences entre l'état initial et l'état final */


/*
	Remplace une sous-liste par une autre dans une liste quelconque.
	Dans notre cas L2 = EtatSuivant.
	S1 la chaine à trouver
	S2 la chaine à remplacer
	L1 la chaine d'entrée
	L2 la chaine résultante
*/
remp(S1, S2, L1, L2) :- 
	append(Tmp1, L1Suffixe, L1),
	append(L1Prefixe, S1, Tmp1),
	append(L1Prefixe, S2, Tmp2),
	append(Tmp2, L1Suffixe, L2).


/*
	Liste des opérateurs disponibles pour arriver à l'état final
	L1 est l'état courant
	rN constante qui définit le nom de l'opération
	L2 le prochaine état (il faut le construire en utilisant remp())
*/
opF( L1, r1, L2) :- remp([h,h], [b,b], L1, L2).
opF( L1, r2, L2) :- remp([h,b], [b,h], L1, L2).
opF( L1, r3, L2) :- remp([b,h], [h,b], L1, L2).
opF( L1, r4, L2) :- remp([b,b], [h,h], L1, L2).


/*
	Compte le nombre de différence(s) entre l'argument 1 et l'argument 2
	Nbdiff compte le nombre de différence(s) entre l'état finale et l'état suivant 
*/

calculerDiff([H], [B], 0).
calculerDiff([I], [I], 1).

calculerDiff([T|Qs], [T|Qf], Nbdiff) :-  /* tête identique */
	calculerDiff(Qs, Qf, Nbdiff).
	
calculerDiff([Ts|Qs], [Tf|Qf], Nbdiff) :- /* tête différente => incrémentation du nombre de différences */
	calculerDiff(Qs, Qf, NbdiffRec),
	Nbdiff is NbdiffRec + 1. /* passage obligatoire par une variable tmp */
	


/* L'état final est atteint */
rechPf(Ef, Ef, Letats, [], Nbdiff) :- write('Liste des étapes successivent : '), nl, reverse(Letats, L), afficher(L), nl.

/* 
	Recherche de la liste des opérations our atteindre Ef
	la liste des opérateurs et des états sont assemblés quand on remonte dans les appels => il faut reverse chacune des listes
*/
rechPf(Ec, Ef, Letats, [Topx|Qlop], Nbdiff) :- 
	opF(Ec, Topx, Esuivant), /* prend le premier opF qui fonctionne (ce qui n'est pas forcément le meilleur chemin) */
	not(member(Esuivant,Letats)),
	calculerDiff(Esuivant, Ef, NbdiffSuiv),
	NbdiffSuiv < Nbdiff,
	rechPf(Esuivant, Ef, [Esuivant | Letats], Qlop, NbdiffSuiv).

	
	
/* Affichage ligne par ligne */
afficherLigne(X,[]):- write(X), nl.
afficherLigne(X, [T|Q]):- write(X), nl, afficherLigne(T,Q).
afficher([T|Q]):- afficherLigne(T,Q).

		
/* résolution général */
resoudre:- initial(Ei), final(Ef), nbMaxDiff(N), rechPf(Ei, Ef, [Ei], ListeSuccOper, N),  write('Liste des opérateurs successif :'), nl, afficher(ListeSuccOper).