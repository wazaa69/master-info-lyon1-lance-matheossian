#include <opencv/cv.h>
#include <opencv/highgui.h>

#include "include/Accroissement.h"

//-lcv -lhighgui -lcvaux -lml -lcxcore


int main()
{
    Accroissement acc("images/deuxcarre.pgm", 5);
    acc.demarrer();
}
