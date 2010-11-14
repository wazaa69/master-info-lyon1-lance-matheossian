#ifndef _TYPECHAR_
#define _TYPECHAR_

#include "Type.hpp"

/**
*	@brief Type d'un symbole.
*/

class TypeChar : public Type {

	public:

		/**
		* @brief Constructeur Crée un type Char et stocke le nom "Char"
		*/
		TypeChar();

		/**
		* @brief Destructeur
		*/
		~TypeChar();

};

#endif
