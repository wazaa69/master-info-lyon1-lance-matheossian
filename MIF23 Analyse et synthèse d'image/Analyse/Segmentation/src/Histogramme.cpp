#include "../include/Histogramme.h"

Histogramme::Histogramme (const IplImage* img_src, const unsigned int rvbG): picMax(-1), min(255), max(0)
{
    clock_t finAlgo, debutAlgo = clock();

    for(unsigned int i = 0; i < 255; i++) histogramme.push_back(0);

    const unsigned int largeur = img_src->width;
    const unsigned int hauteur = img_src->height;

    if(rvbG == 0 || rvbG == 1 || rvbG == 2)
    {
        unsigned int choix = rvbG;

        if(rvbG == 0) choix = 2; //BGR en RVB
        else if (rvbG == 2) choix = 0;

        for(unsigned int y = 0; y < hauteur; y++)
        {
            for(unsigned int x = 0; x < largeur; x++)
            {
                float valeur = ((uchar *)(img_src->imageData + y*img_src->widthStep))[x*img_src->nChannels + choix];
                cumule(valeur);
                majMin(valeur);
                majMax(valeur);
            }
        }
    }
    else
    {

        for(unsigned int y = 0; y < hauteur; y++)
        {
            for(unsigned int x = 0; x < largeur; x++)
            {
                //pas de CvGet2D dans une double boucle avec CodeBlocks, sinon on a un petit message d'erreur
                float r = ((uchar *)(img_src->imageData + y*img_src->widthStep))[x*img_src->nChannels + 0];
                float v = ((uchar *)(img_src->imageData + y*img_src->widthStep))[x*img_src->nChannels + 1];
                float b = ((uchar *)(img_src->imageData + y*img_src->widthStep))[x*img_src->nChannels + 2];
                unsigned int valeur = (r+v+b)/3;
                cumule(valeur);
                majMin(valeur);
                majMax(valeur);
            }

        }
    }



    finAlgo = clock();

    std::cout << "Temps de création de l'histogramme : " << (((double)finAlgo - debutAlgo) / CLOCKS_PER_SEC) << std::endl;
}

Histogramme::~Histogramme (){}

void Histogramme::majMax(const unsigned int valeur){if(valeur > max) max = valeur;}
void Histogramme::majMin(const unsigned int valeur){if(valeur < min) min = valeur;}
void Histogramme::cumule(const unsigned int valeur){histogramme[floor(valeur)]++;}


unsigned int Histogramme::getPicMax()
{
    if(picMax != -1)
        return picMax;
    else
    {
        double intensite = -1;
        for(unsigned int i = 0; i < histogramme.size(); i++)
        {
            if(histogramme[i] > intensite)
            {
                intensite = histogramme[i];
                picMax = i;
            }
        }

        return picMax;
    }
}


std::vector<double>& Histogramme::getHistogramme(){return histogramme;}

void Histogramme::listerValeurs() const
{
    for(unsigned int i = 0; i < histogramme.size(); i++)
        std::cout << histogramme[i] << std::endl;
}
