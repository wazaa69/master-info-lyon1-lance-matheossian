#ifndef _TYPEARRAY_
#define _TYPEARRAY_

#include "Type.hpp"
#include "TypeInterval.hpp"

/**
*	@brief Type d'un symbole.
*/
class TypeArray : public Type {

	private:
	
		TypeInterval* intervalArray; /** @var intervalArray un interval */
		Type* typeArray; /** @var typeArray le Type des éléments stockés dans le tableau */
	
	public:
	
		/**
		* @brief Crée un type Array et stocke le nom "Array"
		*/
		TypeArray();

		/**
		* @brief Crée un type Array, depuis une instance TypeArray existante
		* @param _ta le contenu à copier
		*/
		TypeArray(TypeArray* _ta);
		
		/**
		* @brief Crée un type Array, depuis une instance d'un TypeInterval et d'un Type
		* @param _interval l'interval à copier
		* @param _type le type à copier
		*/
		TypeArray(TypeInterval* _interval, Type* _type);
		
		~TypeArray();

		/**
		* @brief Récupère le type stocké par le tableau
		* @return retourne l'adresse d'une instance de type Type 
		*/
		Type* getTypeTab();
		
		/**
		* @brief Récupère le TypeInterval du tableau
		* @return retourne l'adresse d'une instance de type TypeInterval 
		*/
		TypeInterval* getInterval();
		

};

#endif
