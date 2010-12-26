/* MIF18: Bases de données avancées
TP N°2
GROUPE 2
LANCE Florian 10604720
MATHEOSSIAN Dimitri 10604950

*/

--Requête n°1:

/* Ecrire une requête qui pour chaque message génère un élément XML ”message”
 avec un élément ”contenu” incluant le corps du message et un élément ”date”
 incluant la date du message. 
*/

SELECT XMLElement(name "message", 
       XMLElement(name "contenu", corps ),
       XMLElement(name "date", date_envoi))
FROM message

----------------------------------------------------------------------------------------

--Requête n°2:

/* Ecrire une requête qui pour chaque message génère un élément ”message” avec
 un attribut ”id_message”, un attribut ”date”, un attribut ”auteur” ayant pour 
 valeur l'identifiant de l'auteur et enfin le corps du message comme contenu. 
*/

SELECT XMLElement(name "message", 
     XMLAttributes(id AS "id_message", date_envoi AS "date", auteur), corps)
FROM message

/* Que peut-on remarquer sur la casse des nom d'attributs?
 Modifier la requête (si besoin) pour que tous les noms soient en minuscules. 
*/

--Les colonnes non renommées sont en majuscules. On utilise "AS" pour renommer en minuscules.

SELECT XMLElement(name "message", 
     XMLAttributes(id AS "id_message", date_envoi AS "date", auteur AS "auteur"), corps)
FROM message

----------------------------------------------------------------------------------------

--Requête n°3:

/* Modifier la requête précédente en utilisant cette fonction pour mettre 
le corps du message dans un élément ”CORPS” et l'email de l'auteur dans
 un élément ”mailAuteur”. 
*/

SELECT XMLElement(name "message", 
     XMLAttributes(me.id AS "id_message", me.date_envoi AS "date", me.auteur AS "auteur"),
     XMLForest(me.corps AS "CORPS", m.email AS "mailAuteur"))
FROM message me, membre m
WHERE me.auteur = m.id;

---------------------------------------------------------------------------

--Requête n°4:

/* Modifier la requête précédente pour ajouter à chaque élément message 
un élément ”reponse” avec comme contenu l'identifiant de la réponse, 
et cela pour chacune des réponses à ce message 
(on ne traitera que les messages ayant des réponses). 
*/

SELECT XMLElement(name "message", 
     XMLAttributes(me.id AS "id_message", me.date_envoi AS "date", me.auteur AS "auteur"),
     XMLForest(me.corps AS "CORPS", m.email AS "mailAuteur"),
	 XMLAgg(XMLElement(name "reponse", r.id)))
FROM message me, message r, membre m
WHERE me.auteur = m.id;
AND r.parent = me.id
GROUP BY me.id, me.date_envoi, me.auteur, me.corps, m.email;

/* Remplacer la jointure sur les messages par un LEFT OUTER JOIN.
 Que peut-on remarquer pour les messages n'ayant pas de réponse? 
*/

SELECT XMLElement(name "message", 
     XMLAttributes(me.id AS "id_message", me.date_envoi AS "date", me.auteur AS "auteur"),
     XMLForest(me.corps AS "CORPS", m.email AS "mailAuteur"),
	 XMLAgg(XMLElement(name "reponse", r.id)))
FROM message me,  LEFT OUTER JOIN message r ON me.id = r.parent, membre m
WHERE me.auteur = m.id;
GROUP BY me.id, me.date_envoi, me.auteur, me.corps, m.email;

-- On remarque que les messages sans réponse, sont quand même pris en compte.

---------------------------------------------------------------------------

--Requête n°5:

/* Créer une vue qui à chaque identifiant de message associe sa représentation XML 
telle que décrite ci-dessus. 
*/

CREATE OR REPLACE VIEW message_xml AS
SELECT me.id, XMLElement(name "message",
    XMLAttributes(me.id AS "id_message", me.date_envoi AS "date", me.auteur AS "auteur"), 
	XMLForest(me.corps, m.email AS "mailAuteur"),
	XMLAgg(XMLElement(name "reponse", XMLAttributes(r.id AS "id_reponse")))) AS XML
