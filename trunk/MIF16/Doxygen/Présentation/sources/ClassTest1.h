
/*!
 * \class ClassTest1
 *
 * \brief Classe d'exemple test1
 * \brief Code d'exemple pour presenter l'utilisation de doxygen
 * dans un code de classe C++.
 *
 * \author Florian LANCE <lance.florian@gmail.com>
 * \author Dimitri MATHEOSSIAN <anima876@htomail.fr>
 * \see http://www.stack.nl/~dimitri/doxygen/manual.html
 *
 * \note Partie libre qui peut accueillir n'importe quoi ou bien une licence.
 *
 * \since 2010/09/20
 * \version 2010/09/27
 *
 */

/*! \mainpage Page d'introduction du programme d'exemple d'utilisation de doxygen
 *
 *
 * Ceci est l'introduction du document.
 * <img src="http://www.bo.infn.it/~medinace/figures/doxygen.png">
 */


#ifndef CLASSTEST1_H
#define CLASSTEST1_H


/** \brief Voici une macro. */
#define MACRO_1 11

/** \brief Voici une deuxième macro. */
#define MACRO_2 56

/* Inclusion de la classe ClassTest2.h, ce commentaire n'est pas genere.*/
#include "ClassTest2.h"

class ClassTest1
{
    public:

        /*! \brief Constructeur par défaut. */
        ClassTest1();

        /*! \brief Constructeur surchargé. */
        ClassTest1(int arg1);

        /*! \brief Destructeur par défaut. */
        virtual ~ClassTest1();

    private:

        /*! \brief Constante de classe. */
        static const RESULTAT = MACRO_1 + MACRO_2;

        /*! \brief Premier attribut. */
        int attribut1;

};

#endif // CLASSTEST1_H
