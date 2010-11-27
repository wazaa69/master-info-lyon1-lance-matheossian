#Point de départ
rive(1)

#Point d'arrivé
rive(2)

fermier(fermier).
loup(loup).
chevre(chevre)
choux(choux).


#
protagoniste

#Verification du type de la valeur en paramètre
type_respect(Fermier, Loup, Chevre, Choux) :- fermier(Fermier), loup(Loup), chevre(Chevre), choux(Choux).

#Définition des états interdits
interdit(Fermier, Loup, Chevre, Choux) :- Fermier == 0, Loup == 1, Chevre == 1.
interdit(Fermier, Loup, Chevre, Choux) :- Fermier == 0, Chevre == 1, Choux == 1.

#
peutPasser(Protagoniste, Rive) :- 