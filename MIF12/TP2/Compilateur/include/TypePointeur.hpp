#ifndef _TYPEPOINTEUR_
#define _TYPEPOINTEUR_

#include "Type.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypePointeur : public Type {

	private:

	Type* reference;

	public:
		TypePointeur();
		~TypePointeur();

		TypePointeur(const Type &ref);

		//Type* getReference() const;
		//std::string* getStringType();
};

#endif
