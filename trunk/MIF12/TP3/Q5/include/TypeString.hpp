#ifndef _TYPESTRING_
#define _TYPESTRING_

#include "Type.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeString : public Type {

	public:
	
		/**
		* @brief Crée un type String et stocke le nom "String"
		*/
		TypeString();
		~TypeString();

};

#endif
