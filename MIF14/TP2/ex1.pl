ville(rome).
ville(tunis).
ville(londres).

duree(1).
duree(2).

hebergement(habitant).
hebergement(camping).
hebergement(hotel).

ptransport(rome,300).
ptransport(tunis,500).
ptransport(londres,200).

psejour(rome,hotel,1000).
psejour(rome,camping,500).
psejour(rome,habitant,300).

psejour(tunis,hotel,600).
psejour(tunis,camping,400).
psejour(tunis,habitant,200).

psejour(londres,hotel,800).
psejour(londres,camping,700).
psejour(londres,habitant,350).

cout(V, C, D, P) :- ptransport(V,X),
                 psejour(V,C,Y),
                 duree(D),
                 P is D*Y+X.
voyage_eco(V, C, D, Pmax) :- cout(V, C, D, P),
                             P < Pmax.