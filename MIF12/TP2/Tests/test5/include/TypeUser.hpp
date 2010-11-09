#ifndef _TYPEUSER_
#define _TYPEUSER_

#include "Type.hpp"
#include <string>

/**
*	@brief Type d'un symbole.
*/
class TypeUser : public Type {

	private:
		
		int* nomUserType; 
	
	public:

		TypeUser(Type* _type, std::string nomUserType);
		~TypeUser();

};

#endif
