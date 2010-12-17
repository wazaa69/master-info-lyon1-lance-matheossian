% Auteur:
% Date: 09/11/2009

ligne(a,[perrache,ampere,bellecour,cordeliers,hotel_de_ville,foch,massena,charpennes,republique,gratte-ciel,flachet,cusset,laurent_bonnevay]).
ligne(b,[charpennes,brotteaux,part-dieu,place_guichard,saxe-gambetta,jean_mace,place_jean_jaures,debourg,stade_de_gerland]).
ligne(c,[hotel_de_ville,croix-paquet,croix-rousse,henon,cuire]).
ligne(d,[gare_de_vaise,valmy,gorge_de_loup,vieux_lyon,bellecour,guillotiere,saxe-gambetta,garibaldi,sans-souci,mon_plaisir-lumiere,grange_blanche,laennec,mermoz_pinel,parilly,gare_de_venissieux]).

station-sur-ligne(S, L) :- ligne(L, Q), member(S, Q).

allera(Src, Dest, Stations) :- itineraire(Src, Dest, Stations).
itineraire(D, D, Q).
itineraire(S, D, Q) :- .