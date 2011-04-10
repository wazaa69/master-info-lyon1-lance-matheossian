
#include <opencv/cv.h>
#include <opencv/highgui.h>
// -lcv -lhighgui -lcvaux -lml -lcxcore
#include <vector>

#include "include/Accroissement.h"
#include "include/Graine.h"


int theFlag = 0;
CvPoint* tmp = (CvPoint*) malloc(sizeof( CvPoint ) );
void onLClick(int event, int x, int y, int flags, void* param);
void onLClick(int event, int x, int y, int flags, void* param){
    theFlag = flags;
    tmp->x = x;
    tmp->y = y;
}

int main()
{

    char* nomFenetre = "img";

    cvNamedWindow(nomFenetre, CV_WINDOW_AUTOSIZE);
    cvMoveWindow(nomFenetre,0,0);
    Accroissement acc("images/cle.pgm",10.0);
    cvShowImage( nomFenetre, acc.getImgSrc());


    std::vector<Graine> graines; //pour stocker les cliques de l'utilisateur
    char key = 'y';

    std::cout << "Disposition des graines automatique ? (y) (n)" << std::endl;


    while(key != 'n' && key != 'y')
    {
        key = cvWaitKey(20);

    }
    if (key == 'n')
    {
        std::cout << "Cliquez sur l'image pour déposer les graines" << std::endl;
        while(key != 'q')
        {
            cvSetMouseCallback(nomFenetre, onLClick);

            if(key == 't') //clique gauche
            {
                graines.push_back(Graine(tmp->x,tmp->y));
                std::cout << "Graine " << graines.size() << " : (" << tmp->x << "," << tmp->y << ")." << std::endl;
                theFlag = 0;
            }

            key = cvWaitKey(20);
        }

    }
    else if (key == 'y')
    {

            std::cout << "Disposition automatique des graines: " << std::endl;
            acc.dispositionAutomatique(&graines);

    }

    key = '-';

    if(graines.size())
    {
        //split & merge
        acc.demarrer(graines);

        //sauvegarde de l'image
        cvSaveImage("images/deuxcarre-seg.pgm",acc.getImgSeg());
        //affichage
        cvShowImage( "img", acc.getImgSeg() );
        // affichage informations
        acc.afficherInformations();

        while(key != 'q')  key = cvWaitKey(10);
    }


}