FROM message me LEFT OUTER JOIN message r ON me.id = r.parent, membre m
WHERE me.auteur = m.id
GROUP BY me.id, me.date_envoi, me.auteur, me.corps, m.email;

/* Créer une vue qui crée un document XML contenant l'ensemble des messages 
sous la forme donnée précédemment. 
*/

CREATE OR REPLACE VIEW document_xml AS
SELECT XMLRoot
	(
		XMLElement(name "message", 
		XMLAttributes(me.id AS "id_message", me.date_envoi AS "date", me.auteur AS "auteur"), 
		XMLForest(me.corps AS "CORPS", m.email AS "mailAuteur"),
		XMLAgg(XMLElement(name "reponse", r.id)))
	) AS RESULT
FROM message me LEFT OUTER JOIN message r ON me.id = r.parent, membre m
WHERE me.auteur = m.id
GROUP BY me.id, me.date_envoi, me.auteur, me.corps, m.email;

---------------------------------------------------------------------------

--Requête n°6:

/* Mettre au point une requête XQuery permettant de transformer une représentation XML
 des messages en une représentation HTML ayant l'aspect suivant: 

<li>
	 <p>Message 25 de <a href="mailto:bob@aol.com">bob@aol.com</a>:</p>
	 <p>Bonjour, c'est moi</p>
	 <p>Réponses: message 45 message 49</p>
</li>
*/	

SELECT XMLQuery('<li>{$message_head}{$message_body}{$message_responses}</li>'
	PASSING XMLElement(name "p", 'Message ' || me.id || ' de ' || XMLElement(name "a", XMLAttributes('maito:' || m.email AS "href"), m.email)) AS "message_head",
	XMLElement(name "p", me.corps) AS "message_body", 
	XMLForest(r.id AS "p") AS "message_responses"
	RETURNING CONTENT)
FROM message me LEFT OUTER JOIN message r ON me.id = r.parent, membre m
WHERE me.auteur = m.id
GROUP BY me.id, me.date_envoi, me.auteur, me.corps, m.email, r.id;

/* Faire une vue de la requête précédente.
*/

CREATE OR REPLACE VIEW message_html AS
SELECT XMLRoot
	(
		XMLElement(name "ul",
			XMLQuery('<li>{$message_head}{$message_body}{$message_responses}</li>'
			PASSING XMLElement(name "p", 'Message ' || me.id || ' de ' || XMLElement(name "a", XMLAttributes('maito:' || m.email AS "href"), m.email)) AS "message_head",
			XMLElement(name "p", me.corps) AS "message_body", 
			XMLForest(r.id AS "p") AS "message_responses"
			RETURNING CONTENT)
		)
	) AS RESULT
FROM message me LEFT OUTER JOIN message r ON me.id = r.parent, membre m
WHERE me.auteur = m.id
GROUP BY me.id, me.date_envoi, me.auteur, me.corps, m.email, r.id;

/* Puis créer une autre vue qui va générer le document HTML entier. */

CREATE OR REPLACE VIEW document_html AS
SELECT XMLRoot
	(
		XMLElement(name "html", XMLElement(name "head", XMLElement(name "title", 'Messages')), 
			XMLElement(name "body",
				XMLElement(name "ul",
					XMLQuery('<li>{$message_head}{$message_body}{$message_responses}</li>'
					PASSING XMLElement(name "p", 'Message ' || me.id || ' de ' || XMLElement(name "a", XMLAttributes('maito:' || m.email AS "href"), m.email)) AS "message_head",
					XMLElement(name "p", me.corps) AS "message_body", 
					XMLForest(r.id AS "p") AS "message_responses"
					RETURNING CONTENT)
				)
			)
		)
	) AS RESULT
FROM message me LEFT OUTER JOIN message r ON me.id = r.parent, membre m
WHERE me.auteur = m.id
GROUP BY me.id, me.date_envoi, me.auteur, me.corps, m.email, r.id;