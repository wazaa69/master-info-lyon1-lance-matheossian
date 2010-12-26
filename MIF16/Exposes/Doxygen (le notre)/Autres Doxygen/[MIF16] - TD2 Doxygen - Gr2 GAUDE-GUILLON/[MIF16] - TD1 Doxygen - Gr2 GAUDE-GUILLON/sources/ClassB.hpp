/*!
 * \class ClassB
 * \since 2009/09/26
 * \version 2009/10/03
 *
 * \brief Classe d'exemple B
 * \brief Une autre classe plus succinte pour compléter la classe A.
 *
 * \author Emmanuel GAUDE <emmanuel.gaude@etu.univ-lyon1.fr>
 * \author Benjamin GUILLON <benjamin.guillon@etu.univ-lyon1.fr>
 *
 * \see http://www.stack.nl/~dimitri/doxygen/manual.html
 * \note Vous mettez ici ce que vous voulez, comme une licence.
 */
 
#ifndef DEF_CLASSB
#define DEF_CLASSB
 
class ClassB {
		private:
			/*! \brief Nom de l'instance. */
			string _monNom;
		
    public:
			/*! 
			 * \brief Constructeur de la classe.
			 *
			 * \pre monNom non vide.
			 * \post Construit un objet de type ClassB.
			 *
			 * \param [in] monNom Nom de l'instance.
			 *
			 * \throw Exception Retourne une exception de type Exception si monNom est vide.
			 */
			ClassB(const string monNom);
			
			/*! 
			 * \brief Accesseur de _monNom.
			 *
			 * \return Nom de l'instance.
			 */
			string getMonNom() const;
			
			/*! 
			 * \brief Modificateur de _monNom.
			 *
			 * \param [in] monNom Nom de l'instance.
			 *
			 * \throw Exception Retourne une exception de type Exception si monNom est vide.
			 */
			void setMonNom(string monNom);
			
			/*!
			 * \brief Ma méthode.
			 *
			 * \return Retourne systématiquement NULL.
			 * \retval NULL Valeur normale de retour.
			 *
			 * \todo Ecrire un code fonctionnel.
			 * \warning La méthode de la classe A n'est pas la seule de ce nom finalement !
			 *
			 * \throw Exception Retourne une exception si appelée une seconde fois.
			 */
			double maMethode();
};
 
#endif
