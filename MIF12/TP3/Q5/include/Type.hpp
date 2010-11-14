#ifndef _TYPE_
#define _TYPE_

#include <string>

/**
*	@brief Type d'un symbole (plusieurs classe héritent de Type).
*/
class Type {

	protected:

		std::string* type; /** @var type l'attribut type peut être = "integer" | "real" | "boolean" | "interval" | "array" | "char" | "string" | "pointeur" | "record". */

	public:

		/**
		* @brief Constructeur par défaut
		*/
		Type();
		
		/**
		* @brief Assigne l'adresse d'une chaine de caractère à l'attribut type
		* @param _type adresse d'une chaine de caractères
		*/	
		Type(std::string* _type);
		
		/**
		* @brief Assigne l'adresse d'une chaine de caractère à l'attribut type
		* @param _type une instance de Type ou d'une classe enfant
		*/
		Type(const Type &_type);
		
		
		~Type();
		

		/**
		* @brief Récupére le type du symbole sous la forme d'une chaine de caractères
		* @return Retourne un pointeur sur une chaine de caractères
		*/
		std::string* getStringType();



		
	
};

#endif
