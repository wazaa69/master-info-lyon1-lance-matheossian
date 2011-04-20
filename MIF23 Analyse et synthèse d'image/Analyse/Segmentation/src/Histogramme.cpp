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
                float intensite = ((uchar *)(img_src->imageData + y*img_src->widthStep))[x*img_src->nChannels + choix];
                cumule(intensite);
                majMin(intensite);
                majMax(intensite);
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
                unsigned int intensite = (r+v+b)/3;
                cumule(intensite);
                majMin(intensite);
                majMax(intensite);
            }

        }
    }



    finAlgo = clock();

    std::cout << "Temps de creation de l'histogramme : " << (((double)finAlgo - debutAlgo) / CLOCKS_PER_SEC) << std::endl;
}

Histogramme::~Histogramme (){}

void Histogramme::majMax(const unsigned int intensite){if(intensite > max) max = intensite;}
void Histogramme::majMin(const unsigned int intensite){if(intensite < min) min = intensite;}
void Histogramme::cumule(const unsigned int intensite){histogramme[floor(intensite)]++;}


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
