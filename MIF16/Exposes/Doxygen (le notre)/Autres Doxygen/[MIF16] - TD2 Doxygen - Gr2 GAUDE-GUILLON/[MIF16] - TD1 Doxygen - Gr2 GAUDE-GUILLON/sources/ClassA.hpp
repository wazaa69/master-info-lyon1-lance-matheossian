/*!
 * \class ClassA
 *
 * \brief Classe d'exemple A
 * \brief Voici un petit bout de code qui illustre l'utilisation de Doxygen 
 * au travers d'un code de classe C++.
 *
 * \author Emmanuel GAUDE <emmanuel.gaude@etu.univ-lyon1.fr>
 * \author Benjamin GUILLON <benjamin.guillon@etu.univ-lyon1.fr>
 * \since 2009/09/26
 * \version 2009/10/03
 *
 * \see http://www.stack.nl/~dimitri/doxygen/manual.html
 * \note Vous mettez ici ce que vous voulez, comme une licence.
 */

/*! \mainpage Ma page d'introduction perso
 *
 *
 * Ceci est l'introduction du document.
 * <img src="http://www710.univ-lyon1.fr/~p0603141/logo%20ucbl.png">
 */
 
#ifndef DEF_CLASSA
#define DEF_CLASSA

/** \brief Macro calculant la valeur de La Réponse sur l'Univers. */
#define THE_ANSWER_MACRO 40 + 2
 
/* Inclusion d'une classe externe. (ce commentaire n'est pas généré) */ 
#include "ClassB.h"

class ClassA {
    private:
			/*! \brief Constante de classe répondant à la grande question sur l'Univers. */
			static const THE_ANSWER = THE_ANSWER_MACRO;
			
			/*! \brief Une énumération de trois valeurs sur une échelle logarithmique 10. */
			enum MonEnum {
				VAL1 = 1, /*!< Première valeur de l'énum (1) */
				VAL2 = 10, /*!< Deuxième valeur de l'énum (10) */
				VAL3 = 100 /*!< Troisième valeur de l'énum (100) */
			};
			
			/*! 
			  \brief Ma structure interne.
			  \brief Une structure de données personnelle.
			*/
			struct MaStruct {
				/*! \brief Premier attribut de la structure. */
				int attr1;
				/*! \brief Second attribut de la structure. */
				char attr2;
			};
			
			/*! \brief Mon premier attribut. */
			long int _attr1;
			
			ClassB * _attr2; /*!< \brief Mon second attribut : pointeur vers instance de B. */
			
    public:
			/*! \brief Constructeur par défaut. */
			ClassA();
			
			/*! \brief Destructeur par défaut. */
			~ClassA();
			
			/*! 
			 * \brief Ma méthode, et à personne d'autre !
			 *
			 * \pre Un système sous Windows...
			 * \post Une perte de données.
			 *
			 * \return Retourne systématiquement NULL.
			 * \deprecated Ne pas l'utiliser sinon plantage assuré...
			 * \bug BSOD
			*/
			MaStruct * maMethode();
			
			/*! 
			 * \brief Version améliorée de ma méthode, et à personne d'autre !
			 *
			 * \param [in,out] param1 Entier dont il faut calculer la racine carrée.
			 * \param [in] param2 Message à afficher.
			 * \return Retourne un pointeur sur un nouvel objet MaStruct initialisé
			 * avec param1.
			 */
			MaStruct * maMethode(int param1, const string param2);
};
 
#endif