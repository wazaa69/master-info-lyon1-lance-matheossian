ligne(lA).
ligne(lB).
ligne(lC).
ligne(lD).

station(perrache).
station(bellecour).
station(hotel-de-ville).
station(charpennes).
station(part-dieu).
station(saxe-gambetta).
station(gerland).
station(croix-rousse).
station(vaise).
station(grange-blanche).
station(venissieux).

station-sur-ligne(perrache, lA).
station-sur-ligne(bellecour, lA).
station-sur-ligne(bellecour, lD).
station-sur-ligne(hotel-de-ville, lA).
station-sur-ligne(hotel-de-ville, lC).
station-sur-ligne(charpennes, lA).
station-sur-ligne(charpennes, lB).
station-sur-ligne(part-dieu, lB).
station-sur-ligne(saxe-gambetta, lB).
station-sur-ligne(saxe-gambetta, lD).
station-sur-ligne(gerland, lB).
station-sur-ligne(croix-rousse, lC).
station-sur-ligne(vaise, lD).
station-sur-ligne(grange-blanche, lD).
station-sur-ligne(venissieux, lD).

changement(L1, L2, S) :- station-sur-ligne(S, L1), station-sur-ligne(S, L2), station(S).

allera(S1, S2, Via) :- station-sur-ligne(S1, L1), station-sur-ligne(S2, L2), changement(L1, L2, Via).
