#ifndef _TYPEUSER_
#define _TYPEUSER_

#include "Symbole.hpp"
#include <string>

/**
*	@brief Type d'un symbole.
*/
class TypeUser : public Symbole {

	public:

	TypeUser(Type& _type, int _id);
	~TypeUser();


};

#endif
