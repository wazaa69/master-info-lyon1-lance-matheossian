#ifndef _TYPEUSER_
#define _TYPEUSER_

#include "Symbole.hpp"
#include <string>
#include "TypeArray.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeUser : public Symbole {

	public:

	/**
	* @brief Constructeur
	* @param _type type du symbole
	* @param _id entier identifiant du symbole typeUser
	*/
	TypeUser(Type& _type, int _id);

	/**
	* @brief Destructeur
	*/
	~TypeUser();

	/**
	* @brief Accesseur
	* @return type type du symbole typeUser
	*/
	Type* getType();
};

#endif
